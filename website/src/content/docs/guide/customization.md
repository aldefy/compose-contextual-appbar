---
title: Customization
description: Custom colors, animations, and back-press handling
---

## Custom Colors

`MaterialContextualTopAppBar` accepts a `ContextualTopAppBarColors` object that controls the background and content (icon/text) colors for both states.

Use `ContextualTopAppBarDefaults.colors()` to start from the Material 3 defaults and override specific values:

```kotlin
import io.github.aldefy.contextualappbar.ContextualTopAppBarDefaults
import io.github.aldefy.contextualappbar.MaterialContextualTopAppBar

MaterialContextualTopAppBar(
    selectedCount = selectedCount,
    onClearSelection = onClearSelection,
    defaultBar = { /* ... */ },
    contextualActions = { /* ... */ },
    colors = ContextualTopAppBarDefaults.colors(
        // Contextual bar gets a surface-variant background
        contextualContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        contextualContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        contextualNavigationIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        contextualActionIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    )
)
```

### All Color Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| `contextualContainerColor` | `MaterialTheme.colorScheme.primaryContainer` | Background of the contextual bar |
| `contextualContentColor` | `MaterialTheme.colorScheme.onPrimaryContainer` | Default text color in the contextual bar |
| `contextualNavigationIconColor` | `MaterialTheme.colorScheme.onPrimaryContainer` | Tint for any navigation icon in the contextual bar |
| `contextualActionIconColor` | `MaterialTheme.colorScheme.onPrimaryContainer` | Tint for action icons in the contextual bar |

## Custom Animations

Both `ContextualTopAppBar` and `MaterialContextualTopAppBar` accept a `transitionSpec` parameter that returns a `ContentTransform`.

The default is a crossfade via `ContextualAnimationSpec.defaultTransition()` (or `ContextualTopAppBarDefaults.defaultTransition()` on the Material variant).

### Slide Transition

```kotlin
import androidx.compose.animation.*
import io.github.aldefy.contextualappbar.MaterialContextualTopAppBar

MaterialContextualTopAppBar(
    selectedCount = selectedCount,
    onClearSelection = onClearSelection,
    defaultBar = { /* ... */ },
    contextualActions = { /* ... */ },
    transitionSpec = {
        // Contextual bar slides down from the top
        (slideInVertically { -it } + fadeIn()) togetherWith
        (slideOutVertically { it } + fadeOut())
    }
)
```

### Slow Crossfade

```kotlin
import androidx.compose.animation.core.tween

transitionSpec = {
    fadeIn(animationSpec = tween(600)) togetherWith
    fadeOut(animationSpec = tween(600))
}
```

### No Animation

```kotlin
transitionSpec = {
    EnterTransition.None togetherWith ExitTransition.None
}
```

## Disabling Back Press Handling

By default, `handleBackPress = true` intercepts the system back button when items are selected. To disable this:

```kotlin
MaterialContextualTopAppBar(
    selectedCount = selectedCount,
    onClearSelection = onClearSelection,
    handleBackPress = false, // You handle back press yourself
    defaultBar = { /* ... */ },
    contextualActions = { /* ... */ }
)
```

You might want to do this if:

- Your navigation library (e.g., Voyager, Decompose) intercepts back presses globally, and you need to handle the "deselect on back" logic yourself in a `BackHandler` elsewhere.
- You want to show a confirmation dialog before clearing the selection on back press.

### Managing Back Press Yourself

```kotlin
val selectedIds by remember { mutableStateOf(emptySet<Int>()) }

// Your own BackHandler — placed before or inside the Scaffold
BackHandler(enabled = selectedIds.isNotEmpty()) {
    // Custom logic — show dialog, analytics, etc.
    selectedIds = emptySet()
}

MaterialContextualTopAppBar(
    selectedCount = selectedIds.size,
    onClearSelection = { selectedIds = emptySet() },
    handleBackPress = false,
    // ...
)
```

## Custom Selection Title

Override `contextualTitle` to change the "N selected" format:

```kotlin
MaterialContextualTopAppBar(
    selectedCount = selectedCount,
    onClearSelection = onClearSelection,
    defaultBar = { /* ... */ },
    contextualActions = { /* ... */ },
    contextualTitle = { count ->
        Text(
            text = pluralStringResource(R.plurals.items_selected, count, count),
            style = MaterialTheme.typography.titleMedium,
        )
    }
)
```
