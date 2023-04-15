package com.rickandmorty.core.ui.component

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.rickandmorty.core.ui.theme.NeonGreen400
import com.rickandmorty.data.datasource.remote.location.entity.Results

fun LazyListScope.onLoadStateRefresh(
    locations: LazyPagingItems<Results>,
    modifier: Modifier,
    setCharacterStateError: () -> Unit
) {
    when (val state = locations.loadState.refresh) {
        is LoadState.Error -> {
            setCharacterStateError()
            Log.e("load state refresh error", state.error.stackTraceToString())
            item {
                ErrorPagingItem(modifier = modifier)
            }
        }
        is LoadState.Loading -> {
            item {
                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = NeonGreen400)
                }
            }
        }
        else -> {}
    }
}