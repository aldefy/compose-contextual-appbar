---
title: Defaults & Colors
description: API reference for ContextualTopAppBarDefaults, ContextualTopAppBarColors, and ContextualAnimationSpec
---

## ContextualTopAppBarDefaults

```kotlin
object ContextualTopAppBarDefaults {

    @Composable
    fun colors(
        contextualContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        contextualContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        contextualNavigationIconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        contextualActionIconColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    ): ContextualTopAppBarColors

    fun defaultTransition(): ContentTransform
}
```

### `colors()`

Returns a `ContextualTopAppBarColors` configured from Material 3 color scheme tokens. All parameters are optional — supply only the ones you want to override.

| Parameter | Default | Description |
|-----------|---------|-------------|
| `contextualContainerColor` | `primaryContainer` | Background color of the contextual bar. |
| `contextualContentColor` | `onPrimaryContainer` | Default content (text) color in the contextual bar. |
| `contextualNavigationIconColor` | `onPrimaryContainer` | Color of any navigation icon composable in the contextual bar. |
| `contextualActionIconColor` | `onPrimaryContainer` | Color tint applied to action icons in the contextual bar. |

**Note:** There are no `default*` color parameters because the default bar is a separate composable (`defaultBar`) that manages its own colors.

### `defaultTransition()`

Returns the default `ContentTransform` used for the bar transition: a 250ms crossfade (fade in + fade out simultaneously).

```kotlin
// Equivalent to:
fadeIn(animationSpec = tween(250)) togetherWith fadeOut(animationSpec = tween(250))
```

---

## ContextualTopAppBarColors

```kotlin
@Immutable
data class ContextualTopAppBarColors(
    val contextualContainerColor: Color,
    val contextualContentColor: Color,
    val contextualNavigationIconColor: Color,
    val contextualActionIconColor: Color,
)
```

This is a plain data class. You can construct it directly, but prefer using `ContextualTopAppBarDefaults.colors()` to inherit Material 3 defaults for any values you don't specify.

### Fields

| Field | Description |
|-------|-------------|
| `contextualContainerColor` | Background of the contextual bar surface. |
| `contextualContentColor` | Default text/icon color inside the contextual bar. |
| `contextualNavigationIconColor` | Tint for the navigation icon slot (applied when `contextualNavigationIcon` is provided to `MaterialContextualTopAppBar`). |
| `contextualActionIconColor` | Tint for the icons in the `contextualActions` slot. |

---

## ContextualAnimationSpec

```kotlin
object ContextualAnimationSpec {
    val DefaultDurationMillis: Int = 250

    fun defaultTransition(): ContentTransform
}
```

A utility object used internally by `ContextualTopAppBar`. Also accessible directly if you want to reference the default duration constant.

### `DefaultDurationMillis`

`250` — the duration in milliseconds for the default crossfade transition.

### `defaultTransition()`

```kotlin
fun defaultTransition(): ContentTransform =
    fadeIn(animationSpec = tween(DefaultDurationMillis)) togetherWith
    fadeOut(animationSpec = tween(DefaultDurationMillis))
```

Returns a simple 250ms crossfade. `ContextualTopAppBarDefaults.defaultTransition()` delegates to this.

---

## Usage Example

```kotlin
import io.github.aldefy.contextualappbar.ContextualTopAppBarDefaults
import io.github.aldefy.contextualappbar.ContextualAnimationSpec

// Colors with a custom container
val customColors = ContextualTopAppBarDefaults.colors(
    contextualContainerColor = MaterialTheme.colorScheme.errorContainer,
    contextualContentColor = MaterialTheme.colorScheme.onErrorContainer,
    contextualNavigationIconColor = MaterialTheme.colorScheme.onErrorContainer,
    contextualActionIconColor = MaterialTheme.colorScheme.onErrorContainer,
)

// Reference the duration constant for a custom animation
val myTransition = fadeIn(tween(ContextualAnimationSpec.DefaultDurationMillis * 2)) togetherWith
                   fadeOut(tween(ContextualAnimationSpec.DefaultDurationMillis * 2))

MaterialContextualTopAppBar(
    selectedCount = selectedCount,
    onClearSelection = onClearSelection,
    defaultBar = { /* ... */ },
    contextualActions = { /* ... */ },
    colors = customColors,
    transitionSpec = { myTransition },
)
```
