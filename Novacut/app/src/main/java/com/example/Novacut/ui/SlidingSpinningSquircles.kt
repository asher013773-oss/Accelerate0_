package com.example.Novacut.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random
import com.example.Novacut.ui.theme.NovacutTheme

/**
 * A true-ish squircle (superellipse approximation via cubic Beziers).
 * cornerSmoothing closer to 1f -> more circular; closer to 0.4f-0.5f -> more square with soft corners.
 */
class SquircleShape(private val cornerSmoothing: Float = 0.6f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val w = size.width
            val h = size.height
            val rx = w / 2f * cornerSmoothing
            val ry = h / 2f * cornerSmoothing

            moveTo(w / 2f, 0f)
            cubicTo(w - rx, 0f, w, ry, w, h / 2f)
            cubicTo(w, h - ry, w - rx, h, w / 2f, h)
            cubicTo(rx, h, 0f, h - ry, 0f, h / 2f)
            cubicTo(0f, ry, rx, 0f, w / 2f, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun SlidingSpinningSquircles(
    modifier: Modifier = Modifier,
    count: Int = 5
) {
    // 1f = fully displaced (off-box below), 0f = resting position.
    // All start at 1f -- randomness here comes from the stagger delay below, not the start value.
    val offsets = remember { List(count) { Animatable(1f) } }
    val rotations = remember { List(count) { Animatable(0f) } }

    // Track each box's own measured size in px, so the slide distance
    // scales with whatever this box resolves to on this device/layout.
    val boxSizes = remember { List(count) { mutableStateOf(IntSize.Zero) } }

    LaunchedEffect(Unit) {
        offsets.forEachIndexed { index, offsetAnim ->
            launch {
                // randomized stagger instead of a fixed index * 100L,
                // so the slide-ins don't feel mechanically evenly spaced
                delay(Random.nextLong(0, 400))

                // each circle gets its own random resting height -- from fully aligned (0f)
                // to noticeably lower than its own box height (1.1f) -- instead of every
                // circle converging on the same line
                val restingOffset = Random.nextFloat() * 1.1f

                offsetAnim.animateTo(
                    targetValue = restingOffset,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )

                // once settled, spin forever with randomly varying lap speed
                val rotationAnim = rotations[index]
                while (isActive) {
                    val randomDuration = Random.nextInt(400, 2000)
                    rotationAnim.animateTo(
                        targetValue = rotationAnim.value + 360f,
                        animationSpec = tween(
                            durationMillis = randomDuration,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth(0.45f) // this whole feature occupies 45% of the viewport width
            .aspectRatio(1.1f)   // loosened from 2f so there's vertical room for the stagger to read clearly
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            offsets.forEachIndexed { index, offsetAnim ->
                Box(
                    modifier = Modifier
                        .weight(1f) // shares space proportionally with siblings, scales if count grows
                        .sizeIn(minWidth = 32.dp, minHeight = 32.dp) // floor so circles stay visible/tappable even when squeezed
                        .aspectRatio(1f)
                        .onSizeChanged { boxSizes[index].value = it }
                        .offset {
                            val heightPx = boxSizes[index].value.height
                            IntOffset(0, (offsetAnim.value * heightPx).roundToInt())
                        }
                        .graphicsLayer { rotationZ = rotations[index].value }
                        .clip(SquircleShape(cornerSmoothing = 0.6f))
                        .background(if (index % 2 == 0) Color.Black else Color.Gray)
                )
            }
        }
    }
}
