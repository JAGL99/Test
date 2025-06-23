package com.jagl.pickleapp.features.login.restore_password

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
fun RestorePasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: RestorePasswordViewModel = hiltViewModel(),
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
                RestorePasswordUiEvent.OnNavigateToLogin -> onNavigateToLogin()
                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        RestorePasswordContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }

}

@Composable
fun RestorePasswordContent(
    modifier: Modifier = Modifier,
    uiState: RestorePasswordUiState,
    onEvent: (RestorePasswordUiEvent) -> Unit
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
                val event = RestorePasswordUiEvent.OnEmailChange(value)
                onEvent(event)
            },
            isError = uiState.emailError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = {
                onEvent(RestorePasswordUiEvent.OnRestorePassword)
            },
            enabled = uiState.enabledRestoreButton
        ) {
            Text(text = stringResource(R.string.restart_password))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = { onEvent(RestorePasswordUiEvent.OnNavigateToLogin) }
        ) {
            Text(text = stringResource(R.string.go_to_login))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RestorePasswordContentPreview() {
    RestorePasswordContent(
        uiState = RestorePasswordUiState(),
        onEvent = {}
    )
}
