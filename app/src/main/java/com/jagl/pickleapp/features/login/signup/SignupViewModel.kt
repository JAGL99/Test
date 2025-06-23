package com.jagl.pickleapp.features.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.utils.ui.utils.EmailUtils
import com.jagl.pickleapp.core.utils.ui.utils.PasswordUtils
import com.jagl.pickleapp.core.utils.ui.utils.PasswordUtils.evaluateInvalidPassword
import com.jagl.pickleapp.domain.usecase.firebase.CreateUserWithFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createUserWithFirebase: CreateUserWithFirebase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SignupUiEvent>()
    val uiEvent
        get() = _uiEvent.asSharedFlow()

    fun onEvent(event: SignupUiEvent) {
        when (event) {
            is SignupUiEvent.NavigateToLogin -> onNavigate()
            SignupUiEvent.RegisterUser -> onRegisterUser()
            is SignupUiEvent.UpdateConfirmPassword -> updateConfirmPassword(event.newValue)
            is SignupUiEvent.UpdateEmail -> updateEmail(event.newValue)
            is SignupUiEvent.UpdatePassword -> updatePassword(event.newValue)
        }
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
        evaluatePasswords()
        evaluateRegisterButton()
    }

    private fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = EmailUtils.evaluateInvalidMessage(email)
            )
        }
        evaluateRegisterButton()

    }

    private fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = evaluateInvalidPassword(password)
            )
        }
        evaluatePasswords()
        evaluateRegisterButton()
    }

    private fun evaluateRegisterButton() {
        val state = _uiState.value
        val hasNoError = listOf(
            state.emailError,
            state.passwordError,
            state.confirmPasswordError
        ).none { it != null }
        _uiState.update {
            it.copy(enableRegisterButton = hasNoError)
        }
    }

    private fun evaluatePasswords() {
        _uiState.update {
            it.copy(
                confirmPasswordError = PasswordUtils.evaluateInvalidPasswordConfirmation(
                    password = it.password,
                    confirmation = it.confirmPassword
                )
            )
        }
    }

    private fun onRegisterUser() = viewModelScope.launch(dispatcherProvider.io) {
        val email = _uiState.value.email
        val password = _uiState.value.password
        createUserWithFirebase(
            email = email,
            password = password,
            onSuccess = {
                _uiState.update {
                    it.copy(successMessage = "User registered successfully")
                }
                onNavigate()
            },
            onError = { errorMessage ->
                showError(errorMessage)
            }
        )
    }

    private fun showError(errorMessage: String) {
        _uiState.update {
            it.copy(errorMessage = errorMessage)
        }
    }

    private fun onNavigate() = viewModelScope.launch {
        _uiEvent.emit(SignupUiEvent.NavigateToLogin)
    }

}