package api

import com.dangerfield.core.common.Backoff

interface UserRepository {

    suspend fun getNextUsers(backoff: Backoff = Backoff()): List<User>

    suspend fun setUserSeen(id: Int)
}
