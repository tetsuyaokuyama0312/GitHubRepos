package com.example.githubrepos.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepos.core.model.RepoData
import com.example.githubrepos.data.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReposSearchViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ReposSearchUiState> =
        MutableStateFlow(ReposSearchUiState())
    val uiState: StateFlow<ReposSearchUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagingDataFlow: Flow<PagingData<RepoData>> =
        queryFlow
            .debounce(500)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { _uiState.update { it.copy(hasSearchStarted = true) } }
            .flatMapLatest { query ->
                repository.searchRepos(query)
            }
            .cachedIn(viewModelScope)

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.update { query }
    }
}

data class ReposSearchUiState(
    val query: String = "",
    val hasSearchStarted: Boolean = false,
)
