package com.rickandmorty.presentation.main

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.rickandmorty.R
import com.rickandmorty.presentation.utils.LAUNCH_MESSAGE_SHARED_PREF_KEY
import com.rickandmorty.presentation.utils.LAUNCH_MESSAGE_SHARED_PREF_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RickAndMortyApplication : Application() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(LAUNCH_MESSAGE_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        // We call the showLaunchMessage() function here
        // because we don't want it to be called repeatedly due to configuration changes.
        showLaunchMessage()
    }

    /**
     * The function displays a welcome message if the application is launched for the first time.
     * Otherwise function shows a "Hello!" message.
     * Otherwise, the function displays a hello message.
     */
    private fun showLaunchMessage() {
        // We saved a boolean value when the app launch.
        // If the app launched for the first time our saved value is null.
        // If value is null then we display welcome message.
        // Otherwise our saved value is false.
        // Then we display hello message.
        if (sharedPreferences.getBoolean(LAUNCH_MESSAGE_SHARED_PREF_KEY, true)) {
            Toast.makeText(
                this.applicationContext,
                getString(R.string.first_launch_message),
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this.applicationContext,
                getString(R.string.launch_message),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}