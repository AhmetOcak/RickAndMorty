package com.rickandmorty.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

    HomeScreenContent(modifier = modifier)
}

@Composable
private fun HomeScreenContent(modifier: Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        RickAndMortyImage(modifier = modifier)
        PageTitle(modifier = modifier)
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationsSection(modifier = modifier)
            CharactersSection(modifier = modifier)
        }
    }
}

@Composable
private fun LocationsSection(modifier: Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = LocalConfiguration.current.screenHeightDp.dp / 3 - 48.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            Location(
                modifier = modifier,
                contentImage = R.drawable.location_earth,
                location = "Earth"
            )
        }
    }
}

@Composable
private fun CharactersSection(modifier: Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            Character(
                modifier = modifier,
                characterImage = R.drawable.test,
                characterName = "Beth Smith",
                gender = "male"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Character(
    modifier: Modifier,
    characterImage: Int,
    characterName: String,
    gender: String
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(192.dp),
        onClick = {},
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize(),
                painter = painterResource(id = characterImage),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = modifier
                    .weight(3f)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = modifier.size(144.dp),
                    painter = painterResource(id = R.drawable.male),
                    contentDescription = "character gender $gender"
                )
                Text(
                    text = characterName,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Location(modifier: Modifier, contentImage: Int, location: String) {
    ElevatedCard(
        shape = RoundedCornerShape(10),
        onClick = {}
    ) {
        Row(
            modifier = modifier.size(
                width = 256.dp,
                height = 96.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize(),
                painter = painterResource(id = contentImage),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = modifier.weight(1f),
                text = location,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PageTitle(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth().padding(top = 32.dp),
        text = "Rick and Morty",
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun RickAndMortyImage(modifier: Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 3),
            painter = painterResource(id = R.drawable.rick_and_morty),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}