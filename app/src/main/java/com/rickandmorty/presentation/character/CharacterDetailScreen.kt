package com.rickandmorty.presentation.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rickandmorty.R

@Composable
fun CharacterDetailScreen(modifier: Modifier = Modifier) {

    CharacterDetailScreenContent(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailScreenContent(modifier: Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CharacterDetailTopBar(
                modifier = modifier,
                characterName = "Beth Smith",
                onNavBtnClicked = {}
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CharacterImageSection(modifier = modifier)
            CharacterDetailSection(modifier = modifier)
        }
    }
}

@Composable
private fun CharacterImageSection(modifier: Modifier) {
    Image(
        modifier = modifier
            .size(275.dp)
            .padding(horizontal = 50.dp, vertical = 20.dp),
        painter = painterResource(id = R.drawable.test),
        contentDescription = null
    )
}

@Composable
private fun CharacterDetailSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .padding(horizontal = 20.dp)
    ) {
        for (i in 0..6) {
            CharacterDetail(
                modifier = modifier,
                title = detailTitles[i],
                description = values[i]
            )
        }
    }
}

@Composable
private fun CharacterDetail(modifier: Modifier, title: String, description: String) {
    Row(
        modifier = if (title == "status") {
            modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        } else {
            modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.width(126.dp),
            text = "$title:",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = modifier.padding(start = 20.dp),
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun CharacterDetailTopBar(
    modifier: Modifier,
    characterName: String,
    onNavBtnClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                text = characterName,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {},
        navigationIcon = {
            IconButton(onClick = onNavBtnClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "return to home page"
                )
            }
        }
    )
}

private val detailTitles = listOf(
    "Status",
    "Specy",
    "Gender",
    "Origin",
    "Location",
    "Episodes",
    "Created at (in API)"
)

private val values = listOf(
    "Alive",
    "Human",
    "Female",
    "Earth (C-137)",
    "Earth (C-137)",
    "1, 2, 3, 4, 5, 6, 22, 51",
    "5 May 2017, 09:48:44"
)