package com.rickandmorty.presentation.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rickandmorty.R
import com.rickandmorty.core.ui.component.CharacterShowPlace
import com.rickandmorty.core.ui.component.LoadingIndicator
import com.rickandmorty.core.ui.component.Location
import com.rickandmorty.core.ui.component.onLoadStateAppend
import com.rickandmorty.core.ui.component.onLoadStateRefresh
import com.rickandmorty.core.ui.component.rememberLazyListState
import com.rickandmorty.data.datasource.remote.location.entity.Results
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.presentation.main.OrientationState
import com.rickandmorty.presentation.utils.EMPTY_LOCATION_MESSAGE
import com.rickandmorty.presentation.utils.HOME_PAGE_TITLE

private val PORTRAIT_PADDING = 48.dp
private val LANDSCAPE_PADDING = 24.dp

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

    val locationPadding by remember {
        mutableStateOf(
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_PADDING
            } else {
                LANDSCAPE_PADDING
            }
        )
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = LocalConfiguration.current.screenHeightDp.dp / 3 - locationPadding),
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
            // In order to get the characters from the API, we first need to get the positions.
            // Once locations are received, we use the residents of the first location to initialize the character list.
            // First location id is equal 1, so we need to get first item from itemSnapshotList
            if (!isCharacterListInit && locations.itemSnapshotList.items.isNotEmpty()) {
                getCharacters(locations.itemSnapshotList.items[0].residents)

                // We indicate that the character list is initialized.
                // Thus, the next process of getting characters from the API will be done according to the location to be selected.
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

@Composable
private fun CharactersSection(
    modifier: Modifier,
    characterState: CharacterState,
    setCharacterGenderImg: (String) -> Int,
    onNavigateCharacterDetailScreen: (Character) -> Unit
) {
    when (characterState) {
        is CharacterState.Loading -> {
            LoadingIndicator(modifier = modifier.fillMaxSize())
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
        if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
            PortraitCharacterList(
                modifier = modifier,
                characterState = characterState,
                setCharacterGenderImg = setCharacterGenderImg,
                onNavigateCharacterDetailScreen = onNavigateCharacterDetailScreen
            )
        } else {
            LandscapeCharacterList(
                modifier = modifier,
                characterState = characterState,
                setCharacterGenderImg = setCharacterGenderImg,
                onNavigateCharacterDetailScreen = onNavigateCharacterDetailScreen
            )
        }
    }
}

@Composable
private fun PortraitCharacterList(
    modifier: Modifier,
    characterState: CharacterState.Success,
    setCharacterGenderImg: (String) -> Int,
    onNavigateCharacterDetailScreen: (Character) -> Unit
) {
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

@Composable
private fun LandscapeCharacterList(
    modifier: Modifier,
    characterState: CharacterState.Success,
    setCharacterGenderImg: (String) -> Int,
    onNavigateCharacterDetailScreen: (Character) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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

@Composable
private fun EmptyLocation(modifier: Modifier) {
    if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
        EmptyLocationPortrait(modifier)
    } else {
        EmptyLocationLandscape(modifier)
    }
}

@Composable
private fun EmptyLocationPortrait(modifier: Modifier) {
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
private fun EmptyLocationLandscape(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .fillMaxHeight()
                .padding(vertical = 16.dp),
            painter = painterResource(id = R.drawable.empty_list),
            contentScale = ContentScale.Fit,
            contentDescription = null
        )
        Spacer(modifier = modifier.width(32.dp))
        Text(
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