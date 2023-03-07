package com.dangerfield.core.people

import com.dangerfield.core.common.Backoff
import com.dangerfield.core.test.CoroutinesTestRule
import com.dangerfield.core.users.OfflineFirstUserRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import local.UserDao
import local.UserEntity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import remote.UserNetworkEntity
import remote.UserService
import remote.UsersResponse

internal class OfflineFirstUserRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val userService: UserService = mockk()
    private val userDao: UserDao = mockk {
        coEvery { upsertUsers(any()) } just Runs
        coEvery { deleteUser(any()) } just Runs
    }

    lateinit var userRepository: OfflineFirstUserRepository

    @Before
    fun setup() {
        userRepository = OfflineFirstUserRepository(userService, userDao)
    }

    @Test
    fun `GIVEN happy path, WHEN user is seen, THEN user should be removed from db`() = coroutineTestRule.test {
        userRepository.setUserSeen(0)
        coVerify { userDao.deleteUser(0) }
    }

    @Test
    fun `GIVEN db has users, WHEN getting users, DB response should be given`() = coroutineTestRule.test {
        coEvery { userDao.getUsers() } returns listOf(
            UserEntity(
                about = null,
                gender = null,
                id = 0,
                name = null,
                photo = null,
                hobbies = listOf(),
                school = null
            )
        )

        userRepository.getNextUsers()

        coVerify(exactly = 0) { userService.getUsers() }
        coVerify(exactly = 1) { userDao.getUsers() }
    }

    @Test
    fun `GIVEN cache is empty, WHEN fetching users, THEN network response should fill cache`() =
        coroutineTestRule.test {
            val testName = "kevin"
            val networkResponse = UsersResponse(
                listOf(
                    UserNetworkEntity(
                        about = null,
                        gender = null,
                        id = 0,
                        name = testName,
                        photo = null,
                        hobbies = listOf(),
                        school = null
                    )
                )
            )

            coEvery { userDao.getUsers() } returns emptyList()
            coEvery { userService.getUsers() } returns networkResponse

            userRepository.getNextUsers()

            coVerify(exactly = 1) { userService.getUsers() }
            coVerify(exactly = 1) { userDao.upsertUsers(withArg { it.first().name == testName }) }
            // one initial and one for the return
            coVerify(exactly = 2) { userDao.getUsers() }
        }

    @Test
    fun `GIVEN cache is empty, WHEN fetching users fails, THEN retry should kick in with basic back off`() =
        coroutineTestRule.test {

            val backoff = Backoff(times = 3, initialDelay = 0, maxDelay = 0, factor = 1.0)
            coEvery { userDao.getUsers() } returns emptyList()
            coEvery { userService.getUsers() } throws Error()

            userRepository.getNextUsers(backoff)

            coVerify(exactly = 3) { userService.getUsers() }
            // one initial and one for the return
            coVerify(exactly = 2) { userDao.getUsers() }
            coVerify(exactly = 0) { userDao.upsertUsers(any()) }
        }
}
