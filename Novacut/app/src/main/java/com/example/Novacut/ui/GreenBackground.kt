package com.example.NovaCut.ui

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.MeshGradientPainter
import com.example.NovaCut.ui.theme.NovaCutTheme

@Composable
fun GreenBackground(modifier : Modifier = Modifier) {
    Box(modifier = modifier) {
        val Concept = rememberInfiniteTransition( label = "first" )
        val animatedOffset by Concept.animateFloat(
        initialValue = 0f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
    animation = tween(5000, easing = FastOutLinearInEasing),
    repeatMode = RepeatMode.Reverse
    ),
label = "speed"
)

val darkGreen = Color(0xFF006400)
val forestGreen = Color(0xFF228B22)
val lightGreen = Color(0xFF66BB6A)
val paleGreen = Color(0xFFC8E6C9)
val whiteSmoke = Color(0xFFF5F5F5)
val ghostWhite = Color(0xFFF8F8FF)
val snow = Color(0xFFFFFAFA)
val white = Color(0xFFFFFFFF)

val gradientPainter = remember(animatedOffset) {
    MeshGradientPainter(rows = 3, columns = 2) {
        // Row 0 — deep anchor along the top, dark to light
        setVertex(0, 0, Offset(0.0f, 0.0f), darkGreen)
        setVertex(0, 1, Offset(0.5f, 0.0f) + Offset(animatedOffset * 0.5f, 0f), paleGreen)
        setVertex(0, 2, Offset(1.0f, 0.0f), whiteSmoke)

        // Row 1 — the chaotic middle band, offsets pulling opposite directions
        setVertex(1, 0, Offset(0.0f, 0.3f) + Offset(animatedOffset, -animatedOffset), forestGreen)
        setVertex(1, 1, Offset(0.45f, 0.35f) + Offset(-animatedOffset * 1.4f, animatedOffset), snow)
        setVertex(1, 2, Offset(1.0f, 0.3f) + Offset(-animatedOffset, animatedOffset * 0.6f), lightGreen)

        // Row 2 — second chaotic band, inverse phase to row 1 for cross-current motion
        setVertex(2, 0, Offset(0.0f, 0.7f) + Offset(-animatedOffset, animatedOffset), ghostWhite)
        setVertex(2, 1, Offset(0.5f, 0.75f) + Offset(animatedOffset * 1.4f, -animatedOffset), forestGreen)
        setVertex(2, 2, Offset(1.0f, 0.7f) + Offset(animatedOffset, -animatedOffset * 0.6f), darkGreen)

        // Row 3 — settle back to a clean anchor along the bottom
        setVertex(3, 0, Offset(0.0f, 1.0f), lightGreen)
        setVertex(3, 1, Offset(0.5f, 1.0f) + Offset(-animatedOffset * 0.5f, 0f), white)
        setVertex(3, 2, Offset(1.0f, 1.0f), paleGreen)
            }
        }
    }
}
