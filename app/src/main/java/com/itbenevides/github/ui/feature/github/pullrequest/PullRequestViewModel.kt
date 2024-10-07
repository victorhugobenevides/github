package com.itbenevides.github.ui.feature.github.pullrequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                val response = gitHubRepository.getPullRequests(username, repositoryName)

                _pullRequestInfoState.update { currentState ->
                    currentState.copy(
                        data = currentState.data + response,
                        status = StatusResult.Success
                    )
                }
            } catch (e: IOException) {
                _pullRequestInfoState.update {
                    it.copy(
                        status = StatusResult.Error,
                        errorMessages = "Network error. Please check your connection."
                    )
                }
            } catch (e: HttpException) {
                _pullRequestInfoState.update {
                    it.copy(
                        status = StatusResult.Error,
                        errorMessages = "Server error. Please try again later. ${e.toString()}"
                    )
                }
            } catch (e: Exception) {
                _pullRequestInfoState.update {
                    it.copy(
                        status = StatusResult.Error,
                        errorMessages = e.message ?: "Unknown error"
                    )
                }
            } finally {
                isLoading = false
            }
        }
    }
}