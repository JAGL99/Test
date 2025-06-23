package com.jagl.pickleapp.core.remote.utils

object RequestUtils {

    suspend fun <T> safeCall(
        code: suspend () -> Result<T>
    ): Result<T> {
        return try {
            code()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}