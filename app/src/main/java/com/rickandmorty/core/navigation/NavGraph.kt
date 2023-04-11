package com.rickandmorty.core.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.rickandmorty.presentation.character.CharacterDetailScreen
import com.rickandmorty.presentation.home.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = NavScreen.HomeScreen.route
) {
    val navController = rememberAnimatedNavController()

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
        composable(route = NavScreen.CharacterDetailScreen.route) {
            CharacterDetailScreen()
        }
        composable(route = NavScreen.HomeScreen.route) {
            HomeScreen()
        }
    }
}