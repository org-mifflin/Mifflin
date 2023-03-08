package com.dangerfield.core.users

import api.User
import api.UserRepository
import com.dangerfield.core.common.Backoff
import com.dangerfield.core.common.runCancellableCatching
import com.dangerfield.core.people.toDomainModel
import com.dangerfield.core.people.toLocalModel
import local.UserDao
import remote.UserService
import timber.log.Timber
import javax.inject.Inject

class OfflineFirstUserRepository @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) : UserRepository {

    /**
     * fetches the next user batch if the current cached users are empty
     * otherwise returns cached users
     *
     * no paging implementation exists now but ideally we support it with a similar flow
     */
    override suspend fun getNextUsers(backoff: Backoff): List<User> {
        val dbResponse = userDao.getUsers()
        if (dbResponse.isEmpty()) {
            val networkResponse = runCancellableCatching(backoff) {
                userService.getUsers()
            }.onFailure { Timber.e(it) }
                .getOrNull()

            networkResponse?.let { response -> userDao.upsertUsers(response.users.map { it.toLocalModel() }) }

            return userDao.getUsers().map { it.toDomainModel() }
        }

        return dbResponse.map { it.toDomainModel() }
    }

    /**
     * removes a users details
     */
    override suspend fun setUserSeen(id: Int) {
        userDao.deleteUser(id)
    }
}
