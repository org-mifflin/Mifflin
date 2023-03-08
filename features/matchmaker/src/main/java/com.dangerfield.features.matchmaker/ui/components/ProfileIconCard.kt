package com.dangerfield.features.matchmaker.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.mifflin.features.matchmaker.R

@Composable
fun ProfileIconCard(
    painter: Painter,
    text: String,
    contentDescription: String
) {

    ProfileCard {
        Row {
            Icon(
                modifier = Modifier.height(25.dp).width(25.dp),
                painter = painter,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewProfileIconCard() {
    MifflinTheme {
        Surface {
            val schoolIcon = painterResource(id = R.drawable.graduation_hat)
            ProfileIconCard(
                schoolIcon, "Some super cool text",
                contentDescription = ""
            )
        }
    }
}
