package com.itbenevides.github.data.repository

import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub
import kotlinx.coroutines.delay

class MockGitHubRepository: GitHubRepository {

    val pageRepository  = mutableListOf<ResponseGitHub>()

    override suspend fun getRepositories(page: Int): ResponseGitHub {
        return pageRepository[page]
    }

    override suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest> {
        TODO("Not yet implemented")
    }
}