package com.jagl.pickleapp.features.episode_detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.domain.model.EpisodeDomain
import com.jagl.pickleapp.features.episode_detail.components.CharacterItem

@Composable
fun EpisodeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: EpisodeDetailViewModel = hiltViewModel(),
    onNavigateToCharacterDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler { onBack() }
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        EpisodeDetailScreenContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            onNavigateToCharacterDetail = onNavigateToCharacterDetail
        )

    }

}

@Composable
fun EpisodeDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: EpisodeDetailUiState,
    onNavigateToCharacterDetail: (Long) -> Unit
) {
    val episode = uiState.data ?: return
    Column(modifier = modifier) {
        Text(
            text = "Episode: ${episode.name}",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Air Date: ${episode.airDate}",
            modifier = Modifier.padding(8.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Episode: ${episode.getEpisodeNumber()}",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Season: ${episode.getSeasonNumber()}",
                modifier = Modifier.padding(8.dp)
            )
        }

        val characters = uiState.charactersInEpisode
        if (characters.isNotEmpty()) {
            Text(
                text = "Characters in this episode:",
                modifier = Modifier.padding(8.dp)
            )
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3)
            ) {
                items(items = characters) { character ->
                    CharacterItem(
                        item = character,
                        onClick = {
                            onNavigateToCharacterDetail(it)
                        }
                    )
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun EpisodeDetailScreenPreview() {
    val uiState = EpisodeDetailUiState(
        data = EpisodeDomain(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            charactersInEpisode = emptyList(),
            url = "https://rickandmortyapi.com/api/episode/1",
            created = "2013-11-10T12:56:33.798Z",
            page = 1
        ),
        isLoading = false,
        errorMessage = null,
        charactersInEpisode = listOf(
            CharacterDomain(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "",
                origin = "",
                location = "",
                episodes = emptyList()
            )
        )
    )
    EpisodeDetailScreenContent(
        uiState = uiState,
        onNavigateToCharacterDetail = { }
    )
}

