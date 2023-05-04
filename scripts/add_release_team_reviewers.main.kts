#!/usr/bin/env kotlin

@file:DependsOn("com.squareup.okhttp3:okhttp:4.9.3")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

val red = "\u001b[31m"
val green = "\u001b[32m"
val reset = "\u001b[0m"

fun printRed(text: String) {
    println(red + text + reset)
}

fun printGreen(text: String) {
    println(green + text + reset)
}

@Serializable
data class ReviewerRequest(val reviewers: List<String>)

if (args.size < 2 || args[0] in listOf("--h", "--help")) {
    print(
        """
        This script adds the release engineering team to a pull request with the provided id
        
        usage: ./add_release_team_reviewers.main.kts [token] [pr] 
        [token] - the github access token
        [pr] - the number associated with the pull request
        """.trimIndent()
    )

    throw Exception("See Message Above")

} else {
    main()
}

fun main() {
    val client = OkHttpClient()
    val accessToken = args[0]
    val prId = args[1]
    val teamSlug = "android"
    val json = Json.encodeToString(ReviewerRequest(listOf(teamSlug)))

    val request = Request.Builder()
        .url("https://api.github.com/repos/org-mifflin/Mifflin/pulls/$prId/requested_reviewers")
        .addHeader("Authorization", "Bearer $accessToken")
        .addHeader("Content-Type", "application/json")
        .post(json.toRequestBody("application/json; charset=utf-8".toMediaType()))
        .build()

    val response = client.newCall(request).execute()
    if (response.isSuccessful) {
        printGreen("Team successfully added as a reviewer to PR #$prId")
    } else {
        printRed("Failed to add the team as a reviewer: ${response.message}")
    }
}
