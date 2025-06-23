package com.jagl.pickleapp.features.login.google_login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import com.jagl.pickleapp.R
import com.jagl.pickleapp.core.utils.ui.components.FullScreenLoading

@Composable
fun GoogleLoginScreen(
    modifier: Modifier = Modifier,
    viewModel: GoogleLoginViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit
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

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val account =
            viewModel.handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))
        when (account) {
            is GoogleSignInAccount -> {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.onEvent(GoogleLoginUiEvent.SignInWithGoogle(credential))
            }

            null -> {
                Toast.makeText(context, R.string.google_sign_in_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                GoogleLoginUiEvent.NavigateToHome -> onNavigateToHome()
                GoogleLoginUiEvent.LaunchGoogleRequest -> {
                    viewModel.getSignInIntent(googleSignInLauncher)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        GoogleLoginContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun GoogleLoginContent(
    modifier: Modifier = Modifier,
    uiState: GoogleLoginUiState,
    onEvent: (GoogleLoginUiEvent) -> Unit,
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
        Text(
            style = MaterialTheme.typography.headlineLarge,
            text = stringResource(R.string.sign_in_with_google),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = {
                onEvent(GoogleLoginUiEvent.LaunchGoogleRequest)
            }
        ) {
            Text(text = stringResource(R.string.login_with_google))
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleLoginContentPreview() {
    val uiState = GoogleLoginUiState()
    GoogleLoginContent(uiState = uiState, onEvent = { })
}
