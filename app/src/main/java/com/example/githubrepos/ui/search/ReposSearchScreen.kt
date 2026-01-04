package com.example.githubrepos.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubrepos.core.model.RepoData
import com.example.githubrepos.ui.search.component.SimpleInputSearchBar
import com.example.githubrepos.ui.theme.GitHubReposTheme

@Composable
fun ReposSearchScreen(viewModel: ReposSearchViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReposSearchContent(
        searchState = uiState.searchState,
        query = uiState.query,
        onSearch = viewModel::updateQuery,
        onRetry = viewModel::retry,
    )
}

@Composable
private fun ReposSearchContent(
    searchState: SearchState,
    query: String,
    onSearch: (text: String) -> Unit,
    onRetry: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SimpleInputSearchBar(query = query, onQueryChange = onSearch)

            when (searchState) {
                is SearchState.Idle -> {
                    // nop
                }

                is SearchState.Loading -> {
                    LoadingContent()
                }

                is SearchState.Found -> {
                    ReposList(searchState.items)
                }

                is SearchState.NotFound -> {
                    NotFoundContent()
                }

                is SearchState.Error -> {
                    ErrorContent(onRetry = onRetry)
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ReposList(items: List<RepoData>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            ReposItem(item)
        }
    }
}

@Composable
private fun ReposItem(item: RepoData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = item.name)

                Row {
                    Icon(Icons.Default.Star, contentDescription = "star")
                    Text(text = item.stargazersCount.toString())
                }
            }
            item.language?.let {
                Text(text = it)
            }
            Text(text = item.description.orEmpty())
        }
    }
}

@Composable
private fun NotFoundContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "リポジトリが見つかりませんでした")
    }
}

@Composable
private fun ErrorContent(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "エラーが発生しました\n時間をおいてリトライしてください")
        TextButton(onClick = onRetry) {
            Text("リトライ")
        }
    }
}

@Composable
@Preview
fun ReposSearchContentPreview() {
    GitHubReposTheme {
        ReposSearchContent(
            searchState = SearchState.previewFound,
            query = "kotlin",
            onSearch = {},
            onRetry = {},
        )
    }
}

@Composable
@Preview
fun ReposSearchContentErrorPreview() {
    GitHubReposTheme {
        ReposSearchContent(
            searchState = SearchState.Error,
            query = "kotlin",
            onSearch = {},
            onRetry = {},
        )
    }
}
