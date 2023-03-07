package com.dangerfield.mifflin

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dangerfield.mifflin.error.GlobalErrorScreen
import org.junit.Rule
import org.junit.Test

class GlobalErrorScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val retryButtonMatcher by lazy {
        hasText(composeTestRule.activity.resources.getString(R.string.retry))
    }

    private val okayButtonMatcher by lazy {
        hasText(composeTestRule.activity.resources.getString(R.string.okay))
    }

    @Test
    fun `GIVEN_error_retryable_WHEN_rendering_THEN_show_retry`() {

        composeTestRule.setContent {
            BoxWithConstraints {
                GlobalErrorScreen(
                    isRetryable = true,
                    internalCode = 0,
                    onDismiss = {}
                )
            }
        }

        composeTestRule
            .onNode(retryButtonMatcher)
            .assertExists()

        composeTestRule
            .onNode(okayButtonMatcher)
            .assertDoesNotExist()
    }

    @Test
    fun `GIVEN_error_not_retryable_WHEN_rendering_THEN_show_okay`() {

        composeTestRule.setContent {
            BoxWithConstraints {
                GlobalErrorScreen(
                    isRetryable = false,
                    internalCode = 0,
                    onDismiss = {}
                )
            }
        }

        composeTestRule
            .onNode(retryButtonMatcher)
            .assertDoesNotExist()

        composeTestRule
            .onNode(okayButtonMatcher)
            .assertExists()
    }
}
