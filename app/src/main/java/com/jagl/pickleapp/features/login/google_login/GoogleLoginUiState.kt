package com.jagl.pickleapp.features.login.google_login

data class GoogleLoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)