package com.rickandmorty.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rickandmorty.R
import com.rickandmorty.core.common.setLocationImage
import com.rickandmorty.core.ui.component.CustomImage
import com.rickandmorty.core.ui.component.onLoadStateAppend
import com.rickandmorty.core.ui.component.onLoadStateRefresh
import com.rickandmorty.core.ui.component.rememberLazyListState
import com.rickandmorty.data.datasource.remote.location.entity.Results
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.presentation.utils.EMPTY_LOCATION_MESSAGE
import com.rickandmorty.presentation.utils.HOME_PAGE_TITLE

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateCharacterDetailScreen: (Character) -> Unit
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(darkIcons = false, color = Color.Transparent)
    }

    HomeScreenContent(
        modifier = modifier,
        locations = viewModel.locations.collectAsLazyPagingItems(),
        getCharacters = { viewModel.getCharacters(it) },
        setCharacterGenderImg = { viewModel.setCharacterGenderImage(it) },
        setCharacterStateError = { viewModel.setCharacterStateError() },
        viewModel = viewModel,
        onNavigateCharacterDetailScreen = { onNavigateCharacterDetailScreen(it) }
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier,
    locations: LazyPagingItems<Results>,
    setCharacterGenderImg: (String) -> Int,
    getCharacters: (ArrayList<String>) -> Unit,
    setCharacterStateError: () -> Unit,
    viewModel: HomeViewModel,
    onNavigateCharacterDetailScreen: (Character) -> Unit
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
                setCharacterGenderImg = setCharacterGenderImg,
                onNavigateCharacterDetailScreen = onNavigateCharacterDetailScreen
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
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        state = locations.rememberLazyListState()
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
            // first location id is equal 1, so we need to get first item from itemSnapshotList
            if (!isCharacterListInit && locations.itemSnapshotList.items.isNotEmpty()) {
                getCharacters(locations.itemSnapshotList.items[0].residents)
                isCharacterListInit = true
            }
        }

        onLoadStateRefresh(
            locations,
            modifier,
            setCharacterStateError
        )
        onLoadStateAppend(
            locations,
            modifier
        )
    }
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
            containerColor = if (selectedLocationId != locationId)
                if (isSystemInDarkTheme()) Color.Gray else Color.LightGray
            else
                MaterialTheme.colorScheme.surface
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
private fun CharactersSection(
    modifier: Modifier,
    characterState: CharacterState,
    setCharacterGenderImg: (String) -> Int,
    onNavigateCharacterDetailScreen: (Character) -> Unit
) {
    when (characterState) {
        is CharacterState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CharacterState.Success -> {
            CharacterList(
                modifier = modifier,
                characterState = characterState,
                setCharacterGenderImg = setCharacterGenderImg,
                onNavigateCharacterDetailScreen = onNavigateCharacterDetailScreen
            )
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
private fun CharacterList(
    characterState: CharacterState.Success,
    modifier: Modifier,
    setCharacterGenderImg: (String) -> Int,
    onNavigateCharacterDetailScreen: (Character) -> Unit
) {
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
                CharacterShowPlace(
                    modifier = modifier,
                    characterImage = it.image,
                    characterName = it.name,
                    genderImage = setCharacterGenderImg(it.gender),
                    onCharacterClick = { onNavigateCharacterDetailScreen(it) }
                )
            }
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
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            painter = painterResource(id = R.drawable.empty_list),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        Text(
            modifier = modifier.padding(top = 8.dp),
            text = EMPTY_LOCATION_MESSAGE,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
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
private fun CharacterShowPlace(
    modifier: Modifier,
    characterImage: String,
    characterName: String,
    genderImage: Int,
    onCharacterClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(192.dp),
        onClick = onCharacterClick,
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

@Composable
private fun PageTitle(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = HOME_PAGE_TITLE,
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