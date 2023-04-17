package com.rickandmorty.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.rickandmorty.core.common.ImageLoader
import com.rickandmorty.core.navigation.NavGraph
import com.rickandmorty.core.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

private const val LAUNCH_MESSAGE_SHARED_PREF_NAME = "launch_message"
private const val LAUNCH_MESSAGE_SHARED_PREF_KEY = "first_launch"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        sharedPreferences = getSharedPreferences(LAUNCH_MESSAGE_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        showLaunchMessage()

        ImageLoader.load(applicationContext)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
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

    private fun showLaunchMessage() {
        if (sharedPreferences.getBoolean(LAUNCH_MESSAGE_SHARED_PREF_KEY, true)) {
            Toast.makeText(
                this.applicationContext,
                "Welcome!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this.applicationContext,
                "Hello!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}