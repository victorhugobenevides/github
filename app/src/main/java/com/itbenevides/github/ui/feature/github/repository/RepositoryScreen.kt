package com.itbenevides.github.ui.feature.github.repository

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Star
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.itbenevides.github.ui.activity.PullRequestActivity
import com.itbenevides.github.ui.components.LoadingView
import com.itbenevides.github.ui.feature.github.StatusResult


@Composable
    fun RepositoryRoute() {
    val viewModel = viewModel<RepositoryViewModel>()
    viewModel.getMoreRepositoryInfo()
    PullRequestState(viewModel)
    }

@Composable
fun PullRequestState(viewModel: RepositoryViewModel){
    val state by viewModel.repositoryInfoState.collectAsStateWithLifecycle()
    RepositoryPage(state, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryPage(state: RepositoryInfoState, viewModel: RepositoryViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
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
                        "GitHub KotlinPop",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
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
        RepositoryList(state = state, viewModel = viewModel, innerPadding )
    }
}

@Composable
fun RepositoryList(
    state: RepositoryInfoState,
    viewModel: RepositoryViewModel,
    innerPadding: PaddingValues
){
        val listState = rememberLazyListState()
        val isScrollToEnd by remember {
            derivedStateOf {
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
            }
        }
        if (isScrollToEnd) {
            viewModel.getMoreRepositoryInfo()
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            items(state.data) { repo ->
                RepositoryCard(
                    repositoryName = repo.name,
                    repositoryDescription = repo.description.toString(),
                    username = repo.owner.login,
                    stars = repo.stargazers_count,
                    forks = repo.forks,
                    userImageUrl = repo.owner.avatar_url
                )
                Spacer(modifier = Modifier.height(2.dp)) // Espaço entre os itens
            }
                if(state.status == StatusResult.Error) {
                    item {
                        RepositoryErrorView(error = state.errorMessages, viewModel)
                    }
                }

                if(state.status == StatusResult.Loading) {
                    item {
                        LoadingView()
                    }
                }
        }
    }

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RepositoryCard(
    repositoryName: String,
    repositoryDescription: String,
    username: String,
    stars: Long,
    forks: Long,
    userImageUrl: String
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            val intent = Intent(context, PullRequestActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("repositoryName", repositoryName)
            context.startActivity(intent)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = repositoryName,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF1A5A7F) // Cor do nome do repositório
                )
                Text(
                    text = repositoryDescription,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.CheckCircle,
                        contentDescription = "Fork Icon",
                        tint = Color(0xFFFFC107), // Cor para Stars
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = forks.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFCCAA22),
                        maxLines = 1,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = "Star Icon",
                        tint = Color(0xFFFFC107), // Cor para Stars
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = stars.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFFC107),
                        maxLines = 1,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 82.dp)
                ) {
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
                    Text(
                        text = username,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF1A5A7F),
                        textAlign = TextAlign.Center,

                        )
                }
            }
        }
    }
}

@Composable
    fun RepositoryErrorView(error: String, viewModel: RepositoryViewModel){
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
                Text(text = "Error: ${error}", style = MaterialTheme.typography.bodyLarge)
                Button(
                    onClick = {
                        viewModel.getMoreRepositoryInfo()
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
    fun RepositoryPreview(){

    }
