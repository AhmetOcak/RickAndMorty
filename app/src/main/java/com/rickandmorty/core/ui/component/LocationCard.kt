package com.rickandmorty.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rickandmorty.presentation.main.OrientationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(
    modifier: Modifier,
    contentImage: Int,
    locationName: String,
    locationId: Int,
    residents: ArrayList<String>,
    selectedLocationId: Int,
    onClick: (Int, ArrayList<String>) -> Unit
) {
    val locationHeight by remember {
        mutableStateOf(
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                96.dp
            } else {
                48.dp
            }
        )
    }

    val locationWidth by remember {
        mutableStateOf(
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                256.dp
            } else {
                192.dp
            }
        )
    }

    ElevatedCard(
        shape = RoundedCornerShape(10),
        onClick = {
            // Seçili bir lokasyona ikinci kez tıklandığında, karakterleri çağırma fonksiyonunun
            // tekrar çağrılmasını engelliyoruz.
            if (locationId != selectedLocationId) {
                onClick(locationId, residents)
            }
        },
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (selectedLocationId != locationId)
                if (isSystemInDarkTheme()) Color.Gray else Color.LightGray
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = modifier.size(
                width = locationWidth,
                height = locationHeight
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                LocationImage(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentImage = contentImage,
                    isSelected = selectedLocationId == locationId
                )
            }
            LocationName(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                locationName = locationName
            )
        }
    }
}

@Composable
private fun LocationName(modifier: Modifier, locationName: String) {
    Text(
        modifier = modifier,
        text = locationName,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun LocationImage(
    modifier: Modifier,
    contentImage: Int,
    isSelected: Boolean
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = contentImage),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = if (!isSelected) ColorFilter.colorMatrix(
            ColorMatrix().apply {
                setToSaturation(0f)
            }
        ) else null
    )
}