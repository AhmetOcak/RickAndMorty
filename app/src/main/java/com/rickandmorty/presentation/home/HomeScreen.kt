package com.rickandmorty.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.rickandmorty.R
import com.rickandmorty.core.common.setLocationImage
import com.rickandmorty.core.ui.component.CustomImage
import com.rickandmorty.data.datasource.remote.location.entity.Results

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

    val locations = viewModel.getLocations().collectAsLazyPagingItems()

    HomeScreenContent(
        modifier = modifier,
        locations = locations,
        getCharacters = { viewModel.getCharacters(it) },
        setCharacterGenderImg = { viewModel.setCharacterGenderImage(it) },
        setCharacterStateError = { viewModel.setCharacterStateError() },
        viewModel = viewModel
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier,
    locations: LazyPagingItems<Results>,
    setCharacterGenderImg: (String) -> Int,
    getCharacters: (ArrayList<String>) -> Unit,
    setCharacterStateError: () -> Unit,
    viewModel: HomeViewModel
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        RickAndMortyImage(modifier = modifier)
        PageTitle(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        )
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationsSection(
                modifier = modifier,
                locations = locations,
                getCharacters = getCharacters,
                setCharacterStateError = setCharacterStateError
            )
            CharactersSection(
                modifier = modifier,
                characterState = viewModel.characterState.collectAsState().value,
                setCharacterGenderImg = setCharacterGenderImg
            )
        }
    }
}

@Composable
private fun LocationsSection(
    modifier: Modifier,
    locations: LazyPagingItems<Results>,
    getCharacters: (ArrayList<String>) -> Unit,
    setCharacterStateError: () -> Unit
) {
    var selectedLocationId by rememberSaveable { mutableStateOf(1) }
    var isCharacterListInit by rememberSaveable { mutableStateOf(false) }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = LocalConfiguration.current.screenHeightDp.dp / 3 - 48.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(locations, key = { it.id }) {
            Location(
                modifier = modifier,
                contentImage = setLocationImage(it?.name ?: ""),
                locationName = it?.name ?: "Unknown",
                locationId = it?.id ?: -1,
                residents = it?.residents ?: arrayListOf(),
                selectedLocationId = selectedLocationId,
                onClick = { locationId, residents ->
                    selectedLocationId = locationId
                    getCharacters(residents)
                }
            )

            // init character list
            // first location is equal 1, soo we need get first item from itemSnapshotList
            if (!isCharacterListInit && locations.itemSnapshotList.items.isNotEmpty()) {
                getCharacters(locations.itemSnapshotList.items[0].residents)
                isCharacterListInit = true
            }
        }

        onLoadStateRefresh(
            locations, modifier,
            setCharacterStateError = setCharacterStateError
        )
        onLoadStateAppend(
            locations, modifier,
            setCharacterStateError = setCharacterStateError
        )
    }
}

private fun LazyListScope.onLoadStateAppend(
    locations: LazyPagingItems<Results>,
    modifier: Modifier,
    setCharacterStateError: () -> Unit
) {
    when (val state = locations.loadState.append) {
        is LoadState.Error -> {
            setCharacterStateError()
            Log.e("load state append error", state.error.stackTraceToString())
        }
        is LoadState.Loading -> {
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        else -> {}
    }
}

private fun LazyListScope.onLoadStateRefresh(
    locations: LazyPagingItems<Results>,
    modifier: Modifier,
    setCharacterStateError: () -> Unit
) {
    when (val state = locations.loadState.refresh) {
        is LoadState.Error -> {
            setCharacterStateError()
            Log.e("load state refresh error", state.error.stackTraceToString())
        }
        is LoadState.Loading -> {
            item {
                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        else -> {}
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
            GetCharactersFailMessage(
                modifier = modifier,
                characterState = characterState
            )
        }
    }
}

@Composable
private fun GetCharactersFailMessage(
    modifier: Modifier,
    characterState: CharacterState.Error
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = modifier.size(252.dp),
            contentDescription = "Error",
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.error_image)
        )
    }
    Toast.makeText(
        LocalContext.current,
        characterState.message,
        Toast.LENGTH_LONG
    ).show()
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
            CustomImage(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize(),
                imageUrl = characterImage
            )
            Box(
                modifier = modifier
                    .weight(3f)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CharacterGenderSymbol(
                    modifier = modifier.size(144.dp),
                    genderImage = genderImage
                )
                CharacterName(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), characterName = characterName
                )
            }
        }
    }
}

@Composable
private fun CharacterName(modifier: Modifier, characterName: String) {
    Text(
        modifier = modifier,
        text = characterName,
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CharacterGenderSymbol(modifier: Modifier, genderImage: Int) {
    Image(
        modifier = modifier,
        painter = painterResource(id = genderImage),
        contentDescription = "character gender"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Location(
    modifier: Modifier,
    contentImage: Int,
    locationName: String,
    locationId: Int,
    residents: ArrayList<String>,
    selectedLocationId: Int,
    onClick: (Int, ArrayList<String>) -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(10),
        onClick = {
            // Seçili bir lokasyona ikinci kez tıklandığında, karakterleri çağırma fonksiyonunun
            // tekrar çağrılmasını engelliyoruz.
            if (locationId != selectedLocationId) {
                onClick(locationId, residents)
            }
        },
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (selectedLocationId != locationId) Color.LightGray else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = modifier.size(
                width = 256.dp,
                height = 96.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LocationImage(
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentImage = contentImage,
                isSelected = selectedLocationId == locationId
            )
            LocationName(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                locationName = locationName
            )
        }
    }
}

@Composable
private fun LocationName(modifier: Modifier, locationName: String) {
    Text(
        modifier = modifier,
        text = locationName,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun LocationImage(
    modifier: Modifier,
    contentImage: Int,
    isSelected: Boolean
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = contentImage),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = if (!isSelected) ColorFilter.colorMatrix(
            ColorMatrix().apply {
                setToSaturation(0f)
            }
        ) else null
    )
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
        modifier = modifier,
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