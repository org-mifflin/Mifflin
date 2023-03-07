package com.dangerfield.features.matchmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import api.ProfileSection
import api.User
import com.dangerfield.core.common.doNothing
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.features.matchmaker.MatchMakerViewModel.State
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Empty
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Failed
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Idle
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Loaded
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Loading
import com.dangerfield.features.matchmaker.components.UserProfile
import com.dangerfield.features.matchmaker.ui.NoMoreUsers

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MatchMakerScreen(
    viewModel: MatchMakerViewModel,
    onError: (Throwable) -> Unit
) {
    val state by viewModel.stateStream.collectAsStateWithLifecycle()

    MatchMakerScreenContent(
        state = state,
        onNext = viewModel::loadNextUser,
        onReload = viewModel::loadUsers,
        onError = {
            viewModel.onErrorHandled()
            onError(it)
        },
        onScroll = { position, id -> viewModel.trackProfileScroll(position, id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatchMakerScreenContent(
    state: State,
    onNext: (prevId: Int) -> Unit,
    onReload: () -> Unit,
    onError: (Throwable) -> Unit,
    onScroll: (Int, Int) -> Unit
) {

    Scaffold {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {

            val isFailed = state.userResult is Failed

            LaunchedEffect(key1 = isFailed) {
                if (isFailed) {
                    (state.userResult as? Failed)?.let { status ->
                        onError(status.throwable)
                    }
                }
            }

            when (val status = state.userResult) {
                Idle -> doNothing()
                Empty -> NoMoreUsers(onReload)
                is Failed -> doNothing()
                Loading -> CircularProgressIndicator()
                is Loaded -> UserProfile(
                    status.user,
                    state.profileOrder,
                    onNext = onNext,
                    onScroll = onScroll
                )
            }
        }
    }
}

@Composable
@Preview
private fun MatchMakerScreenContentPreview() {
    MifflinTheme {
        MatchMakerScreenContent(
            state = State(
                Loaded(
                    User(
                        about = """
                            I'm probably the best coworker you could ask for. But be warned: working with me may be 
                            addictive
                        """.trimIndent(),
                        gender = "Male",
                        id = 0,
                        name = "Elijah",
                        photo = "https://tinyurl.com/grey-place-holder-photo",
                        hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                        school = "Bikini Bottom Boating School"
                    )
                ),
                listOf(
                    ProfileSection.Name,
                    ProfileSection.Photo,
                    ProfileSection.About,
                    ProfileSection.Gender,
                    ProfileSection.School,
                    ProfileSection.Hobbies,
                )
            ),
            onNext = {},
            onReload = {},
            onError = {},
            onScroll = { _, _ -> }
        )
    }
}
