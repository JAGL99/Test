package com.jagl.pickleapp.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.core.utils.ui.components.FullScreenLoading

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToGoogleLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiEvent = viewModel.uiEvent

    LaunchedEffect(uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                SplashUiEvent.NavigateToHome -> onNavigateToHome()
                SplashUiEvent.NavigateToLogin -> onNavigateToLogin()
                SplashUiEvent.NavigateToGoogleLogin -> onNavigateToGoogleLogin()
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        SplashContent(
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun SplashContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        FullScreenLoading()
    }
}

@Composable
@Preview(showBackground = true)
fun SplashContentPreview() {
    SplashContent()
}