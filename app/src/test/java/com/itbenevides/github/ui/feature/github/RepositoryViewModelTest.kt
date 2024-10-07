package com.itbenevides.github.ui.feature.github

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.itbenevides.github.data.ExceptionEnum
import com.itbenevides.github.data.model.MockResponseGitHub
import com.itbenevides.github.data.repository.MockGitHubRepository
import com.itbenevides.github.data.repository.MockGitHubRepositoryException
import com.itbenevides.github.ui.feature.github.repository.RepositoryInfoState
import com.itbenevides.github.ui.feature.github.repository.RepositoryViewModel
import io.mockk.mockk
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
        val gitHubRepository = MockGitHubRepository()
        val responsePage1 = MockResponseGitHub().create(pageDescription = "1")
        val responsePage2 = MockResponseGitHub().create(pageDescription = "2")

        gitHubRepository.pageRepository.add(responsePage1)
        gitHubRepository.pageRepository.add(responsePage2)

        val viewModel = RepositoryViewModel(gitHubRepository)

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

        assertEquals(StatusResult.Loading, emittedStates[0].status)

        assertEquals(StatusResult.Success, emittedStates[1].status)
        assertEquals(responsePage1.items, emittedStates[1].data)


        viewModel.getMoreRepositoryInfo()

        advanceUntilIdle()
        runCurrent()
        delay(500)

        assertEquals(StatusResult.Loading, emittedStates[2].status)

        assertEquals(StatusResult.Success, emittedStates[3].status)
        assertEquals(responsePage1.items + responsePage2.items, emittedStates[3].data)

        job.cancel()
    }


    @Test
    fun `getRepositoryInfo error IOException`() = runTest {
        val gitHubRepository = MockGitHubRepositoryException(ExceptionEnum.IOException)


        val viewModel = RepositoryViewModel(gitHubRepository)

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
        assertEquals("Network error. Please check your connection.", emittedStates.last().errorMessages)

        job.cancel()
    }
}