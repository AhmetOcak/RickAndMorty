package com.rickandmorty.presentation.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rickandmorty.R
import com.rickandmorty.core.ui.component.CustomImage
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.presentation.utils.NO_CHARACTER_DETAIL_MESSAGE

private val CHARACTER_IMAGE_SIZE = 275.dp

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    character: Character?,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
    onNavBackBtnClicked: () -> Unit
) {

    if (character != null) {
        viewModel.setDetails(character)
    } else {
        viewModel.setCharacterDataNull()
    }

    val systemUiController = rememberSystemUiController()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setStatusBarColor(
            darkIcons = !isSystemInDarkTheme,
            color = Color.Transparent
        )
    }

    CharacterDetailScreenContent(
        modifier = modifier,
        isCharacterDataNull = viewModel.isCharacterDataNull,
        characterImage = viewModel.characterImage,
        characterName = viewModel.characterName,
        characterDetails = viewModel.characterDetails,
        onNavBackBtnClicked = onNavBackBtnClicked
    )
}

@Composable
private fun CharacterDetailScreenContent(
    modifier: Modifier,
    isCharacterDataNull: Boolean,
    characterImage: String,
    characterName: String,
    characterDetails: MutableMap<String, String>,
    onNavBackBtnClicked: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CharacterDetailTopBar(
                modifier = modifier,
                characterName = characterName,
                onNavBackBtnClicked = onNavBackBtnClicked
            )
        }
    ) {
        if (isCharacterDataNull) {
            NoCharacterDetail(modifier = modifier)
        } else {
            CharacterContent(
                modifier = modifier,
                paddingValues = it,
                characterImage = characterImage,
                characterDetails = characterDetails
            )
        }
    }
}

@Composable
private fun CharacterContent(
    modifier: Modifier,
    paddingValues: PaddingValues,
    characterImage: String,
    characterDetails: MutableMap<String, String>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomImage(
            modifier = modifier
                .size(CHARACTER_IMAGE_SIZE)
                .padding(horizontal = 50.dp, vertical = 20.dp),
            imageUrl = characterImage
        )
        CharacterDetailSection(
            modifier = modifier,
            characterDetails = characterDetails
        )
    }
}

@Composable
private fun CharacterDetailSection(
    modifier: Modifier,
    characterDetails: MutableMap<String, String>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .padding(horizontal = 20.dp)
    ) {
        characterDetails.map {
            CharacterDetail(
                modifier = modifier,
                title = it.key,
                description = it.value
            )
        }
    }
}

@Composable
private fun CharacterDetail(
    modifier: Modifier,
    title: String,
    description: String
) {
    Row(
        modifier = if (title == "Status") {
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
        DetailTitle(
            modifier = modifier.width(126.dp),
            title = title
        )
        DetailDescription(
            modifier = modifier.padding(start = 20.dp),
            description = description
        )
    }
}

@Composable
private fun DetailDescription(modifier: Modifier, description: String) {
    Text(
        modifier = modifier,
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 3
    )
}

@Composable
private fun DetailTitle(modifier: Modifier, title: String) {
    Text(
        modifier = modifier,
        text = "$title:",
        style = MaterialTheme.typography.titleMedium
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailTopBar(
    modifier: Modifier,
    characterName: String,
    onNavBackBtnClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp),
                text = characterName,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavBackBtnClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "return to home page"
                )
            }
        }
    )
}

@Composable
private fun NoCharacterDetail(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NoDetailImage(
            modifier = modifier
                .size(275.dp)
                .padding(horizontal = 20.dp)
        )
        NoDetailMessage(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        )
    }
}

@Composable
private fun NoDetailMessage(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = NO_CHARACTER_DETAIL_MESSAGE,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun NoDetailImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.empty_list),
        contentDescription = "No character details."
    )
}