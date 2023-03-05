package com.dangerfield.core.common

import kotlinx.coroutines.TimeoutCancellationException
import java.util.concurrent.CancellationException

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

fun <T> Result<T>.onCanceled(block: () -> Unit) {
    this.onFailure {
        if (it is CancellationException) {
            block()
        }
    }
}

fun <T> Result<T>.onTimeout(block: () -> Unit) {
    this.onFailure {
        if (it is TimeoutCancellationException) {
            block()
        }
    }
}
