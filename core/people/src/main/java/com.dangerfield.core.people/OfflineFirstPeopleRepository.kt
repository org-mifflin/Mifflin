package com.dangerfield.core.people

import com.dangerfield.core.people.api.PeopleRepository
import com.dangerfield.core.people.api.Person
import kotlinx.coroutines.delay
import javax.inject.Inject

class OfflineFirstPeopleRepository @Inject constructor() : PeopleRepository {

    override suspend fun getNextPeople(): List<Person> {
        delay(3000)
        return listOf(
            Person(
                about = "lorerdfndsj sdlfkjw thasd asdlfkje sdflkj",
                gender = "Male",
                id = 0,
                name = "Elijah",
                photo = "https://tinyurl.com/grey-place-holder-photo",
                hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                school = "Bikini Bottom Boating School"
            ),
            Person(
                about = "lorerdfndsj sdlfkjw thasd asdlfkje sdflkj",
                gender = "Male",
                id = 1,
                name = "Josiah",
                photo = "https://tinyurl.com/grey-place-holder-photo",
                hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                school = "Bikini Bottom Boating School"
            ),
            Person(
                about = "lorerdfndsj sdlfkjw thasd asdlfkje sdflkj",
                gender = "Male",
                id = 2,
                name = "Arif",
                photo = "https://tinyurl.com/grey-place-holder-photo",
                hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                school = "Bikini Bottom Boating School"
            ),
            Person(
                about = "lorerdfndsj sdlfkjw thasd asdlfkje sdflkj",
                gender = "Male",
                id = 3,
                name = "Michael",
                photo = "https://tinyurl.com/grey-place-holder-photo",
                hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                school = "Bikini Bottom Boating School"
            ),
            Person(
                about = "lorerdfndsj sdlfkjw thasd asdlfkje sdflkj",
                gender = "Male",
                id = 4,
                name = "Nibraas",
                photo = "https://tinyurl.com/grey-place-holder-photo",
                hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                school = "Bikini Bottom Boating School"
            ),
            Person(
                about = "lorerdfndsj sdlfkjw thasd asdlfkje sdflkj",
                gender = "Male",
                id = 4,
                name = "George",
                photo = "https://tinyurl.com/grey-place-holder-photo",
                hobbies = listOf("Guitar", "Coding", "Generally being amazing"),
                school = "Bikini Bottom Boating School"
            ),
        )
    }

    override suspend fun setPersonSeen(id: Int) {
        // do nothing
    }
}
