package com.dangerfield.mifflin.error

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.mifflin.R

@Composable
fun GlobalErrorScreen(
    isRetryable: Boolean,
    internalCode: Int,
    onDismiss: ((Boolean) -> Unit) = {},
) {

    val message = stringResource(id = R.string.global_error_message, "\nError Code: $internalCode")

    GlobalErrorScreenContent(
        onDismiss = onDismiss,
        isRetryable = isRetryable,
        message = message,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GlobalErrorScreenContent(
    isRetryable: Boolean,
    message: String,
    onDismiss: ((retry: Boolean) -> Unit),
) {
    Scaffold {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Red)
        ) {

            Text(text = message)
            if (isRetryable) {
                Button(onClick = { onDismiss(true) }) {
                    Text(text = "RETRY")
                }
            } else {
                Button(onClick = { onDismiss(false) }) {
                    Text(text = "OKAY")
                }
            }
        }
    }
}

@Composable
@Preview
private fun GlobalErrorScreenContentPreview() {
    MifflinTheme {
        GlobalErrorScreenContent(
            isRetryable = true,
            message = "Hmmm... something seems to have went wrong",
            onDismiss = {}
        )
    }
}
