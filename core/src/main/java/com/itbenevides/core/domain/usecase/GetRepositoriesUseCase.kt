package com.itbenevides.core.domain.usecase

import com.itbenevides.core.data.model.Repository

interface GetRepositoriesUseCase  {
    suspend fun getRepositories(page: Int): List<Repository>
}

