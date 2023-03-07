package com.dangerfield.features.matchmaker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dangerfield.core.designsystem.components.BasicButton
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.core.ui.DevicePreviews
import com.dangerfield.mifflin.features.matchmaker.R

@Composable
fun NoMoreUsers(
    onReload: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Column() {
            Text(
                text = stringResource(R.string.uh_oh),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.out_of_users),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        BasicButton(
            onClick = onReload,
            text = stringResource(R.string.reload)
        )
    }
}

@Composable
@DevicePreviews
fun PreviewNoMoreUsers() {
    MifflinTheme {
        Surface {
            NoMoreUsers({})
        }
    }
}
