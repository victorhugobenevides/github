package com.itbenevides.github.data.model

import MockRepository
import com.itbenevides.core.data.model.Repository
import com.itbenevides.core.data.model.ResponseGitHub
import io.mockk.every
import io.mockk.mockk

class MockResponseGitHub {

    fun create(
        totalCount: Int = 1,
        incompleteResults: Boolean = false,
        pageDescription: String = "",
        repositories: MutableList<Repository> = MutableList(10) { index ->
            MockRepository().create(
                name = "mock-repo-$index-$pageDescription",
                authorName = "mock-owner-$index-$pageDescription"
            )
        }
    ): ResponseGitHub {
        return mockk {
            every { this@mockk.total_count } returns totalCount
            every { this@mockk.incomplete_results } returns incompleteResults
            every { this@mockk.items } returns repositories
        }
    }
}
