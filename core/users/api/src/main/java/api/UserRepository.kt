package api

interface UserRepository {

    suspend fun getNextUsers(): List<User>

    suspend fun setUserSeen(id: Int)
}
