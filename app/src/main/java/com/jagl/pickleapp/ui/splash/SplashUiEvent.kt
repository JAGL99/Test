package com.jagl.pickleapp.ui.splash

sealed class SplashUiEvent {
    data object NavigateToLogin : SplashUiEvent()
    data object NavigateToHome : SplashUiEvent()
    data object NavigateToGoogleLogin : SplashUiEvent()
}
