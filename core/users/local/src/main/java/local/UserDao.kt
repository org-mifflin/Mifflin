package local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUsers(users: List<UserEntity>)

    @Query("DELETE FROM USER WHERE id = :id")
    suspend fun deleteUser(id: Int)

    @Query("SELECT * FROM USER")
    suspend fun getUsers(): List<UserEntity>
}
