package com.itbenevides.github.domain

import com.itbenevides.core.data.model.Repository
import com.itbenevides.core.domain.usecase.GetRepositoriesUseCase
import com.itbenevides.github.data.ExceptionEnum
import java.io.IOException

class MockGetRepositoriesUseCaseException(val enum: ExceptionEnum): GetRepositoriesUseCase {

    override suspend fun getRepositories(page: Int): List<Repository> {
            when(enum){
                ExceptionEnum.IOException -> throw IOException()
                ExceptionEnum.HttpException -> throw IOException()
                else -> throw Exception()
            }
        }
}