package com.itbenevides.github.data.repository


import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub


interface GitHubRepository {
     suspend fun getRepositories(page: Int = 0): ResponseGitHub

     suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest>
}