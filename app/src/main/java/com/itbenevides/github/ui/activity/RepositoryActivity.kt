package com.itbenevides.github.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.itbenevides.github.ui.feature.github.repository.RepositoryRoute
import com.itbenevides.github.ui.theme.GitHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubTheme {
                    RepositoryRoute()
            }
        }
    }
}