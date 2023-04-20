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

    // We call the showLaunchMessage() function here because we don't want it to be called repeatedly due to configuration changes.

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(LAUNCH_MESSAGE_SHARED_PREF_NAME, Context.MODE_PRIVATE)

        showLaunchMessage()
    }

    private fun showLaunchMessage() {
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