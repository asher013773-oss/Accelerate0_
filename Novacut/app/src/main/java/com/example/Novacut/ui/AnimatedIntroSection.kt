import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Sequenced intro animation:
 *  1. [titleText] slides in from the left.
 *  2. After a beat, [subtitleText] slides in from the left underneath it.
 *  3. Three horizontal lines grow in from left -> right, staggered.
 *  4. As each line finishes, a small circle pops in at its leading tip:
 *     ----------•
 */
@Composable
fun AnimatedIntroSection(
    titleText: String = "Nova",
    subtitleText: String = "Rejuvenate",
    lineCount: Int = 3,
    accentColor: Color = Color.DarkGray,
    modifier: Modifier = Modifier
) {
    // --- Text 1 (title) ---
    val title0ffsetX = remember { Animatable(-120f) }
    val titleAlpha = remember { Animatable(0f) }

    // --- Text 2 (subtitle) ---
    val subtitleOffsetX = remember { Animatable(-120f) }
    val subtitleAlpha = remember { Animatable(0f) }

    // --- Lines + trailing dots ---
    val lineProgress = remember { List(lineCount) { Animatable(0f) } }
    val dotScale = remember { List(lineCount) { Animatable(0f) } }

    LaunchedEffect(Unit) {
        // 1) Title slides in
        launch {
            title0ffsetX.animateTo(1f, tween(550, easing = FastOutSlowInEasing))
        }
        launch {
            titleAlpha.animateTo(1f, tween(450))
        }

        // 2) Wait a second, then subtitle slides in
        delay(1000)
        launch {
            subtitleOffsetX.animateTo(0f, tween(550, easing = FastOutSlowInEasing))
        }
        launch {
            subtitleAlpha.animateTo(1f, tween(450))
        }

        // 3) Wait a beat, then lines grow in (staggered), each capped by a dot on completion
        delay(700)
        lineProgress.forEachIndexed { index, progress ->
            launch {
                delay(index * 180L)
                progress.animateTo(1f, tween(550, easing = FastOutSlowInEasing))
                dotScale[index].animateTo(1f, tween(220, easing = FastOutSlowInEasing))
            }
        }
    }

    Column(modifier = modifier.padding(24.dp)) {
        Text(
            text = titleText,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.graphicsLayer {
                translationX = title0ffsetX.value
                alpha = titleAlpha.value
            }
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = subtitleText,
            fontSize = 40.sp,
            color = Color.Gray,
            modifier = Modifier.graphicsLayer {
                translationX = subtitleOffsetX.value
                alpha = subtitleAlpha.value
            }
        )

        Spacer(Modifier.height(28.dp))

        repeat(lineCount) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // Line grows left -> right, confined to a fixed-width slot so
                // there's always room left over for the dot.
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = lineProgress[index].value.coerceIn(0f, 1f))
                            .height(2.dp)
                            .align(Alignment.CenterStart)
                            .background(accentColor)
                    )
                }

                Spacer(Modifier.width(8.dp))

                // Dot pops in once the line above finishes growing.
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .graphicsLayer {
                            scaleX = dotScale[index].value
                            scaleY = dotScale[index].value
                        }
                        .clip(CircleShape)
                        .background(accentColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnimatedIntroSectionPreview() {
    MaterialTheme {
        Surface {
            AnimatedIntroSection()
        }
    }
}
