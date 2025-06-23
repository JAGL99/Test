package com.jagl.pickleapp.domain.usecase.firebase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LogoutFromFirebase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            firebaseAuth.signOut()
            onSuccess()
        } catch (e: Exception) {
            onError(e.message ?: "An error occurred during logout")
        }
    }
}