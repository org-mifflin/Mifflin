package com.dangerfield.core.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

/**
 * utility function to wrap asynchronous code with error catching
 * and return result wrapped in a kotlin [Result]
 *
 * does not catch [CancellationException] other than [TimeoutCancellationException]
 */
@Suppress("TooGenericExceptionCaught")
suspend fun <R> runCancellableCatching(block: suspend () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

/**
 * convenience function to provide a callback for when a coroutine wrapped in a [Result]
 * timesout
 */
fun <T> Result<T>.onTimeout(block: () -> Unit): Result<T> {
    return this.onFailure {
        if (it is TimeoutCancellationException) {
            block()
        }
    }
}

/**
 * utility function to wrap asynchronous code with error catching
 * and return result wrapped in a kotlin [Result]
 *
 * @param backOff BackOff object to specify how to handle failure retrys
 *
 * does not catch [CancellationException] other than [TimeoutCancellationException]
 */
suspend fun <T> runCancellableCatching(
    backOff: Backoff?,
    block: suspend () -> T
): Result<T> {
    backOff?.let {
        var currentDelay = backOff.initialDelay
        repeat(backOff.times - 1) {
            val result = runCancellableCatching { block() }
                .onFailure { Timber.e(it) }
                .getOrNull()

            result?.let { return Result.success(it) }
            delay(currentDelay)
            currentDelay = (currentDelay * backOff.factor).toLong().coerceAtMost(backOff.maxDelay)
        }
    }

    return runCancellableCatching { block() }
}

/**
 * utility function to being collecting a flow when the base LifecycleOwner
 * reaches the started state and stop when it reaches the stopped state
 *
 * catches and logs errors by default
 */
inline fun <T> LifecycleOwner.collectWhileStarted(
    flow: Flow<T>,
    crossinline onError: (Throwable) -> Unit = { Timber.e(it) },
    crossinline onSuccess: suspend (T) -> Unit
): Job =
    lifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle)
            .catch { onError(it) }
            .collectLatest { onSuccess(it) }
    }
