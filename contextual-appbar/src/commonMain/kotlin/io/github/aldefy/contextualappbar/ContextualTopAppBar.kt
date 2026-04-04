package io.github.aldefy.contextualappbar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContextualTopAppBar(
    selectedCount: Int,
    onClearSelection: () -> Unit,
    contextualContent: @Composable (count: Int) -> Unit,
    defaultContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    handleBackPress: Boolean = true,
    transitionSpec: () -> ContentTransform = { ContextualAnimationSpec.defaultTransition() },
) {
    val isActive = selectedCount > 0

    if (handleBackPress) {
        PlatformBackHandler(enabled = isActive) {
            onClearSelection()
        }
    }

    AnimatedContent(
        targetState = isActive,
        modifier = modifier,
        transitionSpec = { transitionSpec() },
        label = "ContextualTopAppBar",
    ) { showContextual ->
        if (showContextual) {
            contextualContent(selectedCount)
        } else {
            defaultContent()
        }
    }
}
