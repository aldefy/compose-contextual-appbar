---
title: MaterialContextualTopAppBar
description: API reference for the MaterialContextualTopAppBar composable
---

## Signature

```kotlin
@Composable
fun MaterialContextualTopAppBar(
    selectedCount: Int,
    onClearSelection: () -> Unit,
    defaultBar: @Composable () -> Unit,
    contextualActions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    contextualNavigationIcon: @Composable () -> Unit = {},
    contextualTitle: @Composable (count: Int) -> Unit = { Text("$count selected") },
    colors: ContextualTopAppBarColors = ContextualTopAppBarDefaults.colors(),
    handleBackPress: Boolean = true,
    transitionSpec: () -> ContentTransform = { ContextualTopAppBarDefaults.defaultTransition() },
)
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `selectedCount` | `Int` | Required | Number of selected items. Drives the transition between default and contextual states. |
| `onClearSelection` | `() -> Unit` | Required | Called when the close (X) icon is tapped, or when back is pressed while `selectedCount > 0`. |
| `defaultBar` | `@Composable () -> Unit` | Required | The bar displayed when nothing is selected. Usually a standard `TopAppBar`. |
| `contextualActions` | `@Composable RowScope.() -> Unit` | Required | Action icons shown on the right side of the contextual bar. Content is placed in a `Row`. |
| `modifier` | `Modifier` | `Modifier` | Modifier applied to the root container. |
| `contextualNavigationIcon` | `@Composable () -> Unit` | `{}` | Composable placed to the left of the close icon in the contextual bar. Rarely needed. |
| `contextualTitle` | `@Composable (count: Int) -> Unit` | `Text("$count selected")` | Title displayed in the contextual bar. Receives the current count. Override for custom formatting or styling. |
| `colors` | `ContextualTopAppBarColors` | `ContextualTopAppBarDefaults.colors()` | Color configuration for both states. See [Defaults & Colors](/compose-contextual-appbar/api/contextual-top-app-bar-defaults/). |
| `handleBackPress` | `Boolean` | `true` | When `true`, intercepts the system back button to call `onClearSelection` while items are selected. |
| `transitionSpec` | `() -> ContentTransform` | `ContextualTopAppBarDefaults.defaultTransition()` | Animation between default and contextual states. |

## Contextual Bar Layout

When `selectedCount > 0`, the contextual bar is structured as:

```
[ contextualNavigationIcon ] [ X (close) ] [ contextualTitle ] [ contextualActions ]
                              ←————————— TopAppBar layout ————————————————————————→
```

- `contextualNavigationIcon` is placed in the `navigationIcon` slot (before the close icon). Defaults to empty.
- The close (X) `IconButton` is always present and calls `onClearSelection` on tap.
- `contextualTitle` occupies the `title` slot.
- `contextualActions` is placed in the `actions` slot (right side).

## Behaviour

- Delegates to `ContextualTopAppBar` internally, so all the same transition and back-press logic applies.
- The contextual bar is implemented as a standard `TopAppBar` with `colors` applied, ensuring correct elevation and padding.
- `colors.contextualContainerColor` sets the `TopAppBar`'s container color in the contextual state.
- `colors.contextualContentColor` sets the icon and text tint.

## Example

```kotlin
MaterialContextualTopAppBar(
    selectedCount = selectedIds.size,
    onClearSelection = { selectedIds = emptySet() },
    defaultBar = {
        TopAppBar(title = { Text("Inbox") })
    },
    contextualActions = {
        IconButton(onClick = { /* delete */ }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
        IconButton(onClick = { /* archive */ }) {
            Icon(Icons.Default.Archive, contentDescription = "Archive")
        }
    },
    contextualTitle = { count ->
        Text("$count selected")
    },
    colors = ContextualTopAppBarDefaults.colors(
        contextualContainerColor = MaterialTheme.colorScheme.primaryContainer,
        contextualContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        contextualNavigationIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        contextualActionIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
    )
)
```
