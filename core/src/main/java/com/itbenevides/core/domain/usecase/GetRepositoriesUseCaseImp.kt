package com.itbenevides.core.domain.usecase

import com.itbenevides.core.data.model.Repository
import com.itbenevides.core.data.repository.GitHubRepository
import javax.inject.Inject

class GetRepositoriesUseCaseImp @Inject constructor(private val gitHubRepository: GitHubRepository): GetRepositoriesUseCase{
    override suspend fun getRepositories(page: Int): List<Repository> {

        val response =
            gitHubRepository
                .getRepositories(page = page)
                .items

        return response

    }
}

