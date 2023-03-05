package com.dangerfield.core.people

import api.User
import local.UserEntity
import remote.UserNetworkEntity

fun UserNetworkEntity.toLocalModel(): UserEntity = UserEntity(
    about = about,
    gender = gender,
    id = id,
    name = name,
    photo = photo,
    hobbies = hobbies ?: emptyList(),
    school = school
)

fun UserEntity.toDomainModel(): User = User(
    about = about,
    gender = gender,
    id = id,
    name = name,
    photo = photo,
    hobbies = hobbies,
    school = school
)
