package com.dangerfield.core.people.remote

import retrofit2.http.GET

interface PersonService {

    @GET("users")
    suspend fun getPeople(): PeopleResponse
}
