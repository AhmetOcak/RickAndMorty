package com.rickandmorty.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.rickandmorty.core.navigation.NavGraph
import com.rickandmorty.core.ui.theme.RickAndMortyTheme
import com.rickandmorty.presentation.utils.LAUNCH_MESSAGE_SHARED_PREF_KEY
import com.rickandmorty.presentation.utils.LAUNCH_MESSAGE_SHARED_PREF_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        sharedPreferences = getSharedPreferences(LAUNCH_MESSAGE_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        coilLoadImage(applicationContext)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            OrientationState.orientation.value = getCurrentOrientation()

            RickAndMortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (sharedPreferences.getBoolean(LAUNCH_MESSAGE_SHARED_PREF_KEY, true)) {
            sharedPreferences.edit().putBoolean(LAUNCH_MESSAGE_SHARED_PREF_KEY, false).apply()
        }
    }

    @Composable
    private fun getCurrentOrientation(): Int {
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

    private fun coilLoadImage(context: Context): coil.ImageLoader.Builder {
        return coil.ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context).build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .build()
            }
    }
}

object OrientationState {
    val orientation: MutableState<Int> = mutableStateOf(Configuration.ORIENTATION_PORTRAIT)
}