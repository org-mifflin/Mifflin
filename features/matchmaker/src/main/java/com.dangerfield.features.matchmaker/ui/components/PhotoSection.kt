package com.dangerfield.features.matchmaker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.core.ui.debugPlaceholder
import com.dangerfield.mifflin.features.matchmaker.R

@Composable
fun PhotoSection(
    url: String?,
    contentDescription: String,
) {

    var isLoading by remember { mutableStateOf(true) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .diskCacheKey(url)
            .memoryCacheKey(url)
            .data(url)
            .size(Size.ORIGINAL)
            .build(),
        placeholder = debugPlaceholder(debugPreview = R.drawable.low_res_image_placeholder),
        error = painterResource(id = R.drawable.image_load_failed),
        fallback = painterResource(id = R.drawable.image_load_failed),
        onLoading = null,
        onSuccess = { isLoading = false },
        onError = { isLoading = false },
        contentScale = ContentScale.FillWidth,
        filterQuality = DefaultFilterQuality,
    )

    if (painter.state is AsyncImagePainter.State.Loading) {
        ProfileCard {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    } else {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth,
        )
    }
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
