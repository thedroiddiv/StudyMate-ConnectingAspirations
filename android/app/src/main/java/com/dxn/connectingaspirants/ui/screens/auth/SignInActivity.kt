package com.dxn.connectingaspirants.ui.screens.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dxn.connectingaspirants.R
import com.dxn.connectingaspirants.ui.screens.MainActivity
import com.dxn.connectingaspirants.ui.theme.ConnectingAspirantsTheme
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var facebookLoginIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var callbackManager: CallbackManager

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var fireStoreDb: FirebaseFirestore

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectingAspirantsTheme {
                authViewModel = viewModel()
                AuthScreen(
                    viewModel = authViewModel,
                    signInWithGoogle = { signInWithGoogle() },
                    signInWithEmail = { email, password ->
                        signIn(email, password)
                    },
                    signInWithFacebook = { signInWithFacebook() }
                )
            }
        }
        setupFacebookLogin()
        setupGoogleLogin()
    }

    override fun onStart() {
        super.onStart()
        auth.currentUser?.let { navigateToMainActivity() }
    }

    private fun setupGoogleLogin() {
        googleSignInClient = GoogleSignIn.getClient(
            this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(resources.getString(R.string.web_client_id))
                .requestEmail()
                .build()
        )
        signInIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    try {
                        val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                            .getResult(ApiException::class.java)
                        val credential =
                            GoogleAuthProvider.getCredential(account?.idToken, null)
                        auth.signInWithCredential(credential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    authViewModel.createUser()
                                    navigateToMainActivity()
                                } else {
                                    throw Throwable(task.exception)
                                }
                            }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    handleFacebookAccessToken(result!!.accessToken)
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {

                }
            })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    authViewModel.createUser()
                    navigateToMainActivity()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(
            this,
            callbackManager,
            listOf("public_profile", "email")
        )
    }

    private fun signInWithGoogle() {
        signInIntentLauncher.launch(googleSignInClient.signInIntent)
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    authViewModel.createUser()
                    navigateToMainActivity()
                } else {
                    throw Throwable(task.exception)
                }
            }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        googleSignInClient.revokeAccess()
        finish()
    }

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }
}

