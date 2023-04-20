package com.rickandmorty.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val CARD_HEIGHT = 192.dp
private val GENDER_SYMBOL_SIZE = 144.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterShowPlace(
    modifier: Modifier,
    characterImage: String,
    characterName: String,
    genderImage: Int,
    onCharacterClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(CARD_HEIGHT),
        onClick = onCharacterClick,
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomImage(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize(),
                imageUrl = characterImage
            )
            Box(
                modifier = modifier
                    .weight(3f)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CharacterGenderSymbol(
                    modifier = modifier.size(GENDER_SYMBOL_SIZE),
                    genderImage = genderImage
                )
                CharacterName(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    characterName = characterName
                )
            }
        }
    }
}

@Composable
private fun CharacterName(modifier: Modifier, characterName: String) {
    Text(
        modifier = modifier,
        text = characterName,
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CharacterGenderSymbol(modifier: Modifier, genderImage: Int) {
    Image(
        modifier = modifier,
        painter = painterResource(id = genderImage),
        contentDescription = "character gender"
    )
}