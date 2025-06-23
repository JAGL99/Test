package com.jagl.pickleapp.features.login.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.R
import com.jagl.pickleapp.core.utils.ui.components.FullScreenLoading
import com.jagl.pickleapp.core.utils.ui.components.InputWithError

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiEvent = viewModel.uiEvent
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { errorMessage ->
            Toast.makeText(
                context,
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { successMessage ->
            Toast.makeText(
                context,
                successMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                SignupUiEvent.NavigateToLogin -> onNavigateToLogin()
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        SignupContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
private fun SignupContent(
    modifier: Modifier = Modifier,
    uiState: SignupUiState,
    onEvent: (SignupUiEvent) -> Unit
) {

    if (uiState.isLoading) {
        FullScreenLoading()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        InputWithError(
            value = uiState.email,
            placeholderText = stringResource(R.string.email),
            errorMessage = uiState.emailError,
            onValueChange = { value ->
                val event = SignupUiEvent.UpdateEmail(value)
                onEvent(event)
            },
            isError = uiState.emailError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        InputWithError(
            value = uiState.password,
            placeholderText = stringResource(R.string.password),
            errorMessage = uiState.passwordError,
            onValueChange = { value ->
                val event = SignupUiEvent.UpdatePassword(value)
                onEvent(event)
            },
            isError = uiState.passwordError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        InputWithError(
            value = uiState.confirmPassword,
            placeholderText = stringResource(R.string.confirm_password),
            errorMessage = uiState.confirmPasswordError,
            onValueChange = { value ->
                val event = SignupUiEvent.UpdateConfirmPassword(value)
                onEvent(event)
            },
            isError = uiState.confirmPasswordError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = uiState.enableRegisterButton,
            onClick = { onEvent(SignupUiEvent.RegisterUser) }
        ) {
            Text(text = stringResource(R.string.register_user))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onEvent(SignupUiEvent.NavigateToLogin) }
        ) {
            Text(text = stringResource(R.string.go_to_login))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SignupScreenPreview() {
    val uiState = SignupUiState()
    SignupContent(uiState = uiState, onEvent = {})

}