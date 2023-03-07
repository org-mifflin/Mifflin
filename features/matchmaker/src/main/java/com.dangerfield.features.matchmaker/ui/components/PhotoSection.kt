package com.dangerfield.features.matchmaker.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dangerfield.core.ui.debugPlaceholder
import com.dangerfield.mifflin.features.matchmaker.R

@Composable
fun PhotoSection(
    url: String?,
    name: String?,
) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        model = url,
        contentScale = ContentScale.FillWidth,
        placeholder = debugPlaceholder(debugPreview = R.drawable.low_res_image_placeholder),
        contentDescription = "Picture of ${name ?: "user"}"
    )
}
