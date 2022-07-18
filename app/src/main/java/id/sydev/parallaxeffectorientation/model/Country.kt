package id.sydev.parallaxeffectorientation.model

import id.sydev.parallaxeffectorientation.R

data class Country(
    val name: String,
    val background: Int,
    val foreGround: Int
)

fun collectCountries(): Array<Country> {
    return arrayOf(
        Country(name = "Brazil", R.drawable.rio_bg, R.drawable.rio),
        Country(name = "France", R.drawable.france_bg, R.drawable.france),
        Country(name = "Iceland", R.drawable.iceland_bg, R.drawable.iceland),
    )
}