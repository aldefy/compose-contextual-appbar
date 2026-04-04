---
title: ContextualTopAppBar
description: API reference for the ContextualTopAppBar composable
---

## Signature

```kotlin
@Composable
fun ContextualTopAppBar(
    selectedCount: Int,
    onClearSelection: () -> Unit,
    contextualContent: @Composable (count: Int) -> Unit,
    defaultContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    handleBackPress: Boolean = true,
    transitionSpec: () -> ContentTransform = { ContextualAnimationSpec.defaultTransition() },
)
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `selectedCount` | `Int` | Required | Number of selected items. When `> 0`, `contextualContent` is displayed; when `0`, `defaultContent` is displayed. |
| `onClearSelection` | `() -> Unit` | Required | Called when the user presses back while `selectedCount > 0` (if `handleBackPress = true`). You must also wire this to your close/cancel button inside `contextualContent`. |
| `contextualContent` | `@Composable (count: Int) -> Unit` | Required | Composable displayed when items are selected. Receives the current `selectedCount` as `count`. |
| `defaultContent` | `@Composable () -> Unit` | Required | Composable displayed when `selectedCount == 0`. Typically your normal `TopAppBar`. |
| `modifier` | `Modifier` | `Modifier` | Modifier applied to the root `Box` wrapping both states. |
| `handleBackPress` | `Boolean` | `true` | When `true`, intercepts the system back button while `selectedCount > 0` to call `onClearSelection`. Set to `false` to manage back presses yourself. |
| `transitionSpec` | `() -> ContentTransform` | `ContextualAnimationSpec.defaultTransition()` | The `ContentTransform` used when switching between `defaultContent` and `contextualContent`. Defaults to a crossfade. |

## Behaviour

- Uses `AnimatedContent` internally, keyed on whether `selectedCount > 0`.
- The `transitionSpec` lambda is called once per transition to compute the enter/exit animation pair.
- When `handleBackPress = true`, a `BackHandler` is installed that is only active while `selectedCount > 0`.
- No Material dependency — you can use any content in `defaultContent` and `contextualContent`.

## Example

```kotlin
ContextualTopAppBar(
    selectedCount = selectedIds.size,
    onClearSelection = { selectedIds = emptySet() },
    defaultContent = {
        TopAppBar(title = { Text("My Screen") })
    },
    contextualContent = { count ->
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { selectedIds = emptySet() }) {
                Icon(Icons.Default.Close, contentDescription = "Clear")
            }
            Text("$count selected", modifier = Modifier.weight(1f))
            IconButton(onClick = { /* action */ }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
)
```
