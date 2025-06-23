package com.jagl.pickleapp.domain.usecase.logout

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.jagl.critiq.core.utils.dispatcherProvider.DispatcherProvider
import com.jagl.pickleapp.core.local.AppDatabase
import com.jagl.pickleapp.domain.usecase.firebase.GetGoogleSignup
import com.jagl.pickleapp.domain.usecase.firebase.LogoutFromFirebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Logout @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val getGoogleSignup: GetGoogleSignup,
    private val logoutFromFirebase: LogoutFromFirebase,
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = withContext(dispatcherProvider.default) {
        try {
            appDatabase.clearAllTables()
            if (getGoogleSignup()) {
                Identity.getSignInClient(context).signOut()
            }
            logoutFromFirebase(
                onSuccess = onSuccess,
                onError = onError
            )
        } catch (e: Exception) {
            onError(e.message ?: "An error occurred during logout")
        }

    }
}