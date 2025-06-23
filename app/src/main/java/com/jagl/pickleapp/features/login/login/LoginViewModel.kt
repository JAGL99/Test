package com.jagl.pickleapp.features.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.domain.usecase.firebase.SignInWithFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithFirebase: SignInWithFirebase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent
        get() = _uiEvent.asSharedFlow()


    fun onEvent(event: LoginUiEvent) {
        when (event) {
            LoginUiEvent.NavigateToForgotPassword,
            LoginUiEvent.NavigateToHome,
            LoginUiEvent.NavigateToSignUp -> onNavigation(event)

            LoginUiEvent.MakeLogin -> makeLogin()
            is LoginUiEvent.UpdateEmail -> updateEmail(event.newValue)
            is LoginUiEvent.UpdatePassword -> updatePassword(event.newValue)
        }
    }

    private fun onNavigation(event: LoginUiEvent) = viewModelScope.launch { _uiEvent.emit(event) }

    private fun makeLogin() = viewModelScope.launch(dispatcherProvider.default) {
        _uiState.update {
            it.copy(
                errorMessage = null,
                isLoading = true
            )
        }
        val email = uiState.value.email
        val password = uiState.value.password

        signInWithFirebase(
            email = email,
            password = password,
            onSuccess = {
                onNavigation(LoginUiEvent.NavigateToHome)
            },
            onError = { message ->
                showError(message)
            }
        )
        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
    }

    private fun showError(message: String) {
        _uiState.update {
            it.copy(
                errorMessage = message
            )
        }
    }

    private fun evaluateLoginButton() {
        val validEmail = uiState.value.email.isNotBlank()
        val validPassword = uiState.value.password.isNotBlank()
        _uiState.update { it.copy(isLoginEnabled = validEmail && validPassword) }
    }

    private fun updateEmail(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
        evaluateLoginButton()
    }

    private fun updatePassword(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
        evaluateLoginButton()
    }

}