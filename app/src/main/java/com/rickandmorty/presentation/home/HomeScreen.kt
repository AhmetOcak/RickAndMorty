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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rickandmorty.R
import com.rickandmorty.core.common.loadImage
import com.rickandmorty.core.common.setLocationImage

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

    val locationsState by viewModel.locationsState.collectAsState()
    val characterState by viewModel.characterState.collectAsState()

    HomeScreenContent(
        modifier = modifier,
        locationsState = locationsState,
        selectedLocationId = viewModel.selectedLocationId,
        onLocationClicked = {
            viewModel.updateSelectedLocationId(it)
            viewModel.getCharacters()
        },
        characterState = characterState,
        setCharacterGenderImg = { viewModel.setCharacterGenderImage(it) }
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier,
    locationsState: LocationsState,
    selectedLocationId: Int,
    onLocationClicked: (Int) -> Unit,
    characterState: CharacterState,
    setCharacterGenderImg: (String) -> Int
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        RickAndMortyImage(modifier = modifier)
        PageTitle(modifier = modifier)
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationsSection(
                modifier = modifier,
                locationsState = locationsState,
                selectedLocationId = selectedLocationId,
                onLocationClicked = onLocationClicked
            )
            CharactersSection(
                modifier = modifier,
                characterState = characterState,
                setCharacterGenderImg = setCharacterGenderImg
            )
        }
    }
}

@Composable
private fun LocationsSection(
    modifier: Modifier,
    locationsState: LocationsState,
    selectedLocationId: Int,
    onLocationClicked: (Int) -> Unit
) {
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
                items(locationsState.data.results, key = { it.id }) {
                    Location(
                        modifier = modifier,
                        contentImage = setLocationImage(it.name),
                        locationName = it.name,
                        locationId = it.id,
                        isSelected = selectedLocationId == it.id,
                        onClick = onLocationClicked
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
private fun CharactersSection(
    modifier: Modifier,
    characterState: CharacterState,
    setCharacterGenderImg: (String) -> Int
) {
    when (characterState) {
        is CharacterState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CharacterState.Success -> {
            if (characterState.data.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyLocation(modifier = modifier)
                }
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(characterState.data, key = { it.id }) {
                        Character(
                            modifier = modifier,
                            characterImage = it.image,
                            characterName = it.name,
                            genderImage = setCharacterGenderImg(it.gender)
                        )
                    }
                }
            }
        }
        is CharacterState.Error -> {
            Toast.makeText(
                LocalContext.current,
                characterState.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Character(
    modifier: Modifier,
    characterImage: String,
    characterName: String,
    genderImage: Int
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
            AsyncImage(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize(),
                model = loadImage(context = LocalContext.current, imageUrl = characterImage),
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
                    painter = painterResource(id = genderImage),
                    contentDescription = "character gender"
                )
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
private fun Location(
    modifier: Modifier,
    contentImage: Int,
    locationName: String,
    locationId: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(10),
        onClick = { onClick(locationId) },
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (!isSelected) Color.LightGray else MaterialTheme.colorScheme.surface
        )
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
                contentScale = ContentScale.Crop,
                colorFilter = if (!isSelected) ColorFilter.colorMatrix(
                    ColorMatrix().apply {
                        setToSaturation(0f)
                    }
                ) else null
            )
            Text(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                text = locationName,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EmptyLocation(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.size(96.dp),
            painter = painterResource(id = R.drawable.empty_box),
            contentDescription = null
        )
        Text(
            modifier = modifier.padding(top = 8.dp),
            text = "Apparently no one lives here.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
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