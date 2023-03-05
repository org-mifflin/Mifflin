package local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "USER")
@TypeConverters(Converters::class)
data class UserEntity(
    val about: String?,
    val gender: String?,
    @PrimaryKey val id: Int,
    val name: String?,
    val photo: String?,
    val hobbies: List<String>?,
    val school: String?
)
