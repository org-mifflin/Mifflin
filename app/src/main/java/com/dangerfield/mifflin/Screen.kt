package com.dangerfield.mifflin

sealed class Screen(val route: String) {
    object Matchmaker : Screen("matchmaker")
}
