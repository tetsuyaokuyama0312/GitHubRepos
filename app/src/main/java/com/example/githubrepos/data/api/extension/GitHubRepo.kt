package com.example.githubrepos.data.api.extension

import com.example.githubrepos.core.model.RepoData
import com.example.githubrepos.data.api.model.GitHubRepo

fun GitHubRepo.toRepoData(): RepoData = RepoData(
    id = this.id,
    name = this.name,
    description = this.description,
    stargazersCount = this.stargazersCount,
    language = this.language,
    htmlUrl = this.htmlUrl,
)
