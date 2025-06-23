package com.jagl.pickleapp.features.episode_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagl.pickleapp.core.repository.character.CharacterRepository
import com.jagl.pickleapp.core.repository.episode.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val episodeRepository: EpisodeRepository,
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private val episodeId: Long = savedStateHandle.get<Long>(EPISODE_ID) ?: 0L

    private val _uiState = MutableStateFlow(EpisodeDetailUiState())
    val uiState: StateFlow<EpisodeDetailUiState> = _uiState.asStateFlow()

    init {
        getEpisodeDetail(episodeId)
    }

    private fun getEpisodeDetail(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val data = episodeRepository.getEpisodeById(id)
                val charactersId = data.charactersInEpisode
                val characters = characterRepository.getCharactersByIds(charactersId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        data = data,
                        charactersInEpisode = characters
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error",
                        data = null
                    )
                }
            }
        }

    }

    companion object {
        const val EPISODE_ID = "episodeId"
    }
}