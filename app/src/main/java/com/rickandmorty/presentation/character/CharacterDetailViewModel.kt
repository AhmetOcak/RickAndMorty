package com.rickandmorty.presentation.character

import android.net.Uri
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rickandmorty.domain.model.character.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class CharacterDetailViewModel @Inject constructor() : ViewModel() {

    var isCharacterDataNull by mutableStateOf(false)
        private set

    var characterImage = ""
        private set
    var characterName = ""
        private set

    var characterDetails: MutableMap<String, String> = mutableStateMapOf()
        private set

    fun setDetails(character: Character) {
        characterImage = Uri.decode(character.image)
        characterName = character.name

        val episodeNumbers: ArrayList<String> = arrayListOf()
        character.episode.forEach {
            val uri = Uri.parse(Uri.decode(it))
            episodeNumbers.add(
                uri.lastPathSegment ?: ""
            )
        }

        var createdDate = character.created
        createdDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = OffsetDateTime.parse(createdDate)
            val formatter = DateTimeFormatter.ofPattern("dd MMM uuuu, HH:mm:ss", Locale.ENGLISH)
            formatter.format(date)
        } else {
            val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.ENGLISH)
            val date = formatter.parse(createdDate)
            date?.toString() ?: "unknown"
        }

        characterDetails = linkedMapOf(
            STATUS to character.status,
            SPECY to character.species,
            GENDER to character.gender,
            ORIGIN to character.origin.name,
            LOCATION to character.location.locationName,
            EPISODES to episodeNumbers.toString().drop(1).dropLast(1),
            CREATED_DATE to createdDate
        )
    }

    fun setCharacterDataNull() {
        isCharacterDataNull = true
    }

    companion object {
        const val STATUS = "Status"
        const val SPECY = "Specy"
        const val GENDER = "Gender"
        const val ORIGIN = "Origin"
        const val LOCATION = "Location"
        const val EPISODES = "Episodes"
        const val CREATED_DATE = "Created at (in API)"
    }
}