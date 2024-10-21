package com.itbenevides.core.domain.usecase.di

import com.itbenevides.core.domain.usecase.GetRepositoriesUseCase
import com.itbenevides.core.domain.usecase.GetRepositoriesUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface GetRepositoriesUseCaseModule {
    @Binds
    fun bindGitHubRepository(repository: GetRepositoriesUseCaseImp): GetRepositoriesUseCase
}