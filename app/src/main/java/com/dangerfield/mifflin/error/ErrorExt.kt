package com.dangerfield.mifflin

import retrofit2.HttpException

private val ServerErrorRange = 500..599
private const val TimeoutResponseCode = 408
const val ShouldRetryKey = "SHOULD_RETRY"

val Throwable.isRetryable: Boolean
    get() = when (this) {
        is HttpException -> this.isRetryableHttpException()
        else -> true
    }

private fun HttpException.isRetryableHttpException(): Boolean {
    return this.code() in ServerErrorRange || this.code() == TimeoutResponseCode
}

// known errors get an internal error code.
// Silent errors cant escape if we log them and users report them.
val Throwable.internalCode: Int
    get() = when (this) {
        is HttpException -> 83
        else -> 302
    }
