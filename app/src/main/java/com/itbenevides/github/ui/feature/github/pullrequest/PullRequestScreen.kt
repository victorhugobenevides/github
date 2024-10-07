package com.itbenevides.github.ui.feature.github.pullrequest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.itbenevides.github.ui.components.LoadingView
import com.itbenevides.github.ui.feature.github.StatusResult

@Composable
    fun PullRequestRoute(
    username: String?,
    repositoryName: String?
    ) {
        val viewModel = viewModel<PullRequestViewModel>()
        viewModel.getPullRequestInfo(username, repositoryName)
        PullRequestState(viewModel, username, repositoryName)
    }

@Composable
fun PullRequestState(viewModel: PullRequestViewModel, username: String?, repositoryName: String?){
    val state by viewModel.pullRequestInfoState.collectAsStateWithLifecycle()
    PullRequestPage(state, viewModel, username, repositoryName)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullRequestPage(state: PullRequestInfoState, viewModel: PullRequestViewModel, username: String?,  repositoryName: String?) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val activity = LocalContext.current as? Activity
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        repositoryName.toString(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        PullRequestList(state = state, viewModel = viewModel,innerPadding = innerPadding, username = username, repositoryName = repositoryName)
    }
}

@Composable
fun PullRequestList(
    state: PullRequestInfoState,
    viewModel: PullRequestViewModel,
    username: String?,
    repositoryName: String?,
    innerPadding: PaddingValues,
){
    Column(
        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
    ) {
       Row {
           Text(
               text = "500 opened",
               style = MaterialTheme.typography.labelLarge,
               color = Color(0xFFCCAA22),
               maxLines = 1,
               modifier = Modifier.padding(top = 4.dp)
           )
           Text(
               text = "/200 closed",
               style = MaterialTheme.typography.labelLarge,
               color = Color.Black,
               maxLines = 1,
               modifier = Modifier.padding(top = 4.dp)
           )
       }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.data) { pull ->
                PullRequestCard(
                    title = pull.title,
                    description = pull.body,
                    username = pull.user.login,
                    userImageUrl = pull.user.avatar_url,
                    htmlUrl = pull.html_url
                )
                Spacer(modifier = Modifier.height(2.dp))
            }

            if(state.status == StatusResult.Error) {
                item {
                    PullRequestErrorView(error = state.errorMessages, viewModel, username, repositoryName)
                }
            }

            if(state.status == StatusResult.Loading) {
                item {
                    LoadingView()
                }
            }

        }
    }


    }

@Composable
    fun PullRequestErrorView(
    error: String,
    viewModel: PullRequestViewModel,
    username: String?,
    repositoryName: String?
) {
    Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = "Error: $error", style = MaterialTheme.typography.bodyLarge)
                Button(
                    onClick = {
                        viewModel.getPullRequestInfo(username, repositoryName)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Load More")
                }
            }
        }
    }

    @Preview(showSystemUi = true, showBackground = false)
    @Composable
    fun GitHubScreenPreview(){

    }

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PullRequestCard(
    username: String,
    userImageUrl: String,
    description: String?,
    title: String,
    htmlUrl: String
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))
            context.startActivity(intent)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF007AFF)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = userImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(62.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.CenterEnd
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}