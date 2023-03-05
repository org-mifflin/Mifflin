package com.dangerfield.core.people.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PersonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MifflinDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
}
