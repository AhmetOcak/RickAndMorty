package com.rickandmorty.core.common

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun getCurrentOrientation(): Int {
    val conf = LocalConfiguration.current
    return when (conf.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Configuration.ORIENTATION_LANDSCAPE
        }
        else -> {
            Configuration.ORIENTATION_PORTRAIT
        }
    }
}