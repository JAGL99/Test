package com.jagl.pickleapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.domain.usecase.firebase.GetGoogleSignup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val getGoogleSignup: GetGoogleSignup,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>(replay = 1)
    val uiEvent
        get() = _uiEvent.asSharedFlow()

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() = viewModelScope.launch(dispatcher.default) {
        val isAuthenticated = firebaseAuth.currentUser != null
        if (isAuthenticated) {
            _uiEvent.emit(SplashUiEvent.NavigateToHome)
        } else {
            val isGoogleSignupAvailable = getGoogleSignup()
            if (isGoogleSignupAvailable) {
                _uiEvent.emit(SplashUiEvent.NavigateToGoogleLogin)
            } else {
                _uiEvent.emit(SplashUiEvent.NavigateToLogin)
            }

        }
    }
}