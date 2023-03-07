package com.dangerfield.features.matchmaker.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import api.ProfileSection
import api.User
import com.dangerfield.core.designsystem.components.BasicButton
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.features.matchmaker.ui.components.NameSection
import com.dangerfield.features.matchmaker.ui.components.PhotoSection
import com.dangerfield.features.matchmaker.ui.components.ProfileIconCard
import com.dangerfield.features.matchmaker.ui.components.ProfilePromptCard
import com.dangerfield.mifflin.features.matchmaker.R
import kotlinx.coroutines.launch

@Composable
fun UserProfile(
    user: User,
    profileSectionOrder: List<ProfileSection>,
    onNext: (prevId: Int) -> Unit,
    onScroll: (Int, Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    if (scrollState.isScrollInProgress) {
        val scrollPercent = (scrollState.value.toFloat() / scrollState.maxValue.toFloat()) * 100
        onScroll(scrollPercent.toInt(), user.id)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 10.dp)
    ) {

        Sections(profileSectionOrder, user)
        BasicButton(onClick = {
            onNext(user.id)
            scope.launch { scrollState.scrollTo(0) }
        }, text = stringResource(R.string.next))

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Sections(profileSectionOrder: List<ProfileSection>, user: User) {
    Spacer(modifier = Modifier.height(32.dp))
    profileSectionOrder.mapNotNull { section ->
        when (section) {
            ProfileSection.Name -> user.name?.let {
                NameSection(it, stringResource(R.string.profile_options_content_description, it))
            }
            ProfileSection.Photo -> user.photo?.let {
                PhotoSection(
                    url = it,
                    contentDescription = stringResource(
                        id = R.string.profile_photo_content_description,
                        user.name ?: stringResource(R.string.user)
                    )
                )
            }
            ProfileSection.About -> user.about?.let {
                ProfilePromptCard(stringResource(R.string.about_me_prompt), it)
            }
            ProfileSection.Gender -> user.gender?.let {
                ProfilePromptCard(
                    stringResource(
                        R.string.gender_identity_prompt
                    ),
                    it.toReadableGender(LocalContext.current)
                )
            }
            ProfileSection.School -> user.school?.let {
                val schoolIcon = painterResource(id = R.drawable.graduation_hat)
                ProfileIconCard(
                    painter = schoolIcon,
                    text = it,
                    contentDescription = stringResource(R.string.school_icon_content_description)
                )
            }
            ProfileSection.Hobbies -> user.hobbies?.let {
                if (it.isNotEmpty()) {
                    ProfilePromptCard(stringResource(R.string.hobbies_prompt), it.joinToString(", "))
                }
            }
        }?.let {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun String.toReadableGender(context: Context): String =
    when {
        this.contains("man", ignoreCase = true) ||
            this == "m" ||
            this == "M" ||
            this == "male" ||
            this == "Male" -> {
            context.getString(R.string.male)
        }
        this.contains("woman", ignoreCase = true) ||
            this == "f" ||
            this == "F" ||
            this == "female" ||
            this == "Female" -> {
            context.getString(R.string.female)
        }

        else -> this
    }

@Composable
@Preview
private fun MatchMakerScreenContentPreview() {
    MifflinTheme {
        Surface {
            UserProfile(
                user = User(
                    about = """
                    "I'm a tech wizard disguised as a human, with a love for code and a talent for turning coffee into 
                    innovation. When I'm not drinking coffee, I can be found rescuing cats from trees and juggling 
                    flaming bowling pins. If you're looking for a teammate who can bring creativity and a touch of 
                    insanity to your project, look no further. But be warned: working with me may lead to 
                    uncontrollable laughter and a newfound love for all things tech.",
                    """.trimIndent(),
                    gender = "Male",
                    id = 0,
                    name = "Elijah",
                    photo = "https://tinyurl.com/grey-place-holder-photo",
                    hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                    school = "Bikini Bottom Boating School"
                ),
                profileSectionOrder = listOf(
                    ProfileSection.Name,
                    ProfileSection.Photo,
                    ProfileSection.About,
                    ProfileSection.Gender,
                    ProfileSection.School,
                    ProfileSection.Hobbies,
                ),
                onNext = {},
                onScroll = { _, _ -> }
            )
        }
    }
}
