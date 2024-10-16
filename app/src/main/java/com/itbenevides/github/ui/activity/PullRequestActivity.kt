package com.itbenevides.github.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.itbenevides.github.ui.feature.github.pullrequest.PullRequestRoute
import com.itbenevides.github.ui.theme.GitHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PullRequestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("username")
        val repositoryName = intent.getStringExtra("repositoryName")

        enableEdgeToEdge()
        setContent {
            GitHubTheme {
                PullRequestRoute(username = username, repositoryName = repositoryName)
            }
        }
    }
}