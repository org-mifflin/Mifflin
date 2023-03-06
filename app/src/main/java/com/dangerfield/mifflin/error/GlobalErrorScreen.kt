package com.dangerfield.mifflin.error

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dangerfield.core.designsystem.components.BasicButton
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
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Column() {
                Text(
                    text = "Uh oh....",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            BasicButton(
                onClick = { onDismiss(isRetryable) },
                text = if (isRetryable) "Retry" else "Okay"
            )
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
