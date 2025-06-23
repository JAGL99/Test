package com.jagl.pickleapp.domain.usecase.firebase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignInWithFirebase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            onSuccess()
                        } else {
                            onError("User not found")
                        }
                    } else {
                        val message = task.exception?.message ?: "Login failed"
                        onError(message)
                    }
                }
                .addOnFailureListener { exception ->
                    onError(exception.message ?: "Login failed")
                }
        } catch (e: Exception) {
            onError(e.message ?: "An error occurred during sign-in")
        }

    }
}