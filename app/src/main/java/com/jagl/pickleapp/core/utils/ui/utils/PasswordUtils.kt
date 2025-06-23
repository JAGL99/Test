package com.jagl.pickleapp.core.utils.ui.utils

object PasswordUtils {

    fun evaluateInvalidPassword(password: String): String? {
        return when {
            (password.isBlank()) -> "Password cannot be empty"
            (password.trim().length < 6) -> "Password must be at least 6 characters long"
            else -> null
        }
    }

    fun evaluateInvalidPasswordConfirmation(password: String, confirmation: String): String? {
        return when {
            (confirmation.isBlank()) -> "Password confirmation cannot be empty"
            (confirmation != password) -> "Passwords do not match"
            else -> null
        }
    }
}