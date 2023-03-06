package com.dangerfield.core.common

/**
 * wrapper for data specifying exponential backoff params
 * @param times: the number of times to retry
 * @param initialDelay: the milliseconds delay to start with in
 * @param maxDelay: the maximum delay to reach
 * @param factor: the multiplier for the backoff growth
 */
data class ExponentialBackoff(
    val times: Int = 10,
    val initialDelay: Long = 100, // 0.1 second
    val maxDelay: Long = 2000, // 1 second
    val factor: Double = 2.0,
)
