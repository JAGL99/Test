package com.jagl.pickleapp.domain.usecase.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetGoogleSignup @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) {
    suspend operator fun invoke(): Boolean {
        return try {
            firebaseRemoteConfig.fetch(0)
            firebaseRemoteConfig.activate().await()
            return firebaseRemoteConfig.getBoolean("enable_google_signup")
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}