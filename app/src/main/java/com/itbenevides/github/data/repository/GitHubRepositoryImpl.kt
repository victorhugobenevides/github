package com.itbenevides.github.data.repository


import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub
import com.itbenevides.github.data.remote.APIService
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val apiService: APIService
): GitHubRepository {
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