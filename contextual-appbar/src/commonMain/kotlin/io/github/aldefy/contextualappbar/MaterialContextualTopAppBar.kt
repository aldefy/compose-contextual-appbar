package io.github.aldefy.contextualappbar

import androidx.compose.animation.ContentTransform
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialContextualTopAppBar(
    selectedCount: Int,
    onClearSelection: () -> Unit,
    defaultBar: @Composable () -> Unit,
    contextualActions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    contextualNavigationIcon: @Composable () -> Unit = {},
    contextualTitle: @Composable (count: Int) -> Unit = { count ->
        Text("$count selected")
    },
    colors: ContextualTopAppBarColors = ContextualTopAppBarDefaults.colors(),
    handleBackPress: Boolean = true,
    transitionSpec: () -> ContentTransform = { ContextualTopAppBarDefaults.defaultTransition() },
) {
    ContextualTopAppBar(
        selectedCount = selectedCount,
        onClearSelection = onClearSelection,
        modifier = modifier,
        handleBackPress = handleBackPress,
        transitionSpec = transitionSpec,
        defaultContent = defaultBar,
        contextualContent = { count ->
            TopAppBar(
                title = { contextualTitle(count) },
                navigationIcon = contextualNavigationIcon,
                actions = contextualActions,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.contextualContainerColor,
                    titleContentColor = colors.contextualContentColor,
                    navigationIconContentColor = colors.contextualNavigationIconColor,
                    actionIconContentColor = colors.contextualActionIconColor,
                ),
            )
        },
    )
}
