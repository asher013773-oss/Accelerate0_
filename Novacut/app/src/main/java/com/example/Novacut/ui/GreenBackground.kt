package com.example.Novacut.ui

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.MeshGradientPainter
import com.example.Novacut.ui.theme.NovacutTheme

@Composable
fun GreenBackground(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        val Concept = rememberInfiniteTransition(label = "first")
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
            MeshGradientPainter(rows = 4, columns = 3) {
                // Row 0 — anchor top edge, dark corners bleeding into light center
                setVertex(0, 0, Offset(0.0f, 0.0f), darkGreen)
                setVertex(0, 1, Offset(0.5f, 0.0f) + Offset(animatedOffset, 0f), whiteSmoke)
                setVertex(0, 2, Offset(1.0f, 0.0f), forestGreen)

                // Row 1 — the wild swing zone
                setVertex(1, 0, Offset(0.0f, 0.3f) + Offset(0f, animatedOffset), lightGreen)
                setVertex(1, 1, Offset(0.4f, 0.35f) + Offset(animatedOffset * 1.5f, animatedOffset), snow)
                setVertex(1, 2, Offset(1.0f, 0.3f) + Offset(0f, -animatedOffset), paleGreen)

                // Row 2 — mirror swing, opposite phase for churn
                setVertex(2, 0, Offset(0.0f, 0.7f) + Offset(0f, -animatedOffset), ghostWhite)
                setVertex(2, 1, Offset(0.4f, 0.75f) + Offset(-animatedOffset * 1.5f, animatedOffset), forestGreen)
                setVertex(2, 2, Offset(1.0f, 0.7f) + Offset(0f, animatedOffset), lightGreen)

                // Row 3 — anchor bottom edge
                setVertex(3, 0, Offset(0.0f, 1.0f), white)
                setVertex(3, 1, Offset(0.5f, 1.0f) + Offset(-animatedOffset, 0f), darkGreen)
                setVertex(3, 2, Offset(1.0f, 1.0f), paleGreen)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(gradientPainter)
        )
    }
}
