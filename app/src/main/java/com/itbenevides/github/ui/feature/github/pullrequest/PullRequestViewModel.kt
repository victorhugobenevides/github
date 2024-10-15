package com.itbenevides.github.ui.feature.github.pullrequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itbenevides.github.data.model.PullRequest
import com.itbenevides.github.data.repository.GitHubRepository
import com.itbenevides.github.ui.feature.github.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
) : ViewModel() {

    private val _pullRequestInfoState = MutableStateFlow(
        PullRequestInfoState(
            data = emptyList(),
            status = StatusResult.Idle
        )
    )
    val pullRequestInfoState: StateFlow<PullRequestInfoState> = _pullRequestInfoState.asStateFlow()

    private var isLoading = false

    fun getPullRequestInfo(username: String?, repositoryName: String?) {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            _pullRequestInfoState.update { currentState ->
                currentState.copy(status = StatusResult.Loading)
            }

        try {
            val response = gitHubRepository.getPullRequests(username, repositoryName).await()
            updatePullRequestInfoState(StatusResult.Success, response)
        } catch (e: IOException) {
            updatePullRequestInfoState(StatusResult.Error, errorMessages = "Network error. Please check your connection.")
        } catch (e: HttpException) {
            updatePullRequestInfoState(StatusResult.Error, errorMessages = "Server error. Please try again later.")
        } catch (e: Exception) {
            updatePullRequestInfoState(StatusResult.Error, errorMessages = e.message ?: "Unknown error")
        } finally {
            isLoading = false
        }
        }
    }
    private fun updatePullRequestInfoState(statusResult: StatusResult, data: List<PullRequest> = mutableListOf(), errorMessages: String = ""){
        viewModelScope.launch(Dispatchers.Main) {
            _pullRequestInfoState.update { currentState ->
                currentState.copy(
                    data = currentState.data + data,
                    status = statusResult,
                    errorMessages = errorMessages
                )
            }
        }
    }
}