package com.dangerfield.features.matchmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dangerfield.core.common.doNothing
import com.dangerfield.core.designsystem.theme.MifflinTheme
import com.dangerfield.core.people.api.Person
import com.dangerfield.core.people.api.ProfileSection
import com.dangerfield.core.ui.debugPlaceholder
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Empty
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Failed
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Idle
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Loading
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Showing
import com.dangerfield.features.matchmaker.MatchMakerViewModel.State
import com.dangerfield.mifflin.features.matchmaker.R

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MatchMakerScreen(
    viewModel: MatchMakerViewModel,
    onError: (Throwable) -> Unit
) {
    val state by viewModel.stateStream.collectAsStateWithLifecycle()
    MatchMakerScreenContent(
        state = state,
        onNext = viewModel::loadNextPerson,
        onReload = viewModel::loadPeople,
        onError = {
            viewModel.onErrorHandled()
            onError(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MatchMakerScreenContent(
    state: State,
    onNext: (prevId: Int) -> Unit,
    onReload: () -> Unit,
    onError: (Throwable) -> Unit
) {
    Scaffold {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Red)
        ) {

            val isFailed = state.peopleStatus is Failed

            LaunchedEffect(key1 = isFailed) {
                if (isFailed) {
                    (state.peopleStatus as? Failed)?.let { status ->
                        onError(status.throwable)
                    }
                }
            }

            when (val status = state.peopleStatus) {
                Idle -> doNothing()
                Empty -> NoMorePeople(onReload)
                is Failed -> doNothing()
                Loading -> Loading()
                is Showing -> PersonProfile(
                    status.potentialMatch,
                    state.profileOrder,
                    onNext = onNext
                )
            }
        }
    }
}

@Composable
private fun PersonProfile(
    person: Person,
    profileSectionOrder: List<ProfileSection>,
    onNext: (prevId: Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        profileSectionOrder.map { section ->
            when (section) {
                ProfileSection.Name -> Text(person.name)
                ProfileSection.Photo -> {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = person.photo,
                        placeholder = debugPlaceholder(debugPreview = R.drawable.low_res_image_placeholder),
                        contentDescription = "Picture of $${person.name}"
                    )
                }
                ProfileSection.Gender -> Text(person.gender)
                ProfileSection.About -> Text(person.about)
                ProfileSection.School -> person.school?.let { Text(it) }
                ProfileSection.Hobbies -> person.hobbies?.let { Text(it.joinToString { "," }) }
            }
        }

        Button(onClick = { onNext(person.id) }) {
            Text(text = "Next")
        }
    }
}

@Composable
private fun NoMorePeople(
    onReload: () -> Unit
) {
    Column {
        Text("No more people in your area")
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
                Showing(
                    Person(
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
            onError = {}
        )
    }
}
