package io.github.aldefy.contextualappbar.sample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.aldefy.contextualappbar.sample.examples.BasicExample
import io.github.aldefy.contextualappbar.sample.examples.GmailExample
import io.github.aldefy.contextualappbar.sample.examples.PhotosGridExample

private enum class Screen {
    Catalog,
    Basic,
    GmailInbox,
    PhotosGrid,
}

private data class ExampleItem(
    val title: String,
    val description: String,
    val screen: Screen,
)

private val examples = listOf(
    ExampleItem(
        title = "Gmail Inbox",
        description = "Polished Gmail-style inbox with multi-select, contextual actions, and animated avatars.",
        screen = Screen.GmailInbox,
    ),
    ExampleItem(
        title = "Basic List",
        description = "Minimal example showing the API in action with a simple selectable list.",
        screen = Screen.Basic,
    ),
    ExampleItem(
        title = "Photos Grid",
        description = "Google Photos-style grid with long-press selection and overlay checkmarks.",
        screen = Screen.PhotosGrid,
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleApp() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.Catalog) }

        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = {
                if (targetState == Screen.Catalog) {
                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                        (slideOutHorizontally { it } + fadeOut())
                } else {
                    (slideInHorizontally { it } + fadeIn()) togetherWith
                        (slideOutHorizontally { -it } + fadeOut())
                }
            },
            label = "ScreenNav",
        ) { screen ->
            when (screen) {
                Screen.Catalog -> CatalogScreen(
                    onExampleClick = { currentScreen = it },
                )
                Screen.Basic -> BasicExample(
                    onBack = { currentScreen = Screen.Catalog },
                )
                Screen.GmailInbox -> GmailExample(
                    onBack = { currentScreen = Screen.Catalog },
                )
                Screen.PhotosGrid -> PhotosGridExample(
                    onBack = { currentScreen = Screen.Catalog },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogScreen(onExampleClick: (Screen) -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("ContextualAppBar Samples")
                },
            )
        },
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(examples) { example ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onExampleClick(example.screen) },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = example.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = example.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}
