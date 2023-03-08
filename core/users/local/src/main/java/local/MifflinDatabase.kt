package local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * main app database
 */
@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MifflinDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
