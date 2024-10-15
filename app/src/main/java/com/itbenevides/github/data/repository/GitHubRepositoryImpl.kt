package com.itbenevides.github.data.repository


import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub
import com.itbenevides.github.data.remote.APIService
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val apiService: APIService
): GitHubRepository {
    override suspend fun getRepositories(page: Int): Deferred<ResponseGitHub> {

       val response =
           apiService
               .getRepositories(page = page)

        return response

    }

    override suspend fun getPullRequests(user: String?, repo: String?): Deferred<List<PullRequest>> {

        val response =
            apiService.getPullRequests(user, repo)

        return response
    }
}