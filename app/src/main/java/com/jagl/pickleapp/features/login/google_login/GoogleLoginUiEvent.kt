package com.jagl.pickleapp.features.login.google_login

import com.google.firebase.auth.AuthCredential

sealed class GoogleLoginUiEvent {
    data object LaunchGoogleRequest : GoogleLoginUiEvent()
    data object NavigateToHome : GoogleLoginUiEvent()
    data class SignInWithGoogle(val account: AuthCredential) : GoogleLoginUiEvent()
}