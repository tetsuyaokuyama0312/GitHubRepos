package com.example.githubrepos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubrepos.ui.search.ReposSearchScreen

@Composable
fun GitHubReposNavHost() {
    NavHost(
        navController = rememberNavController(),
        startDestination = Routes.Search,
        modifier = Modifier,
    ) {
        composable<Routes.Search> {
            ReposSearchScreen()
        }
    }
}
