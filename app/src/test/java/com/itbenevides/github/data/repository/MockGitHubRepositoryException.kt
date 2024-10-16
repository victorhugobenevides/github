package com.itbenevides.github.data.repository

import com.itbenevides.core.data.model.PullRequest
import com.itbenevides.core.data.model.ResponseGitHub
import com.itbenevides.core.data.repository.GitHubRepository
import com.itbenevides.github.data.ExceptionEnum
import java.io.IOException

class MockGitHubRepositoryException(val enum: ExceptionEnum): GitHubRepository {

    override suspend fun getRepositories(page: Int): ResponseGitHub {
            when(enum){
                ExceptionEnum.IOException -> throw IOException()
                ExceptionEnum.HttpException -> throw IOException()
                else -> throw Exception()
            }
        }

    override suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest> {
        when(enum){
            ExceptionEnum.IOException -> throw IOException()
            ExceptionEnum.HttpException -> throw IOException()
            else -> throw Exception()
        }
    }


}