package com.dxn.connectingaspirants.data.models

data class User(
    val name:String = "",
    val uid:String = "",
    val level: Level = Level.NEWBIE,
    val target: Target = Target.JEE,
    val rating : Float = 5.0f,
)