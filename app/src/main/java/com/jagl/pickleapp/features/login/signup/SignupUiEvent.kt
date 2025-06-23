package com.jagl.pickleapp.features.login.signup

sealed class SignupUiEvent {
    data object NavigateToLogin : SignupUiEvent()
    data object RegisterUser : SignupUiEvent()
    data class UpdateEmail(val newValue: String) : SignupUiEvent()
    data class UpdatePassword(val newValue: String) : SignupUiEvent()
    data class UpdateConfirmPassword(val newValue: String) : SignupUiEvent()
}