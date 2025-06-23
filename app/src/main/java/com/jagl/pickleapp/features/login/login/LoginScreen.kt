package com.jagl.pickleapp.features.login.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.R
import com.jagl.pickleapp.core.utils.ui.components.FullScreenLoading
import com.jagl.pickleapp.core.utils.ui.components.InputWithError

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToSignup: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
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

    LaunchedEffect(uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                LoginUiEvent.NavigateToForgotPassword -> onNavigateToForgotPassword()
                LoginUiEvent.NavigateToHome -> onNavigateToHome()
                LoginUiEvent.NavigateToSignUp -> onNavigateToSignup()
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LoginContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
) {

    if (uiState.isLoading) {
        FullScreenLoading()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputWithError(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = uiState.email,
            onValueChange = { value ->
                val event = LoginUiEvent.UpdateEmail(value)
                onEvent(event)
            },
            errorMessage = uiState.emailError,
            placeholderText = stringResource(R.string.email)
        )
        Spacer(modifier = Modifier.height(12.dp))
        InputWithError(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = uiState.password,
            onValueChange = { value ->
                val event = LoginUiEvent.UpdatePassword(value)
                onEvent(event)
            },
            errorMessage = uiState.passwordError,
            placeholderText = stringResource(R.string.password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = {
                onEvent(LoginUiEvent.MakeLogin)
            },
            enabled = uiState.isLoginEnabled
        ) {
            Text(text = stringResource(R.string.login))
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                text = stringResource(R.string.forgot_password),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onEvent(LoginUiEvent.NavigateToForgotPassword) }
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                text = stringResource(R.string.signup),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onEvent(LoginUiEvent.NavigateToSignUp) }

            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    val uiState = LoginUiState()
    LoginContent(uiState = uiState, onEvent = { })
}
