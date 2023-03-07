package com.dangerfield.core.people

import api.User
import local.UserEntity
import remote.UserNetworkEntity

/**
 * converts a network user entity to a local user entity
 */
fun UserNetworkEntity.toLocalModel(): UserEntity = UserEntity(
    about = about,
    gender = gender,
    id = id,
    name = name,
    photo = photo,
    hobbies = hobbies ?: emptyList(),
    school = school
)

/**
 * converts a local user entity to a domain user entity
 */
fun UserEntity.toDomainModel(): User = User(
    about = about,
    gender = gender,
    id = id,
    name = name,
    photo = photo,
    hobbies = hobbies,
    school = school
)
