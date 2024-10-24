package com.itbenevides.github.ui.feature.github

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.itbenevides.github.data.ExceptionEnum
import com.itbenevides.github.data.model.MockResponseGitHub
import com.itbenevides.github.domain.MockGetRepositoriesUseCase
import com.itbenevides.github.domain.MockGetRepositoriesUseCaseException
import com.itbenevides.github.ui.feature.github.repository.RepositoryInfoState
import com.itbenevides.github.ui.feature.github.repository.RepositoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRepositoryInfo success`() = runTest {
        val getRepositoriesUseCase = MockGetRepositoriesUseCase()
        val responsePage1 = MockResponseGitHub().create(pageDescription = "1")
        val responsePage2 = MockResponseGitHub().create(pageDescription = "2")

        getRepositoriesUseCase.pageRepository.add(responsePage1)
        getRepositoriesUseCase.pageRepository.add(responsePage2)

        val viewModel = RepositoryViewModel(getRepositoriesUseCase)

        val emittedStates = mutableListOf<RepositoryInfoState>()

        val job = launch {
            viewModel.repositoryInfoState.collect {
                emittedStates.add(it)
            }
        }

        advanceUntilIdle()
        runCurrent()
        delay(500)

        viewModel.getRepositoryInfo()

        advanceUntilIdle()
        runCurrent()
        delay(500)

        assertEquals(StatusResult.Idle, emittedStates[0].status)

        assertEquals(StatusResult.Loading, emittedStates[1].status)

        assertEquals(StatusResult.Success, emittedStates[2].status)

        assertEquals(responsePage1, emittedStates[2].data)


        viewModel.getMoreRepositoryInfo()

        advanceUntilIdle()
        runCurrent()
        delay(500)

        assertEquals(StatusResult.Loading, emittedStates[3].status)

        assertEquals(StatusResult.Success, emittedStates[4].status)
        assertEquals(responsePage1 + responsePage2, emittedStates[4].data)

        job.cancel()
    }


    @Test
    fun `getRepositoryInfo error IOException`() = runTest {


        val getRepositoriesUseCase = MockGetRepositoriesUseCaseException(ExceptionEnum.IOException)

        val viewModel = RepositoryViewModel(getRepositoriesUseCase)

        val emittedStates = mutableListOf<RepositoryInfoState>()

        val job = launch {
            viewModel.repositoryInfoState.collect {
                emittedStates.add(it)
            }
        }

        viewModel.getRepositoryInfo()

        advanceUntilIdle()
        runCurrent()
        delay(500)

        assertEquals(StatusResult.Error, emittedStates.last().status)
        assertEquals(
            "Network error. Please check your connection.",
            emittedStates.last().errorMessages
        )

        job.cancel()
    }
}