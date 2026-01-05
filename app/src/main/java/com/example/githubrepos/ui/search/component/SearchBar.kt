package com.example.githubrepos.ui.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubrepos.ui.theme.GitHubReposTheme

@Composable
fun SimpleInputSearchBar(
    query: String,
    placeholder: String,
    onQueryChanged: (query: String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "clear")
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun SimpleInputSearchBarPreview() {
    GitHubReposTheme {
        SimpleInputSearchBar(
            query = "query",
            placeholder = "検索キーワードを入力",
            onQueryChanged = {})
    }
}

@Composable
@Preview(showBackground = true)
fun SimpleInputSearchBarEmptyPreview() {
    GitHubReposTheme {
        SimpleInputSearchBar(
            query = "",
            placeholder = "検索キーワードを入力",
            onQueryChanged = {})
    }
}
