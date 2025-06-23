package com.jagl.pickleapp.domain.usecase.firebase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CreateUserWithFirebase @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    operator fun invoke(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            onSuccess()
                        } else {
                            onError("User registration failed, please try again")
                        }
                    } else {
                        onError(task.exception?.message ?: "Registration failed")
                    }
                }.addOnFailureListener { exception ->
                    onError(exception.message ?: "An error occurred during registration")
                }
        } catch (e: Exception) {
            onError(e.message ?: "An error occurred during registration")
        }

    }
}