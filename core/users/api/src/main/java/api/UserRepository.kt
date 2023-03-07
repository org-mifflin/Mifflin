package api

import com.dangerfield.core.common.Backoff

/**
 * responsible for fetching users from the backend and marking them as seen
 */
interface UserRepository {

    /**
     * gets the next batch of users
     */
    suspend fun getNextUsers(backoff: Backoff = Backoff()): List<User>

    /**
     * sets the user as seen
     */
    suspend fun setUserSeen(id: Int)
}
