package com.rickandmorty.core.navigation

/**
 * This class provide screens.
 * @property route the route of the screen.
 */
sealed class NavScreen(val route: String) {
    object CharacterDetailScreen : NavScreen(route = NavRoutes.character_detail_screen)
    object HomeScreen : NavScreen(route = NavRoutes.home_screen)
}