package io.github.aldefy.contextualappbar.sample.examples

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aldefy.contextualappbar.MaterialContextualTopAppBar

private val items = (1..20).map { "Item $it" }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BasicExample(onBack: () -> Unit) {
    var selected by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            MaterialContextualTopAppBar(
                selectedCount = selected.size,
                onClearSelection = { selected = emptySet() },
                contextualNavigationIcon = {
                    IconButton(onClick = { selected = emptySet() }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                },
                contextualActions = {
                    IconButton(onClick = { selected = emptySet() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                },
                defaultBar = {
                    TopAppBar(
                        title = { Text("Basic Example") },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                    )
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(vertical = 4.dp),
        ) {
            items(items) { item ->
                val isSelected = item in selected
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                if (selected.isNotEmpty()) {
                                    selected = if (isSelected) selected - item else selected + item
                                }
                            },
                            onLongClick = { selected = selected + item },
                        )
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                            else MaterialTheme.colorScheme.surface
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (selected.isNotEmpty()) {
                        Checkbox(checked = isSelected, onCheckedChange = null)
                        Spacer(Modifier.width(12.dp))
                    }
                    Text(item, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
