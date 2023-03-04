package com.dangerfield.features.matchmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.dangerfield.core.designsystem.theme.MifflinTheme

@Composable
fun MatchMakerScreen() {
    MatchMakerScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatchMakerScreenContent() {
    Scaffold {
        Column(Modifier.padding(it).fillMaxSize().background(Color.Red)) {
        }
    }
}

@Composable
@Preview
private fun MatchMakerScreenContentPreview() {
    MifflinTheme {
        MatchMakerScreenContent()
    }
}
