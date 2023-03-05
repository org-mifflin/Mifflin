package com.dangerfield.features.matchmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import api.ProfileSection
import api.User
import coil.compose.AsyncImage
import com.dangerfield.core.common.doNothing
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.core.ui.ComposableLifecycle
import com.dangerfield.core.ui.debugPlaceholder
import com.dangerfield.features.matchmaker.MatchMakerViewModel.State
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Empty
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Failed
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Idle
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Loaded
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Loading
import com.dangerfield.mifflin.features.matchmaker.R
import kotlinx.coroutines.launch

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
        onScroll = { position, id ->
            viewModel.trackProfileScroll(position, id)
        }
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
                .background(Color.Red)
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
private fun UserProfile(
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
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        profileSectionOrder.map { section ->
            when (section) {
                ProfileSection.Name -> user.name?.let { Text(it) }
                ProfileSection.Photo -> user.photo?.let { url ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Green),
                        model = url,
                        contentScale = ContentScale.FillWidth,
                        placeholder = debugPlaceholder(debugPreview = R.drawable.low_res_image_placeholder),
                        contentDescription = "Picture of $${user.name}"
                    )
                }
                ProfileSection.Gender -> user.gender?.let { Text(it) }
                ProfileSection.About -> user.about?.let { Text(it) }
                ProfileSection.School -> user.school?.let { Text(it) }
                ProfileSection.Hobbies -> user.hobbies?.let { Text(it.joinToString { "," }) }
            }
        }

        Button(
            onClick = {
                onNext(user.id)
                scope.launch { scrollState.scrollTo(0) }
            }
        ) {
            Text(text = "Next")
        }
    }
}

@Composable
private fun NoMorePeople(
    onReload: () -> Unit
) {
    Column {
        Text("No more users in your area")
        Button(onClick = onReload) {
            Text(text = "Reload")
        }
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
