package remote

import retrofit2.http.GET

interface UserService {

    @GET("users")
    suspend fun getUsers(): UsersResponse
}
