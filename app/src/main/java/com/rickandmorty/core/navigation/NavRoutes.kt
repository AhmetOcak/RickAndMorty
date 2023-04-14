package com.rickandmorty.core.navigation

import com.rickandmorty.core.utils.NAV_ARG_CHARACTER

object NavRoutes {
    const val character_detail_screen = "${NavNames.character_detail_screen}/{$NAV_ARG_CHARACTER}"
    const val home_screen = "home_screen"
}

object NavNames {
    const val character_detail_screen = "character_detail_screen"
}