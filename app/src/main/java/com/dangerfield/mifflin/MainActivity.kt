package com.dangerfield.mifflin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dangerfield.core.common.collectWhileStarted
import com.dangerfield.mifflin.MainActivityViewModel.State
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        var uiState: State by mutableStateOf(State(isLoading = true))

        collectWhileStarted(viewModel.stateStream) {
            Timber.i("New Main Activity State: is loading ${it.isLoading}")
            uiState = it
        }

        viewModel.initializeApp()

        // Keeps the splash screen up while the state is loading
        splashScreen.setKeepOnScreenCondition { uiState.isLoading }

        setContent {
            MifflinApp()
        }
    }
}
