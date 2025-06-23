package com.jagl.pickleapp.domain.usecase.google

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jagl.pickleapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetGoogleSignInClient @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    operator fun invoke(
        onSuccess: (GoogleSignInClient) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val gson = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(
                    context.getString(R.string.default_web_client_id)
                )
                .requestEmail()
                .build()
            onSuccess(GoogleSignIn.getClient(context, gson))
        } catch (e: Exception) {
            onError(e.message ?: "An error occurred during sign-in")
        }

    }


}