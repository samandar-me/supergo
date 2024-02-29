package com.sdk.supergo.ui.root

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.layout
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimator
import kotlin.math.abs

fun slideFade(
    animationSpec: FiniteAnimationSpec<Float> = tween()
): StackAnimator =
    stackAnimator(animationSpec = animationSpec) { factor, _, content ->
        content(
            Modifier
                .offsetXFactor(factor)
                .alpha(
                    getFadeAlpha(
                        factor = factor,
                        minAlpha = 0f
                    )
                )
        )
    }

private fun Modifier.offsetXFactor(factor: Float): Modifier =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = (placeable.width.toFloat() * factor).toInt(), y = 0)
        }
    }

internal fun getFadeAlpha(factor: Float, minAlpha: Float): Float =
    (1F - abs(factor) * (1F - minAlpha)).coerceIn(minimumValue = 0F, maximumValue = 1F)