package com.example.githubrepos.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.core.model.RepoData
import com.example.githubrepos.core.model.RepoOwnerData
import com.example.githubrepos.data.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposSearchViewModel @Inject constructor(
    private val repository: GitHubRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ReposSearchUiState> =
        MutableStateFlow(ReposSearchUiState())
    val uiState: StateFlow<ReposSearchUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        observeQuery()
    }

    // todo
    //  * paging
    //  * details

    @OptIn(FlowPreview::class)
    private fun observeQuery() = viewModelScope.launch {
        queryFlow
            .debounce(500)
            .distinctUntilChanged()
            .filter { it.length >= 2 }
            .collect { search(it) }
    }

    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.update { query }
    }

    fun retry() {
        search(queryFlow.value)
    }

    private fun search(query: String) = viewModelScope.launch {
        _uiState.update { it.copy(searchState = SearchState.Loading) }

        val result = repository.searchRepos(query).also {
            Log.d(javaClass.name, "result:$it")
        }
        val newState = when {
            result.isSuccess -> {
                result.getOrNull()?.ifEmpty { null }?.let { items ->
                    SearchState.Found(items)
                } ?: run {
                    SearchState.NotFound
                }
            }

            else -> {
                SearchState.Error
            }
        }
        _uiState.update { it.copy(searchState = newState) }
    }
}

data class ReposSearchUiState(
    val query: String = "",
    val currentPage: Int = 1,
    val searchState: SearchState = SearchState.Idle,
)

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Found(val items: List<RepoData>) : SearchState
    data object NotFound : SearchState
    data object Error : SearchState

    companion object {
        val previewFound = Found(
            items = listOf(
                RepoData(
                    id = 1,
                    name = "repo1",
                    fullName = "full repo1",
                    description = "description of repo1",
                    homepage = "https://example.com/repo1",
                    stargazersCount = 111,
                    language = "Kotlin",
                    owner = RepoOwnerData(login = "login", avatarUrl = "https://example.com")
                ),
                RepoData(
                    id = 2,
                    name = "repo2",
                    fullName = "full repo2",
                    description = "description of repo2",
                    homepage = "https://example.com/repo2",
                    stargazersCount = 222,
                    language = "Python",
                    owner = RepoOwnerData(login = "login", avatarUrl = "https://example.com")
                )
            )
        )
    }
}
