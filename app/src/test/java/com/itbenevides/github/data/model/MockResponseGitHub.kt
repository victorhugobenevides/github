package com.itbenevides.github.data.model

import MockRepository
import com.itbenevides.core.data.model.Repository

class MockResponseGitHub {

    fun create(
        pageDescription: String = "",
        repositories: MutableList<Repository> = MutableList(10) { index ->
            MockRepository().create(
                name = "mock-repo-$index-$pageDescription",
                authorName = "mock-owner-$index-$pageDescription"
            )
        }
    ): List<Repository> {
        return repositories
    }
}
