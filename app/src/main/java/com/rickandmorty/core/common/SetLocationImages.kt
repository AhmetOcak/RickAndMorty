package com.rickandmorty.core.common

import com.rickandmorty.R

fun setLocationImage(locationName: String): Int {
    return when (locationName) {
        LocationNames.earth_5_126 -> {
            R.drawable.earth_c_137
        }
        LocationNames.abadango -> {
            R.drawable.abadango
        }
        LocationNames.citadel_of_ricks -> {
            R.drawable.citadel_of_ricks
        }
        LocationNames.anatomy_park -> {
            R.drawable.anatomy_park
        }
        LocationNames.bepis_nine -> {
            R.drawable.bepis_9
        }
        LocationNames.bird_world -> {
            R.drawable.bird_world
        }
        LocationNames.earth_c137 -> {
            R.drawable.earth_c_137
        }
        LocationNames.gromflom_prime -> {
            R.drawable.gromflom_prime
        }
        LocationNames.giants_town -> {
            R.drawable.giant_town
        }
        LocationNames.cronenberg_earth -> {
            R.drawable.cronenberg_earth
        }
        LocationNames.earth_replacement_dimension -> {
            R.drawable.earth_replacement_dimension_
        }
        LocationNames.wordlenders_lair -> {
            R.drawable.worldenders_lair
        }
        LocationNames.interdimensional_cable -> {
            R.drawable.interdimensional
        }
        LocationNames.immortality_field_resort -> {
            R.drawable.immortality_field_resort
        }
        LocationNames.post_apocalyptic_earth -> {
            R.drawable.post_apocalyptic_world
        }
        LocationNames.purge_planet -> {
            R.drawable.purge_planet
        }
        LocationNames.venezunalon_seven -> {
            R.drawable.venzenulon_7
        }
        LocationNames.nuptia_four -> {
            R.drawable.interdimensional
        }
        LocationNames.st_gloopy_noops_hospital -> {
            R.drawable.st_gloopy_noops_hospital
        }
        LocationNames.mr_goldenfolds_dream -> {
            R.drawable.mr_goldenfolds_dream
        }
        else -> {
            R.drawable.no_image_available
        }
    }
}

object LocationNames {
    const val earth_c137 = "Earth (C-137)"
    const val abadango = "Abadango"
    const val citadel_of_ricks = "Citadel of Ricks"
    const val wordlenders_lair = "Worldender's lair"
    const val anatomy_park = "Anatomy Park"
    const val interdimensional_cable = "Interdimensional Cable"
    const val immortality_field_resort = "Immortality Field Resort"
    const val post_apocalyptic_earth = "Post-Apocalyptic Earth"
    const val purge_planet = "Purge Planet"
    const val venezunalon_seven = "Venzenulon 7"
    const val bepis_nine = "Bepis 9"
    const val cronenberg_earth = "Cronenberg Earth"
    const val nuptia_four = "Nuptia 4"
    const val giants_town = "Giant's Town"
    const val bird_world = "Bird World"
    const val st_gloopy_noops_hospital = "St. Gloopy Noops Hospital"
    const val earth_5_126 = "Earth (5-126)"
    const val mr_goldenfolds_dream = "Mr. Goldenfold's dream"
    const val gromflom_prime = "Gromflom Prime"
    const val earth_replacement_dimension = "Earth (Replacement Dimension)"
}