package io.github.aldefy.contextualappbar

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

object ContextualAnimationSpec {
    val DefaultDurationMillis: Int = 250

    fun defaultTransition(): ContentTransform =
        fadeIn(animationSpec = tween(DefaultDurationMillis)) togetherWith
                fadeOut(animationSpec = tween(DefaultDurationMillis))
}
