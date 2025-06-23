package com.jagl.pickleapp.features.login.login

sealed class LoginUiEvent {
    data object MakeLogin : LoginUiEvent()
    data object NavigateToSignUp : LoginUiEvent()
    data object NavigateToHome : LoginUiEvent()
    data object NavigateToForgotPassword : LoginUiEvent()
    data class UpdateEmail(val newValue: String) : LoginUiEvent()
    data class UpdatePassword(val newValue: String) : LoginUiEvent()
}