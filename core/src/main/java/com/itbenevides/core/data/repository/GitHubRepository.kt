package com.itbenevides.core.data.repository


import com.itbenevides.core.data.model.PullRequest
import com.itbenevides.core.data.model.ResponseGitHub


interface GitHubRepository {
     suspend fun getRepositories(page: Int = 0): ResponseGitHub

     suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest>
}