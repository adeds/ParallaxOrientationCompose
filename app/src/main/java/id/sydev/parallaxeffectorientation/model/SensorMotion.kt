package id.sydev.parallaxeffectorientation.model

data class SensorMotion(val z: Float = 0f, val rotation: Float = 0f) {
    val background = z * rotation
    val foreground = (-z) * rotation
}