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
fun SimpleInputSearchBar(query: String, onQueryChange: (query: String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
        },
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = { Text("検索キーワードを入力") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "クリア")
                }
            }
        },
//        // キーボードの「Enter」を「検索」アイコンに変更
//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//        // キーボードの検索ボタンが押された時の処理
//        keyboardActions = KeyboardActions(
//            onSearch = {
//                onSearch(text)
//                focusManager.clearFocus() // キーボードを閉じる
//            }
//        ),
        singleLine = true,
        shape = RoundedCornerShape(28.dp) // 丸みを帯びたデザイン
    )
}

@Composable
@Preview
fun SimpleInputSearchBarPreview() {
    GitHubReposTheme {
        SimpleInputSearchBar(query = "query", onQueryChange = {})
    }
}
