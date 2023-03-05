package com.dangerfield.core.people.api

data class Person(
    val about: String,
    val gender: String,
    val id: Int,
    val name: String,
    val photo: String,
    val hobbies: List<String>?,
    val school: String?
)
