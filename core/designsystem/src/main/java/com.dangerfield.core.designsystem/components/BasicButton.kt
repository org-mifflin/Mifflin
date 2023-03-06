package com.dangerfield.core.designsystem.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dangerfield.core.designsystem.theme.MifflinTheme

@Composable
fun BasicButton(onClick: () -> Unit, text: String) {
    Button(
        modifier = Modifier.defaultMinSize(
            minWidth = 200.dp,
            minHeight = 60.dp
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun PreviewBasicButton() {
    MifflinTheme {
        BasicButton({}, "Next")
    }
}
