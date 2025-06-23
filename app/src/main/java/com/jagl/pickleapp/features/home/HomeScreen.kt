package com.jagl.pickleapp.features.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.core.utils.extensions.SEARCH_BY_EPISODE
import com.jagl.pickleapp.core.utils.extensions.SEARCH_BY_NAME
import com.jagl.pickleapp.core.utils.ui.components.FullScreenLoading
import com.jagl.pickleapp.features.home.components.EpisodeItem
import com.jagl.pickleapp.ui.main.MainActivity

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Long) -> Unit,
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
                is HomeUiEvent.GoToEpisodeDetails -> onNavigateToDetail(event.id)
                HomeUiEvent.Logout -> {
                    val intent = Intent(context, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(intent)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        HomeContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent
        )

    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier.fillMaxSize(),
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.then(modifier)
    ) {

        if (uiState.isLoading) {
            FullScreenLoading()
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.Underline,
                text = "Search type: ${uiState.searchType}",
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable {
                        val newSearchType =
                            if (uiState.searchType == String.SEARCH_BY_NAME) String.SEARCH_BY_EPISODE else String.SEARCH_BY_NAME
                        onEvent(HomeUiEvent.OnSearchTypeChanged(newSearchType))
                    }
            )

            IconButton(
                modifier = Modifier.padding(8.dp),
                onClick = { onEvent(HomeUiEvent.Logout) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }



        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = uiState.query,
            onValueChange = { query ->
                when (query.isBlank()) {
                    true -> onEvent(HomeUiEvent.ClearSearch)
                    else -> onEvent(HomeUiEvent.SearchQueryChanged(query))
                }
            },
            placeholder = { Text(text = "Search episode by ${uiState.searchType}") },
        )

        if (uiState.data.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(1)
            ) {
                itemsIndexed(items = uiState.data) { index, episode ->
                    if (
                        !uiState.isLoading &&
                        ((index + 1) >= (uiState.page * 20)) &&
                        uiState.query.isEmpty()
                    ) {
                        onEvent(HomeUiEvent.GetMoreEpisodes)
                    }
                    EpisodeItem(
                        item = episode,
                        onClick = { id -> onEvent(HomeUiEvent.GoToEpisodeDetails(id)) }
                    )
                }
            }
        } else if (uiState.query.isNotEmpty()) {
            Text(
                text = "No items found",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val uiState = HomeUiState()
    HomeContent(uiState = uiState, onEvent = {})
}

