/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.feature.new_transaction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.toPath
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import kotlinx.coroutines.launch
import org.queryquill.app.core.utils.vibration
import kotlin.math.max
import kotlin.math.min

@Composable
internal fun AnimatedLogo(imageVector: Painter) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Box(
        modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center
    ) {
        ClickToMorphShapes(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    rotationZ = rotation
                }, color = MaterialTheme.colorScheme.surfaceContainer
        )
        Icon(
            painter = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceTint,
            modifier = Modifier.size(180.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ClickToMorphShapes(
    modifier: Modifier = Modifier,
    animationDuration: Int = 500,
    polygons: List<RoundedPolygon> = listOf(
        MaterialShapes.Sunny,
        MaterialShapes.VerySunny,
        MaterialShapes.Cookie7Sided,
        MaterialShapes.Cookie9Sided,
        MaterialShapes.Cookie12Sided
    ),
    color: Color = Color.Black,
) {
    require(polygons.size > 1) { "Need at least two RoundedPolygon shapes" }
    var currentMorphIndex by remember { mutableIntStateOf(0) }
    val progress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val morphSeq = remember(polygons) { buildMorphSequence(polygons) }
    val shapeScale by remember(polygons) {
        mutableFloatStateOf(calculateScaleFactor(polygons))
    }
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    Box(modifier
        .clickable(interactionSource = interactionSource, indication = null) {
            vibration(context)
            if (progress.isRunning) return@clickable
            scope.launch {
                progress.animateTo(
                    targetValue = 1f, animationSpec = tween(animationDuration)
                )
                currentMorphIndex = (currentMorphIndex + 1) % morphSeq.size
                progress.snapTo(0f)
            }
        }
        .fillMaxSize()
        .drawWithContent {
            val rawPath = morphSeq[currentMorphIndex].toPath(
                progress = progress.value, path = Path(), startAngle = 0
            )
            val finalPath = processPath(
                rawPath, size = size, scaleFactor = shapeScale, scaleMatrix = Matrix()
            )
            drawPath(finalPath, color, style = Fill)
        }) {

    }
}

private fun buildMorphSequence(polygons: List<RoundedPolygon>): List<Morph> =
    List(polygons.size) { i ->
        val next = (i + 1) % polygons.size
        Morph(polygons[i].normalized(), polygons[next].normalized())
    }

private fun calculateScaleFactor(polys: List<RoundedPolygon>): Float {
    var scale = 1f
    val bounds = FloatArray(4)
    val maxBounds = FloatArray(4)

    polys.forEach { p ->
        p.calculateBounds(bounds)
        p.calculateMaxBounds(maxBounds)
        val sx = bounds.width() / maxBounds.width()
        val sy = bounds.height() / maxBounds.height()
        scale = min(scale, max(sx, sy))
    }
    return scale
}

private fun FloatArray.width(): Float = this[2] - this[0]

private fun FloatArray.height(): Float = this[3] - this[1]

private fun processPath(
    path: Path, size: Size, scaleFactor: Float, scaleMatrix: Matrix
): Path {
    scaleMatrix.reset()
    scaleMatrix.scale(size.width * scaleFactor, size.height * scaleFactor)
    path.transform(scaleMatrix)
    path.translate(size.center - path.getBounds().center)
    return path
}

@Preview
@Composable
private fun PreviewAnimatedLogo() {
    val imageVector = painterResource(id = R.drawable.logo_add_request)
    AnimatedLogo(imageVector)
}
