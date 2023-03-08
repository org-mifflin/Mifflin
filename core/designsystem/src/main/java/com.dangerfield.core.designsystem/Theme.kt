package com.dangerfield.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dangerfield.mifflin.designsystem.Blue10
import com.dangerfield.mifflin.designsystem.Blue40
import com.dangerfield.mifflin.designsystem.Blue90
import com.dangerfield.mifflin.designsystem.DarkPurpleGray10
import com.dangerfield.mifflin.designsystem.MifflinTypography
import com.dangerfield.mifflin.designsystem.Orange10
import com.dangerfield.mifflin.designsystem.Orange40
import com.dangerfield.mifflin.designsystem.Orange90
import com.dangerfield.mifflin.designsystem.Purple10
import com.dangerfield.mifflin.designsystem.Purple30
import com.dangerfield.mifflin.designsystem.Purple90
import com.dangerfield.mifflin.designsystem.PurpleGray30
import com.dangerfield.mifflin.designsystem.PurpleGray50
import com.dangerfield.mifflin.designsystem.PurpleGray90
import com.dangerfield.mifflin.designsystem.Red10
import com.dangerfield.mifflin.designsystem.Red40
import com.dangerfield.mifflin.designsystem.Red90

private val MifflinColorScheme = lightColorScheme(
    primary = Purple30,
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Blue40,
    onTertiary = Color.White,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Color(0xFFF0F0F0),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = DarkPurpleGray10,
    surfaceVariant = PurpleGray90,
    onSurfaceVariant = PurpleGray30,
    outline = PurpleGray50,
)

@Composable
fun MifflinTheme(content: @Composable() () -> Unit) {

    MaterialTheme(
        colorScheme = MifflinColorScheme,
        typography = MifflinTypography,
        content = content
    )
}
