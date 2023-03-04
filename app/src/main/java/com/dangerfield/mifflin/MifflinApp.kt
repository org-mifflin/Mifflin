package com.dangerfield.mifflin

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.features.matchmaker.MatchMakerScreen

@Composable
fun MifflinApp() {
    MifflinTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Matchmaker.route) {
                composable(Screen.Matchmaker.route) {
                    MatchMakerScreen()
                }
            }
        }
    }
}
