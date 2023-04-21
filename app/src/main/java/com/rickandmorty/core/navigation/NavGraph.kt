package com.rickandmorty.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.rickandmorty.core.common.encodeForSafe
import com.rickandmorty.core.utils.NAV_ARG_CHARACTER
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.domain.model.character.CharacterLocation
import com.rickandmorty.domain.model.character.Origin
import com.rickandmorty.presentation.character.CharacterDetailScreen
import com.rickandmorty.presentation.home.HomeScreen

/**
 * @param startDestination the route of the start screen.
 * @param navController [NavHostController]
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    startDestination: String = NavScreen.HomeScreen.route,
    navController: NavHostController = rememberAnimatedNavController()
) {
    AnimatedNavHost(
        navController = navController, startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        }
    ) {
        composable(
            route = NavScreen.CharacterDetailScreen.route,
            arguments = listOf(
                navArgument(NAV_ARG_CHARACTER) { type = CharacterNavArgType() }
            )
        ) { navBackStackEntry ->
            val character = navBackStackEntry.arguments?.getString(NAV_ARG_CHARACTER)?.let {
                Gson().fromJson(it, Character::class.java)
            }

            CharacterDetailScreen(
                character = character,
                onNavBackBtnClicked = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = NavScreen.HomeScreen.route) {
            HomeScreen(
                onNavigateCharacterDetailScreen = {
                    val characterDetails = encodeCharacter(it)
                    navController.navigate("${NavNames.character_detail_screen}/${characterDetails}")
                }
            )
        }
    }
}

private fun encodeCharacter(character: Character) = Character(
    id = character.id,
    name = character.name,
    status = character.status,
    species = character.species,
    origin = Origin(
        name = character.origin.name,
        url = encodeForSafe(character.origin.url)
    ),
    location = CharacterLocation(
        locationName = character.location.locationName,
        url = encodeForSafe(character.location.url)
    ),
    url = encodeForSafe(character.url),
    created = character.created,
    image = encodeForSafe(character.image),
    type = character.type,
    gender = character.gender,
    episode = character.episode.map { episode ->
        encodeForSafe(episode)
    } as ArrayList<String>
)