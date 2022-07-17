package id.sydev.parallaxeffectorientation.model

import id.sydev.parallaxeffectorientation.R

data class City(
    val name: String,
    val background: Int,
    val foreGround: Int
)

fun collectCities(): Array<City> {
    return arrayOf(
        City(name = "Rio", R.drawable.rio_bg, R.drawable.rio),
        City(name = "Paris", R.drawable.france_bg, R.drawable.france),
        City(name = "Iceland", R.drawable.iceland_bg, R.drawable.iceland),
    )
}