package com.itbenevides.github.domain

import com.itbenevides.core.data.model.Repository
import com.itbenevides.core.domain.usecase.GetRepositoriesUseCase

class MockGetRepositoriesUseCase : GetRepositoriesUseCase {

    val pageRepository = mutableListOf<List<Repository>>()

    override suspend fun getRepositories(page: Int): List<Repository> {
        return pageRepository[page]
    }
}