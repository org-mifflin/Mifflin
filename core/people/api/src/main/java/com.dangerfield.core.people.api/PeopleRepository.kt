package com.dangerfield.core.people.api

interface PeopleRepository {

    suspend fun getNextPeople(): List<Person>

    suspend fun setPersonSeen(id: Int)
}
