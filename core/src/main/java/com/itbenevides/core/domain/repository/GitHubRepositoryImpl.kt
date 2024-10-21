package com.itbenevides.core.domain.repository


import com.itbenevides.core.data.model.PullRequest
import com.itbenevides.core.data.model.ResponseGitHub
import com.itbenevides.core.data.remote.APIService
import com.itbenevides.core.data.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : GitHubRepository {
    override suspend fun getRepositories(page: Int): ResponseGitHub {

        val response =
            apiService
                .getRepositories(page = page)
                .await()

        return response

    }

    override suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest> {

        val response =
            apiService.getPullRequests(user, repo)
                .await()

        return response
    }
}