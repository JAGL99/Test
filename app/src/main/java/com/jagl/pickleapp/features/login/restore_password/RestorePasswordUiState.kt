package com.jagl.pickleapp.features.login.restore_password

data class RestorePasswordUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val enabledRestoreButton: Boolean = false,
    val successMessage: String? = null
)