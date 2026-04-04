package io.github.aldefy.contextualappbar.sample.examples

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.aldefy.contextualappbar.MaterialContextualTopAppBar

private data class PhotoItem(
    val id: Int,
    val color: Color,
)

private val photoColors = listOf(
    Color(0xFFEF5350), Color(0xFFAB47BC), Color(0xFF5C6BC0),
    Color(0xFF29B6F6), Color(0xFF26A69A), Color(0xFF66BB6A),
    Color(0xFFD4E157), Color(0xFFFFCA28), Color(0xFFFF7043),
    Color(0xFF8D6E63), Color(0xFF78909C), Color(0xFFEC407A),
    Color(0xFF7E57C2), Color(0xFF42A5F5), Color(0xFF26C6DA),
    Color(0xFF9CCC65), Color(0xFFFFA726), Color(0xFF5C6BC0),
    Color(0xFFEF5350), Color(0xFF66BB6A), Color(0xFFFFEE58),
    Color(0xFFFF7043), Color(0xFF8D6E63), Color(0xFFBDBDBD),
    Color(0xFF29B6F6), Color(0xFFAB47BC), Color(0xFFD4E157),
    Color(0xFF26A69A), Color(0xFFEC407A), Color(0xFF42A5F5),
)

private val photos = photoColors.mapIndexed { index, color ->
    PhotoItem(id = index, color = color)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PhotosGridExample(onBack: () -> Unit) {
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    val selectionMode = selectedIds.isNotEmpty()

    Scaffold(
        topBar = {
            MaterialContextualTopAppBar(
                selectedCount = selectedIds.size,
                onClearSelection = { selectedIds = emptySet() },
                contextualNavigationIcon = {
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                },
                contextualActions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.AddToPhotos, contentDescription = "Add to album")
                    }
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                },
                defaultBar = {
                    TopAppBar(
                        title = { Text("Photos") },
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(photos, key = { it.id }) { photo ->
                val isSelected = photo.id in selectedIds

                val borderColor by animateColorAsState(
                    targetValue = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    animationSpec = tween(200),
                    label = "border",
                )

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(photo.color)
                        .border(
                            width = if (isSelected) 3.dp else 0.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(4.dp),
                        )
                        .combinedClickable(
                            onClick = {
                                if (selectionMode) {
                                    selectedIds = if (isSelected) {
                                        selectedIds - photo.id
                                    } else {
                                        selectedIds + photo.id
                                    }
                                }
                            },
                            onLongClick = {
                                selectedIds = selectedIds + photo.id
                            },
                        ),
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .padding(6.dp)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .align(Alignment.TopStart),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
