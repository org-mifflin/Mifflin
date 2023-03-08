package api

/**
 * Represents a distinct section of a users profile
 */
enum class ProfileSection(val sectionName: String) {
    Name("name"),
    Photo("photo"),
    Gender("gender"),
    About("about"),
    School("school"),
    Hobbies("hobbies")
}
