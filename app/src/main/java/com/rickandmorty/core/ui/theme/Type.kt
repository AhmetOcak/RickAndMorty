package com.rickandmorty.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rickandmorty.R

private val pageTitleGradient = listOf(
    NeonGreen400,
    NeonGreen300,
    NeonGreen200,
    NeonGreen100,
    NeonYellow
)

private val Avenir = FontFamily(
    Font(R.font.avenir_lts_td_black, FontWeight.Bold),
    Font(R.font.avenir_lts_td_roman, FontWeight.SemiBold),
    Font(R.font.avenir_lts_td_roman, FontWeight.Normal)
)

private val RickAndMorty = FontFamily(
    Font(R.font.get_schwifty, FontWeight.Bold)
)

@OptIn(ExperimentalTextApi::class)
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    displayLarge = TextStyle(
        fontFamily = RickAndMorty,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        brush = Brush.horizontalGradient(pageTitleGradient)
    ),
    displayMedium = TextStyle(
        fontFamily = RickAndMorty,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Avenir,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    )
)