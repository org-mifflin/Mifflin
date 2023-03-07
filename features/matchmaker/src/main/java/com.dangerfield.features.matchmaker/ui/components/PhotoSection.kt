package com.dangerfield.features.matchmaker.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.core.ui.debugPlaceholder
import com.dangerfield.mifflin.features.matchmaker.R

@Composable
fun PhotoSection(
    url: String?,
    contentDescription: String,
) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        model = url,
        contentScale = ContentScale.FillWidth,
        placeholder = debugPlaceholder(debugPreview = R.drawable.low_res_image_placeholder),
        contentDescription = contentDescription
    )
}

@Preview
@Composable
fun PreviewPhotoSection() {
    MifflinTheme {
        Surface {
            PhotoSection(null, "Elijah")
        }
    }
}
