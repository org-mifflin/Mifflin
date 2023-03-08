package remote

import com.google.gson.annotations.SerializedName

/**
 * Entity representing a person as returned from our backend
 * prefer nullable fields within reason. Defensive programming saves lives.
 */
data class UserNetworkEntity(
    @SerializedName("about") val about: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("photo") val photo: String?,
    @SerializedName("hobbies") val hobbies: List<String>?,
    @SerializedName("school")val school: String?
)
