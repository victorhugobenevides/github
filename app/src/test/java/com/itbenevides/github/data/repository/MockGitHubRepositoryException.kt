package com.itbenevides.github.data.repository

import com.itbenevides.github.data.ExceptionEnum
import com.itbenevides.github.data.model.MockResponseGitHub
import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.model.ResponseGitHub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MockGitHubRepositoryException(val enum: ExceptionEnum): GitHubRepository {

    @Throws(HttpException::class)
    override suspend fun getRepositories(page: Int): ResponseGitHub {
            when(enum){
                ExceptionEnum.IOException -> throw IOException()
                ExceptionEnum.HttpException -> throw HttpException(Response.error<Any>(404, null))
                else -> throw Exception()
            }
        }

    override suspend fun getPullRequests(user: String?, repo: String?): List<PullRequest> {
        when(enum){
            ExceptionEnum.IOException -> throw IOException()
            ExceptionEnum.HttpException -> throw HttpException(Response.error<Any>(404, null))
            else -> throw Exception()
        }
    }


}