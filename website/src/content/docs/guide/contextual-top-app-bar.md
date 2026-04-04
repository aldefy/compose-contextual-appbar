---
title: ContextualTopAppBar
description: The raw, fully customizable contextual top app bar composable
---

`ContextualTopAppBar` is the low-level primitive. It handles the selection state transition and back-press logic, but delegates **all layout and visual design** to you via two composable slots: `defaultContent` and `contextualContent`.

## When to Use It

Use `ContextualTopAppBar` when:

- You are not using Material 3, or you have a custom design system
- You need a completely custom contextual bar layout (e.g., a search bar, a custom title component, a different color scheme per screen)
- You need fine-grained control over the transition animation

Use [`MaterialContextualTopAppBar`](/compose-contextual-appbar/guide/material-contextual-top-app-bar/) when you want Material 3 out of the box.

## Example

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aldefy.contextualappbar.ContextualTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosScreen() {
    var selectedIds by remember { mutableStateOf(emptySet<Int>()) }

    Scaffold(
        topBar = {
            ContextualTopAppBar(
                selectedCount = selectedIds.size,
                onClearSelection = { selectedIds = emptySet() },
                defaultContent = {
                    // Your default bar — any composable you like
                    TopAppBar(
                        title = { Text("Photos") }
                    )
                },
                contextualContent = { count ->
                    // Your contextual bar — full control over layout
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        tonalElevation = 3.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = { selectedIds = emptySet() }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear selection")
                            }
                            Text(
                                text = "$count selected",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f),
                            )
                            IconButton(onClick = { /* share */ }) {
                                Icon(Icons.Default.Share, contentDescription = "Share")
                            }
                            IconButton(onClick = { /* delete */ }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        // Photo grid content
    }
}
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `selectedCount` | `Int` | Required | Number of selected items. When `> 0`, the contextual bar is shown. |
| `onClearSelection` | `() -> Unit` | Required | Called when the user taps the close icon or presses back. |
| `contextualContent` | `@Composable (count: Int) -> Unit` | Required | Composable shown when items are selected. Receives the current count. |
| `defaultContent` | `@Composable () -> Unit` | Required | Composable shown when nothing is selected. |
| `modifier` | `Modifier` | `Modifier` | Applied to the outer container. |
| `handleBackPress` | `Boolean` | `true` | When `true`, intercepts the system back button to call `onClearSelection` while items are selected. |
| `transitionSpec` | `() -> ContentTransform` | `ContextualAnimationSpec.defaultTransition()` | Customize the animation between default and contextual states. |

## Back Press Behaviour

By default, `handleBackPress = true`. This means:

- While `selectedCount > 0`, pressing the system back button calls `onClearSelection` instead of navigating away.
- Once `selectedCount` is `0`, back presses are handled normally by the navigation system.

Set `handleBackPress = false` if you manage back press yourself (e.g., you're using a custom navigation library that intercepts back presses at a higher level).

## Custom Transition

```kotlin
import androidx.compose.animation.*
import io.github.aldefy.contextualappbar.ContextualTopAppBar

ContextualTopAppBar(
    selectedCount = selectedIds.size,
    onClearSelection = { selectedIds = emptySet() },
    transitionSpec = {
        // Slide up when entering, slide down when exiting
        (slideInVertically { -it } + fadeIn()) togetherWith
        (slideOutVertically { -it } + fadeOut())
    },
    defaultContent = { /* ... */ },
    contextualContent = { count -> /* ... */ }
)
```
