package com.jagl.pickleapp.features.character_detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.features.character_detail.components.CharacterDetail

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler { onBack() }
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        CharacterDetailScreenContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState
        )

    }

}

@Composable
fun CharacterDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: CharacterDetailUiState
) {
    val character = uiState.data ?: return
    CharacterDetail(
        modifier = modifier,
        item = character
    )
}


