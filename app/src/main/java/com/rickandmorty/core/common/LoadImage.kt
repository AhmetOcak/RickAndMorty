package com.rickandmorty.core.common

import android.content.Context
import coil.request.ImageRequest
import com.rickandmorty.R

fun loadImage(
    context: Context,
    imageUrl: String,
    placeHolderAndErrorImage: Int = R.drawable.no_image_available
): ImageRequest {
    return ImageRequest.Builder(context = context)
        .data(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .placeholder(placeHolderAndErrorImage)
        .error(placeHolderAndErrorImage)
        .build()
}