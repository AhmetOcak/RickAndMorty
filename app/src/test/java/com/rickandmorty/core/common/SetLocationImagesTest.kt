package com.rickandmorty.core.common

import com.rickandmorty.R
import com.rickandmorty.presentation.home.LocationNames
import com.rickandmorty.presentation.home.setLocationImage
import org.junit.Assert
import org.junit.Test

class SetLocationImagesTest {

    @Test
    fun setLocationImage_MismatchedLocationName_ReturnsNoImageAvailable() {
        val mismatchedLocationName = "mismatchedLocationName"
        val noImageAvailable = R.drawable.no_image_available

        val result = setLocationImage(mismatchedLocationName)

        Assert.assertEquals(result, noImageAvailable)
    }

    @Test
    fun setLocationImage_MatchedLocationName_ReturnsMatchedImage() {
        val matchedLocationName = LocationNames.alien_day_spa
        val matchedImage = R.drawable.alien_day_spa

        val result = setLocationImage(matchedLocationName)

        Assert.assertEquals(result, matchedImage)
    }
}