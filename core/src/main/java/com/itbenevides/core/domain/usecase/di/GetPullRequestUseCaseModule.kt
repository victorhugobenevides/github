package com.itbenevides.core.domain.usecase.di

import com.itbenevides.core.domain.usecase.GetPullRequestUseCase
import com.itbenevides.core.domain.usecase.GetPullRequestUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface GetPullRequestUseCaseModule {
    @Binds
    fun bindGitHubRepository(repository: GetPullRequestUseCaseImp): GetPullRequestUseCase
}