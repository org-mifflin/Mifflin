package com.dangerfield.mifflin

const val InternalCodeNavArg = "internalCode"
const val InternalCodeDefaultValue = 123
const val IsRetryableNavArg = "isRetryable"
const val IsRetryableDefaultValue = false

sealed class Screen(val route: String) {
    object Matchmaker : Screen("matchmaker")
    object GlobalError : Screen("globalerror")
}
