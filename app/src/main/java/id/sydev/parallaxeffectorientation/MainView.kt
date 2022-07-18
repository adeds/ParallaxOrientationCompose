package id.sydev.parallaxeffectorientation

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import id.sydev.parallaxeffectorientation.model.Country
import id.sydev.parallaxeffectorientation.model.SensorMotion
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainView(motion: SensorMotion, cities: Array<Country>) {
    val minZoom = 1.2f
    val maxZoom = 1.5f
    Column(Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()
        val scale = remember { mutableStateOf(1f) }
        HorizontalPager(
            count = cities.size,
            state = pagerState,
            // Add 32.dp horizontal padding to 'center' the pages
            contentPadding = PaddingValues(horizontal = 32.dp),
            modifier = Modifier
                .padding(top = 16.dp)
                .weight(1f)
                .fillMaxWidth(),
        ) { page ->
            Card(
                modifier = Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, rotation ->
                            scale.value *= zoom
                        }
                    },
            ) {
                Image(
                    modifier = Modifier.fillMaxSize()
                        .graphicsLayer(
                            scaleX = maxOf(minZoom, minOf(maxZoom, scale.value)),
                            scaleY = maxOf(minZoom, minOf(maxZoom, scale.value)),
                            rotationZ = 1f
                        ),
                    painter = painterResource(cities[page].background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = BiasAlignment(
                        horizontalBias = motion.background*scale.value,
                        verticalBias = 0f
                    )
                )

                Image(
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = maxOf(minZoom, minOf(maxZoom, scale.value)),
                            scaleY = maxOf(minZoom, minOf(maxZoom, scale.value)),
                            rotationZ = 1f
                        ).fillMaxSize(),
                    painter = painterResource(cities[page].foreGround),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = BiasAlignment(
                        horizontalBias = motion.foreground*scale.value,
                        verticalBias = 1f
                    )
                )
            }

        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )
    }
}