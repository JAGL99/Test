package com.jagl.pickleapp.core.utils.ui.utils

object EmailUtils {

    fun evaluateInvalidMessage(email: String): String? {
        return if (email.isBlank()) "Email cannot be empty" else
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()
            ) "Invalid email format"
            else null
    }
}