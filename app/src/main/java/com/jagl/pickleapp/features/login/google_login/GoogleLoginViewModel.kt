package com.jagl.pickleapp.features.login.google_login

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.domain.usecase.google.GetGoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class GoogleLoginViewModel @Inject constructor(
    private val getGoogleSignInClient: GetGoogleSignInClient,
    private val firebaseAuth: FirebaseAuth,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoogleLoginUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<GoogleLoginUiEvent>()
    val uiEvent
        get() = _uiEvent.asSharedFlow()


    fun onEvent(event: GoogleLoginUiEvent) {
        when (event) {
            GoogleLoginUiEvent.NavigateToHome -> onNavigation(event)
            GoogleLoginUiEvent.LaunchGoogleRequest -> launchGoogleRequest()
            is GoogleLoginUiEvent.SignInWithGoogle -> signInWithGoogle(event.account)
        }
    }

    fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>): GoogleSignInAccount? {
        return try {
            val account = task.getResult(ApiException::class.java)
            account
        } catch (e: Exception) {
            e.printStackTrace()
            showError(e.message ?: "An error occurred during Google sign-in")
            null
        }
    }

    private fun signInWithGoogle(account: AuthCredential) =
        viewModelScope.launch(dispatcherProvider.default) {
            try {
                val firebaseUser = firebaseAuth.signInWithCredential(account).await()
                if (firebaseUser.user == null) {
                    showError("Failed to sign in with Google. User is null.")
                } else {
                    firebaseUser.user!!
                    onNavigation(GoogleLoginUiEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError(e.message ?: "An error occurred during Google sign-in")
            }
        }

    private fun onNavigation(event: GoogleLoginUiEvent) =
        viewModelScope.launch { _uiEvent.emit(event) }

    private fun launchGoogleRequest() = viewModelScope.launch {
        _uiEvent.emit(GoogleLoginUiEvent.LaunchGoogleRequest)
    }

    private fun showError(message: String) {
        _uiState.update {
            it.copy(
                errorMessage = message
            )
        }
    }

    fun getSignInIntent(
        launcher: ActivityResultLauncher<Intent>
    ) {
        getGoogleSignInClient(
            onSuccess = { launcher.launch(it.signInIntent) },
            onError = { message -> showError(message) }
        )
    }
}