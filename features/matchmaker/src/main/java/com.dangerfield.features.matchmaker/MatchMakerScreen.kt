package com.dangerfield.features.matchmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import api.ProfileSection
import api.User
import com.dangerfield.core.common.doNothing
import com.dangerfield.core.designsystem.components.BasicButton
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.core.ui.ComposableLifecycle
import com.dangerfield.features.matchmaker.MatchMakerViewModel.State
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Empty
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Failed
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Idle
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Loaded
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Loading
import com.dangerfield.features.matchmaker.components.UserProfile

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MatchMakerScreen(
    viewModel: MatchMakerViewModel,
    onError: (Throwable) -> Unit
) {
    val state by viewModel.stateStream.collectAsStateWithLifecycle()

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            viewModel.loadUsers()
        }
    }

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
                Empty -> NoMorePeople(onReload)
                is Failed -> doNothing()
                Loading -> Loading()
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
private fun NoMorePeople(
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
                text = "Uh oh....",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = """
                    "It looks like the only person left is Bob Vance (Vance Refrigeration). 
                    
                    Maybe consider looking outside of Scranton."
                """.trimIndent(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        BasicButton(
            onClick = onReload,
            text = "Reload"
        )
    }
}

@Composable
private fun Loading() {
    CircularProgressIndicator()
}

@Composable
@Preview
private fun MatchMakerScreenContentPreview() {
    MifflinTheme {
        MatchMakerScreenContent(
            state = State(
                Loaded(
                    User(
                        about = "lorerdfndsj sdlfkjw thasd asdlfkje sdflkj",
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
