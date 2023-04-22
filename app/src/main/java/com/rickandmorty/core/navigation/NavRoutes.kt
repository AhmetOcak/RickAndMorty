package com.rickandmorty.core.navigation

import com.rickandmorty.core.utils.NAV_ARG_CHARACTER

/**
 * This object contains screen routes.
 */
object NavRoutes {
    const val character_detail_screen = "${NavNames.character_detail_screen}/{$NAV_ARG_CHARACTER}"
    const val home_screen = "home_screen"
}

/**
 * This object contains screen names.
 */
object NavNames {
    const val character_detail_screen = "character_detail_screen"
}