---
title: Quick Start
description: Get up and running with MaterialContextualTopAppBar in minutes
---

This guide shows the most common usage pattern: a `Scaffold` with a `MaterialContextualTopAppBar` and a `LazyColumn` where list items can be selected.

## Full Working Example

```kotlin
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aldefy.contextualappbar.MaterialContextualTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen() {
    val emails = remember {
        (1..20).map { "Email #$it — Subject line goes here" }
    }
    var selectedIds by remember { mutableStateOf(emptySet<Int>()) }

    Scaffold(
        topBar = {
            MaterialContextualTopAppBar(
                selectedCount = selectedIds.size,
                onClearSelection = { selectedIds = emptySet() },
                defaultBar = {
                    TopAppBar(
                        title = { Text("Inbox") },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More")
                            }
                        }
                    )
                },
                contextualActions = {
                    IconButton(onClick = {
                        // handle delete for selectedIds
                        selectedIds = emptySet()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = {
                        // handle archive for selectedIds
                        selectedIds = emptySet()
                    }) {
                        Icon(Icons.Default.Archive, contentDescription = "Archive")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(emails.indices.toList(), key = { it }) { index ->
                val isSelected = index in selectedIds

                ListItem(
                    headlineContent = { Text(emails[index]) },
                    supportingContent = { Text("Tap to open, long-press to select") },
                    leadingContent = {
                        if (isSelected) {
                            Checkbox(checked = true, onCheckedChange = null)
                        } else {
                            Checkbox(checked = false, onCheckedChange = null)
                        }
                    },
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {
                                if (selectedIds.isNotEmpty()) {
                                    // In selection mode, tap toggles
                                    selectedIds = if (index in selectedIds) {
                                        selectedIds - index
                                    } else {
                                        selectedIds + index
                                    }
                                }
                                // else: open email
                            },
                            onLongClick = {
                                selectedIds = selectedIds + index
                            }
                        )
                )
                HorizontalDivider()
            }
        }
    }
}
```

## How Selection Works

1. The user long-presses a list item — you add it to `selectedIds`.
2. `selectedIds.size` is passed to `selectedCount`. When it becomes `> 0`, the contextual bar animates in.
3. The contextual bar shows "N selected" by default and renders your `contextualActions`.
4. When the user taps the close icon **or** presses the system back button, `onClearSelection` is called, emptying `selectedIds`.
5. `selectedCount` drops to `0` and the default bar animates back in.

## Next Steps

- [ContextualTopAppBar guide](/compose-contextual-appbar/guide/contextual-top-app-bar/) — use the raw composable for a fully custom layout
- [MaterialContextualTopAppBar guide](/compose-contextual-appbar/guide/material-contextual-top-app-bar/) — all parameters explained
- [Customization guide](/compose-contextual-appbar/guide/customization/) — custom colors, animations, disabling back press
