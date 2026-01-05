package com.example.githubrepos.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.githubrepos.core.common.extension.openUrl
import com.example.githubrepos.core.model.RepoData
import com.example.githubrepos.ui.search.component.SimpleInputSearchBar
import com.example.githubrepos.ui.theme.GitHubReposTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun ReposSearchScreen(viewModel: ReposSearchViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    ReposSearchContent(
        pagingItems = pagingItems,
        query = uiState.query,
        hasSearchStarted = uiState.hasSearchStarted,
        onQueryChanged = viewModel::updateQuery,
        onClickItem = context::openUrl,
        onRetry = pagingItems::retry,
    )
}

@Composable
private fun ReposSearchContent(
    pagingItems: LazyPagingItems<RepoData>,
    query: String,
    hasSearchStarted: Boolean,
    onQueryChanged: (text: String) -> Unit,
    onClickItem: (url: String) -> Unit,
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
            SimpleInputSearchBar(query = query, onQueryChanged = onQueryChanged)

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when {
                    // 初期状態
                    !hasSearchStarted -> {
                        // empty
                    }

                    // 検索中
                    pagingItems.loadState.refresh is LoadState.Loading -> {
                        LoadingContent()
                    }

                    // エラー
                    pagingItems.loadState.refresh is LoadState.Error -> {
                        ErrorContent(onRetry = onRetry)
                    }

                    // 検索結果0件
                    pagingItems.itemCount == 0 &&
                            pagingItems.loadState.refresh is LoadState.NotLoading -> {
                        NotFoundContent()
                    }

                    // 検索成功
                    else -> {
                        ReposList(
                            pagingItems = pagingItems,
                            onClickItem = onClickItem,
                            onRetry = onRetry
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    CircularProgressIndicator()
}

@Composable
private fun ReposList(
    pagingItems: LazyPagingItems<RepoData>,
    onClickItem: (url: String) -> Unit,
    onRetry: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id }
        ) { index ->
            pagingItems[index]?.let {
                RepoItem(item = it, onClick = onClickItem)
            }
        }
        item {
            when (pagingItems.loadState.append) {
                // 追加ロード中
                is LoadState.Loading -> {
                    LoadingContent()
                }

                // 追加ロードエラー
                is LoadState.Error -> {
                    ErrorContent(onRetry = onRetry)
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun RepoItem(item: RepoData, onClick: (url: String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(item.htmlUrl) }),
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "エラーが発生しました\n時間をおいてリトライしてください")
        TextButton(onClick = onRetry) {
            Text("リトライ")
        }
    }
}

// NOTE: Paging3のプレビューが上手くいかず、Loading状態しかプレビューできていない
private val previewRepoDataList = listOf(
    RepoData(
        id = 1,
        name = "repo1",
        description = "description of repo1",
        stargazersCount = 111,
        language = "Kotlin",
        htmlUrl = "https://example.com/repo1",
    ),
    RepoData(
        id = 2,
        name = "repo2",
        description = "description of repo2",
        stargazersCount = 222,
        language = "Python",
        htmlUrl = "https://example.com/repo2",
    )
).let { PagingData.from(data = it) }.let { flowOf(it) }

@Composable
@Preview
fun ReposSearchInitialPreview() {
    GitHubReposTheme {
        ReposSearchContent(
            pagingItems = previewRepoDataList.collectAsLazyPagingItems(),
            hasSearchStarted = false,
            query = "kotlin",
            onQueryChanged = {},
            onClickItem = {},
            onRetry = {},
        )
    }
}

@Composable
@Preview
fun ReposSearchSearchingPreview() {
    GitHubReposTheme {
        ReposSearchContent(
            pagingItems = previewRepoDataList.collectAsLazyPagingItems(),
            hasSearchStarted = true,
            query = "kotlin",
            onQueryChanged = {},
            onClickItem = {},
            onRetry = {},
        )
    }
}
