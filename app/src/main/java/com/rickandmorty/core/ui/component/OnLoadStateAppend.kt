package com.rickandmorty.core.ui.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rickandmorty.core.ui.theme.NeonGreen400
import com.rickandmorty.data.datasource.remote.location.entity.Results

fun LazyListScope.onLoadStateAppend(
    locations: LazyPagingItems<Results>,
    modifier: Modifier
) {
    when (val state = locations.loadState.append) {
        is LoadState.Error -> {
            item {
                ErrorPagingItem(modifier = modifier)
            }
            Log.e("load state append error", state.error.stackTraceToString())
        }
        is LoadState.Loading -> {
            item {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(color = NeonGreen400)
                }
            }
        }
        else -> {}
    }
}