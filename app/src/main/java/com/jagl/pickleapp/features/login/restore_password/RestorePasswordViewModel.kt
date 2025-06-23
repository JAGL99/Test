package com.jagl.pickleapp.features.login.restore_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.pickleapp.core.utils.ui.utils.EmailUtils
import com.jagl.pickleapp.domain.usecase.firebase.ResetPasswordWithFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestorePasswordViewModel @Inject constructor(
    private val resetPasswordWithFirebase: ResetPasswordWithFirebase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestorePasswordUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RestorePasswordUiEvent>()
    val uiEvent
        get() = _uiEvent.asSharedFlow()

    fun onEvent(event: RestorePasswordUiEvent) {
        when (event) {
            is RestorePasswordUiEvent.OnEmailChange -> updateEmail(event.email)
            RestorePasswordUiEvent.OnNavigateToLogin -> onNavigateToLogin()
            RestorePasswordUiEvent.OnRestorePassword -> restorePassword()
        }
    }

    private fun restorePassword() = viewModelScope.launch {
        val email = _uiState.value.email
        resetPasswordWithFirebase(
            email = email,
            onSuccess = {
                _uiState.update { it.copy(successMessage = "Password reset email sent") }
                onNavigateToLogin()
            },
            onError = { errorMessage ->
                _uiState.update { it.copy(errorMessage = errorMessage) }
            }
        )
    }


    private fun evaluateRestoreButton() {
        val currentState = _uiState.value
        val isEmailValid = currentState.emailError == null
        _uiState.update { it.copy(enabledRestoreButton = isEmailValid) }
    }

    private fun onNavigateToLogin() = viewModelScope.launch {
        _uiEvent.emit(RestorePasswordUiEvent.OnNavigateToLogin)
    }

    private fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = EmailUtils.evaluateInvalidMessage(email),
            )
        }
        evaluateRestoreButton()
    }
}