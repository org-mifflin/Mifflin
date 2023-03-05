package com.dangerfield.core.people.remote

import com.google.gson.annotations.SerializedName

data class PeopleResponse(
    @SerializedName("users") val people: List<PersonNetworkEntity>
)
