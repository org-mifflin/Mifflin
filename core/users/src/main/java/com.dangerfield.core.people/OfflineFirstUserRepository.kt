package com.dangerfield.core.users

import api.User
import api.UserRepository
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

    // TODO comment about adapting this to paging
    override suspend fun getNextUsers(): List<User> {
        val networkResponse = runCancellableCatching {
            userService.getUsers()
        }.onFailure { Timber.e(it) }
            .getOrNull()

        networkResponse?.let { response -> userDao.upsertUsers(response.users.map { it.toLocalModel() }) }

        return userDao.getUsers().map { it.toDomainModel() }
    }

    override suspend fun setUserSeen(id: Int) {
        userDao.deleteUser(id)
    }
}
