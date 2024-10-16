package com.itbenevides.github.ui.feature.github.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itbenevides.core.data.model.Repository
import com.itbenevides.core.data.repository.GitHubRepository
import com.itbenevides.github.ui.feature.github.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _repositoryInfoState = MutableStateFlow(
        RepositoryInfoState(
            data = emptyList(),
            status = StatusResult.Idle
        )
    )
    val repositoryInfoState: StateFlow<RepositoryInfoState> = _repositoryInfoState.asStateFlow()

    var page = 0
    private var isLoading = false

    fun getRepositoryInfo(page: Int = 0) {
        if (isLoading) return
        updateRepositoryInfoState(StatusResult.Loading)
        isLoading = true
        viewModelScope.launch {
            try {
                val response = gitHubRepository.getRepositories(page)
                updateRepositoryInfoState(StatusResult.Success, response.items)
            } catch (e: IOException) {
                updateRepositoryInfoState(
                    StatusResult.Error,
                    errorMessages = "Network error. Please check your connection."
                )
            } catch (e: Exception) {
                updateRepositoryInfoState(
                    StatusResult.Error,
                    errorMessages = e.message ?: "Unknown error"
                )
            } finally {
                isLoading = false
            }
        }
    }

    private fun updateRepositoryInfoState(
        statusResult: StatusResult,
        data: List<Repository> = mutableListOf(),
        errorMessages: String = ""
    ) {
        _repositoryInfoState.update { currentState ->
            currentState.copy(
                data = currentState.data + data,
                status = statusResult,
                errorMessages = errorMessages
            )
        }
    }

    fun getMoreRepositoryInfo() {
        if (!isLoading) {
            page += 1
            getRepositoryInfo(page)
        }
    }
}
