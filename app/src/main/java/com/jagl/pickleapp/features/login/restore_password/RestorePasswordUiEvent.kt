package com.jagl.pickleapp.features.login.restore_password

sealed class RestorePasswordUiEvent {
    data class OnEmailChange(val email: String) : RestorePasswordUiEvent()
    data object OnRestorePassword : RestorePasswordUiEvent()
    data object OnNavigateToLogin : RestorePasswordUiEvent()
}