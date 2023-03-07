package com.dangerfield.features.matchmaker

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.rules.ActivityScenarioRule
import api.ProfileSection
import api.User
import com.dangerfield.mifflin.features.matchmaker.R
import org.junit.Rule
import org.junit.Test

class MatchMakerScreenContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val nextButtonMatcher by lazy {
        hasText(composeTestRule.activity.resources.getString(R.string.next))
    }

    private val reloadButtonMatcher by lazy {
        hasText(composeTestRule.activity.resources.getString(R.string.reload))
    }

    @Test
    fun `GIVEN_loading_state_WHEN_rendering_THEN_no_content_displays`() {
        val state = MatchMakerViewModel.State(
            userResult = MatchMakerViewModel.UserResult.Loading,
            profileOrder = listOf()
        )

        composeTestRule.setContent {
            BoxWithConstraints {
                MatchMakerScreenContent(
                    state = state,
                    onNext = {},
                    onReload = {},
                    onError = {},
                    onScroll = { _, _ -> }
                )
            }
        }

        composeTestRule
            .onNode(nextButtonMatcher)
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.resources.getString(R.string.about_me_prompt))
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.resources.getString(R.string.gender_identity_prompt))
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.resources.getString(R.string.hobbies_prompt))
            .assertDoesNotExist()

        composeTestRule.schoolSection().assertDoesNotExist()
    }

    @Test
    fun `GIVEN_user_state_WHEN_rendering_THEN_content_displays`() {

        val name = "Elijah"
        val profileSectionOrder = listOf(
            ProfileSection.Name,
            ProfileSection.Photo,
            ProfileSection.About,
            ProfileSection.Gender,
            ProfileSection.School,
            ProfileSection.Hobbies,
        )

        val state = MatchMakerViewModel.State(
            userResult = MatchMakerViewModel.UserResult.Loaded(
                User(
                    about = "about me",
                    gender = "male",
                    id = 0,
                    name = name,
                    photo = null,
                    hobbies = listOf("this", "and", "that"),
                    school = "Boating School"
                )
            ),
            profileOrder = profileSectionOrder
        )

        composeTestRule.setContent {
            BoxWithConstraints {
                MatchMakerScreenContent(
                    state = state,
                    onNext = {},
                    onReload = {},
                    onError = {},
                    onScroll = { _, _ -> }
                )
            }
        }

        composeTestRule
            .nameSection("Elijah")
            .assertExists()

        composeTestRule
            .onNode(nextButtonMatcher)
            .performScrollTo()
            .assertExists()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.resources.getString(R.string.about_me_prompt))
            .assertExists()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.resources.getString(R.string.gender_identity_prompt))
            .assertExists()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.resources.getString(R.string.hobbies_prompt))
            .assertExists()

        composeTestRule.schoolSection().assertExists()
    }

    @Test
    fun `GIVEN_no_more_users_WHEN_rendering_THEN_empty_message_displays`() {

        val profileSectionOrder = listOf(
            ProfileSection.Name,
            ProfileSection.Photo,
            ProfileSection.About,
            ProfileSection.Gender,
            ProfileSection.School,
            ProfileSection.Hobbies,
        )

        val state = MatchMakerViewModel.State(
            userResult = MatchMakerViewModel.UserResult.Empty,
            profileOrder = profileSectionOrder
        )

        composeTestRule.setContent {
            BoxWithConstraints {
                MatchMakerScreenContent(
                    state = state,
                    onNext = {},
                    onReload = {},
                    onError = {},
                    onScroll = { _, _ -> }
                )
            }
        }

        composeTestRule
            .onNode(reloadButtonMatcher)
            .assertExists()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.resources.getString(R.string.out_of_users))
            .assertExists()
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.schoolSection() =
        onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(R.string.school_icon_content_description)
        )

    private fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.nameSection(
        name: String
    ) =
        onNodeWithContentDescription(
            composeTestRule.activity.resources.getString(R.string.profile_options_content_description, name)
        )
}
