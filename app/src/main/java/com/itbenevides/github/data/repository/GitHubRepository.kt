package com.itbenevides.github.data.repository


import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub
import kotlinx.coroutines.Deferred


interface GitHubRepository {
     suspend fun getRepositories(page: Int = 0): Deferred<ResponseGitHub>

     suspend fun getPullRequests(user: String?, repo: String?): Deferred<List<PullRequest>>
}