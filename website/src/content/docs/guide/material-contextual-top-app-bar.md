---
title: MaterialContextualTopAppBar
description: Material 3 implementation of the contextual top app bar
---

`MaterialContextualTopAppBar` builds on `ContextualTopAppBar` and provides a complete Material 3 contextual bar out of the box. You supply your default bar and the contextual action buttons — it handles the animated container, title, close icon, and color scheme.

## When to Use It

Use `MaterialContextualTopAppBar` when:

- Your app uses Material 3 (most Compose apps do)
- You want the standard contextual bar look: close icon on the left, "N selected" title, action icons on the right
- You don't need a fully custom layout

## Gmail-Style Inbox Example

```kotlin
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import io.github.aldefy.contextualappbar.MaterialContextualTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GmailScreen() {
    val emails = remember { (1..30).map { "Email subject #$it" } }
    var selectedIds by remember { mutableStateOf(emptySet<Int>()) }

    Scaffold(
        topBar = {
            MaterialContextualTopAppBar(
                selectedCount = selectedIds.size,
                onClearSelection = { selectedIds = emptySet() },
                defaultBar = {
                    TopAppBar(
                        title = { Text("Inbox") },
                        navigationIcon = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        }
                    )
                },
                contextualActions = {
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.Archive, contentDescription = "Archive")
                    }
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.MarkEmailRead, contentDescription = "Mark read")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(emails.indices.toList(), key = { it }) { index ->
                ListItem(
                    headlineContent = { Text(emails[index]) },
                    modifier = Modifier.combinedClickable(
                        onClick = {
                            if (selectedIds.isNotEmpty()) {
                                selectedIds = selectedIds.toggle(index)
                            }
                        },
                        onLongClick = { selectedIds = selectedIds + index }
                    )
                )
                HorizontalDivider()
            }
        }
    }
}

private fun Set<Int>.toggle(value: Int) =
    if (contains(value)) this - value else this + value
```

## Parameters Explained

### Required Parameters

**`selectedCount: Int`**
The number of currently selected items. Pass `selectedIds.size`. When this is `> 0`, the contextual bar slides in; when it returns to `0`, the default bar comes back.

**`onClearSelection: () -> Unit`**
Called when the user taps the close (X) icon in the contextual bar, or when back is pressed while items are selected. Your lambda should clear your selection state (e.g., `selectedIds = emptySet()`).

**`defaultBar: @Composable () -> Unit`**
The bar shown when nothing is selected. Typically a standard `TopAppBar` with your screen title and navigation icon.

**`contextualActions: @Composable RowScope.() -> Unit`**
The action icons shown on the right side of the contextual bar. These are rendered inside a `Row`, so you can place any number of `IconButton` composables here.

### Optional Parameters

**`contextualNavigationIcon: @Composable () -> Unit`**
A composable shown to the left of the title in the contextual bar, before the close button. Defaults to an empty composable (nothing extra shown).

**`contextualTitle: @Composable (count: Int) -> Unit`**
The title shown in the contextual bar. Defaults to `Text("$count selected")`. Override to provide a different format or style.

```kotlin
contextualTitle = { count ->
    Text(
        text = if (count == 1) "1 item selected" else "$count items selected",
        style = MaterialTheme.typography.titleMedium,
    )
}
```

**`colors: ContextualTopAppBarColors`**
Controls the background and content colors for both the default and contextual states. See [Defaults & Colors](/compose-contextual-appbar/api/contextual-top-app-bar-defaults/) for the full color API. Defaults to `ContextualTopAppBarDefaults.colors()`.

**`handleBackPress: Boolean`**
Whether to intercept the system back button. Defaults to `true`. Set to `false` to manage back presses yourself.

**`transitionSpec: () -> ContentTransform`**
The animation used when switching between default and contextual states. Defaults to `ContextualTopAppBarDefaults.defaultTransition()` (a crossfade). Override for a custom animation.

**`modifier: Modifier`**
Applied to the root container of the bar.
