package com.example.githubrepos.core.api.extension

import com.example.githubrepos.core.api.model.GitHubRepo
import com.example.githubrepos.core.model.RepoData
import com.example.githubrepos.core.model.RepoOwnerData

fun GitHubRepo.toRepoData(): RepoData = RepoData(
    id = this.id,
    name = this.name,
    fullName = this.fullName,
    description = this.description,
    homepage = this.homePage,
    stargazersCount = this.stargazersCount,
    language = this.language,
    htmlUrl = this.htmlUrl,
    owner = RepoOwnerData(
        this.owner.login,
        this.owner.avatarUrl,
    )
)
