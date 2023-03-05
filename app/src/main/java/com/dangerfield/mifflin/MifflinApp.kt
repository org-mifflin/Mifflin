package com.dangerfield.mifflin

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.features.matchmaker.MatchMakerScreen
import com.dangerfield.features.matchmaker.MatchMakerViewModel
import com.dangerfield.mifflin.error.GlobalErrorScreen

@Composable
fun MifflinApp() {
    MifflinTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.Matchmaker.route) {

                composable(Screen.Matchmaker.route) {
                    val viewModel: MatchMakerViewModel = hiltViewModel()
                    val shouldRetry = it.savedStateHandle.get<Boolean>(ShouldRetryKey) ?: false
                    LaunchedEffect(key1 = shouldRetry) { if (shouldRetry) viewModel.loadPeople() }
                    MatchMakerScreen(
                        viewModel,
                        onError = { throwable ->
                            navController.navigate(
                                Screen.GlobalError.route +
                                    "?$InternalCodeNavArg=${throwable.internalCode}" +
                                    "&$IsRetryableNavArg=${throwable.isRetryable}"
                            )
                        }
                    )
                }

                composable(
                    Screen.GlobalError.route +
                        "?$InternalCodeNavArg={$InternalCodeNavArg}" +
                        "&$IsRetryableNavArg={$IsRetryableNavArg}",
                    arguments = listOf(
                        navArgument(InternalCodeNavArg) {
                            type = NavType.IntType
                            defaultValue = InternalCodeDefaultValue
                        },
                        navArgument(IsRetryableNavArg) {
                            type = NavType.BoolType
                            defaultValue = IsRetryableDefaultValue
                        }
                    )
                ) {
                    val internalCode = it.arguments?.getInt(InternalCodeNavArg) ?: InternalCodeDefaultValue
                    val isRetryable = it.arguments?.getBoolean(IsRetryableNavArg) ?: IsRetryableDefaultValue

                    GlobalErrorScreen(
                        internalCode = internalCode,
                        isRetryable = isRetryable,
                        onDismiss = { shouldRetry ->
                            navController.previousBackStackEntry?.savedStateHandle?.set(ShouldRetryKey, shouldRetry)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
