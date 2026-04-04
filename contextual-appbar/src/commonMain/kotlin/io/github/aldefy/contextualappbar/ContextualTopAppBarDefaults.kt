package io.github.aldefy.contextualappbar

import androidx.compose.animation.ContentTransform
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ContextualTopAppBarDefaults {

    @Composable
    fun colors(
        contextualContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        contextualContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        contextualNavigationIconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        contextualActionIconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    ): ContextualTopAppBarColors = ContextualTopAppBarColors(
        contextualContainerColor = contextualContainerColor,
        contextualContentColor = contextualContentColor,
        contextualNavigationIconColor = contextualNavigationIconColor,
        contextualActionIconColor = contextualActionIconColor,
    )

    fun defaultTransition(): ContentTransform = ContextualAnimationSpec.defaultTransition()
}
