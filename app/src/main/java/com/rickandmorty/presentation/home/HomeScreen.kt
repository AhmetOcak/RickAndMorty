package com.rickandmorty.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rickandmorty.R
import com.rickandmorty.core.common.setLocationImage

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

    val locationsState by viewModel.locationsState.collectAsState()

    HomeScreenContent(modifier = modifier, locationsState = locationsState)
}

@Composable
private fun HomeScreenContent(modifier: Modifier, locationsState: LocationsState) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        RickAndMortyImage(modifier = modifier)
        PageTitle(modifier = modifier)
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationsSection(modifier = modifier, locationsState = locationsState)
            CharactersSection(modifier = modifier)
        }
    }
}

@Composable
private fun LocationsSection(modifier: Modifier, locationsState: LocationsState) {
    when (locationsState) {
        is LocationsState.Loading -> {
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Green)
            }
        }
        is LocationsState.Success -> {
            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = LocalConfiguration.current.screenHeightDp.dp / 3 - 48.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(locationsState.data.results) {
                    Location(
                        modifier = modifier,
                        contentImage = setLocationImage(it.name),
                        location = it.name
                    )
                }
            }
        }
        is LocationsState.Error -> {
            Toast.makeText(
                LocalContext.current,
                locationsState.message,
                Toast.LENGTH_LONG
            ).show()
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
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
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
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
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