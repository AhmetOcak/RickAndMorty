package com.rickandmorty.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rickandmorty.R

@Composable
fun ErrorPagingItem(modifier: Modifier) {
    ElevatedCard(
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = modifier.size(
                width = 256.dp,
                height = 96.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ErrorItemImage(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            ErrorItemMessage(
                modifier = modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ErrorItemMessage(modifier: Modifier) {
    Text(
        modifier = modifier.padding(top = 8.dp),
        text = "Something happened \uD83D\uDE25",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun ErrorItemImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.error_sign),
        contentDescription = "location load error",
        contentScale = ContentScale.Crop
    )
}