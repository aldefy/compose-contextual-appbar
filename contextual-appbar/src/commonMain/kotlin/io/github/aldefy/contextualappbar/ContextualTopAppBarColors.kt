package io.github.aldefy.contextualappbar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ContextualTopAppBarColors(
    val contextualContainerColor: Color,
    val contextualContentColor: Color,
    val contextualNavigationIconColor: Color,
    val contextualActionIconColor: Color,
)
