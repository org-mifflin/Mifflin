package api

data class User(
    val about: String?,
    val gender: String?,
    val id: Int,
    val name: String?,
    val photo: String?,
    val hobbies: List<String>?,
    val school: String?
)
