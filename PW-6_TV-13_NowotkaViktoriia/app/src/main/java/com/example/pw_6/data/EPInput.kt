package com.example.pw_6.data

import kotlinx.serialization.Serializable

@Serializable
data class EPInput(
    var name: String = "",
    var coeffUsefulAct: String = "",
    var coeffPower: String = "",
    var voltage: String = "",
    var count: String = "",
    var capacity: String = "",
    var coefUsage: String = "",
    var coeffReactPower: String = ""
)
