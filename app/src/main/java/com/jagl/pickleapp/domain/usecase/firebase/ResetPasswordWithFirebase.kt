package com.jagl.pickleapp.domain.usecase.firebase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ResetPasswordWithFirebase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend operator fun invoke(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            firebaseAuth.sendPasswordResetEmail(email)
            onSuccess()
        } catch (e: Exception) {
            onError(e.message ?: "An error occurred while resetting the password.")
        }
    }

}