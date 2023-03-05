package com.dangerfield.core.people.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PersonDao {

    @Upsert
    suspend fun upsertPeople(people: List<PersonEntity>)

    @Query("DELETE FROM USER WHERE id = :id")
    suspend fun deletePerson(id: Int)
}
