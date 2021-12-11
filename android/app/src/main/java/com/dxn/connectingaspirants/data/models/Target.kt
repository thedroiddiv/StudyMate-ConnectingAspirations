package com.dxn.connectingaspirants.data.models

enum class Target {
    ALL, UPSC, JEE, NEET, GATE, PROGRAMMING
}

sealed class TargetParameters(val parameters: List<String>) {
    object UPSC : TargetParameters(listOf("Speed", "Accuracy", "Language"))
    object JEE : TargetParameters(listOf("Physics", "Chemistry", "Maths", "Accuracy", "Speed"))
    object NEET :
        TargetParameters(listOf("Physics", "Chemistry", "Zoology", "Botany", "Accuracy", "Speed"))

    object GATE : TargetParameters(listOf("Speed", "Accuracy", "Language"))
    object PROGRAMMING :
        TargetParameters(listOf("Speed", "Accuracy", "Time Complexity", "Space Complexity"))

    companion object {
        fun getTargetParams(target: Target): TargetParameters? {
            return when (target) {
                Target.JEE -> JEE
                Target.UPSC -> UPSC
                Target.NEET -> NEET
                Target.GATE -> GATE
                Target.PROGRAMMING -> PROGRAMMING
                else -> null
            }
        }
    }
}

