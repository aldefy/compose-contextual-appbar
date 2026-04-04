package io.github.aldefy.contextualappbar.sample.examples

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.MarkEmailRead
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.aldefy.contextualappbar.MaterialContextualTopAppBar

private data class Email(
    val id: Int,
    val sender: String,
    val subject: String,
    val preview: String,
    val time: String,
    val avatarColor: Color,
)

private val sampleEmails = listOf(
    Email(
        id = 1,
        sender = "GitHub",
        subject = "Your pull request was merged",
        preview = "Congratulations! PR #142 \"Add contextual appbar component\" has been merged into main by aldefy.",
        time = "10:42 AM",
        avatarColor = Color(0xFF4CAF50),
    ),
    Email(
        id = 2,
        sender = "Priya Sharma",
        subject = "Trip planning for Bali",
        preview = "Hey! I found some amazing deals on flights to Bali for March. Let me know if you want to split the Airbnb. The villa near Ubud looks incredible.",
        time = "9:15 AM",
        avatarColor = Color(0xFFE91E63),
    ),
    Email(
        id = 3,
        sender = "Figma",
        subject = "New comment on your design",
        preview = "Raina left a comment on \"App Bar Variants\": \"Love the transition animation! Can we try a slightly warmer purple?\"",
        time = "8:30 AM",
        avatarColor = Color(0xFF9C27B0),
    ),
    Email(
        id = 4,
        sender = "Arjun Mehta",
        subject = "Re: Weekend trek to Coorg",
        preview = "Count me in! I can drive. We should leave by 5 AM to beat the traffic on Mysore Road. Bringing my DSLR this time.",
        time = "Yesterday",
        avatarColor = Color(0xFF2196F3),
    ),
    Email(
        id = 5,
        sender = "Google Cloud",
        subject = "Your billing statement is ready",
        preview = "Your Google Cloud billing statement for February 2026 is now available. Total charges: \$12.47. View your statement in the Cloud Console.",
        time = "Yesterday",
        avatarColor = Color(0xFF4285F4),
    ),
    Email(
        id = 6,
        sender = "Kavitha R.",
        subject = "Shared a document with you",
        preview = "Kavitha R. shared \"Q1 2026 OKRs - Engineering\" with you. Click to open in Google Docs. You have edit access.",
        time = "Yesterday",
        avatarColor = Color(0xFFFF9800),
    ),
    Email(
        id = 7,
        sender = "Slack",
        subject = "3 new messages in #android-dev",
        preview = "Rahul: Has anyone tried the new Compose 1.7 stable? The shared element transitions are smooth. Ananya: Yes! We shipped it last week...",
        time = "Mon",
        avatarColor = Color(0xFF611F69),
    ),
    Email(
        id = 8,
        sender = "Amazon",
        subject = "Your order has shipped!",
        preview = "Your order #402-8834521 containing \"Kotlin in Action, 2nd Edition\" has been shipped. Expected delivery: Thursday, March 6.",
        time = "Mon",
        avatarColor = Color(0xFFFF9900),
    ),
    Email(
        id = 9,
        sender = "Rohan Kulkarni",
        subject = "KMP library feedback",
        preview = "Tried your contextual appbar lib on our project. Works great! One suggestion: maybe add a convenience overload that takes a list of MenuItem data classes?",
        time = "Sun",
        avatarColor = Color(0xFF009688),
    ),
    Email(
        id = 10,
        sender = "JetBrains",
        subject = "IntelliJ IDEA 2026.1 EAP is here",
        preview = "Try the latest EAP build with improved Kotlin K2 support, faster indexing, and new AI-powered code completion. Download now.",
        time = "Sun",
        avatarColor = Color(0xFF000000),
    ),
    Email(
        id = 11,
        sender = "Swiggy",
        subject = "Your order is on the way!",
        preview = "Your order from Third Wave Coffee Koramangala is being delivered. Estimated arrival: 15 minutes. Track your order in the app.",
        time = "Sat",
        avatarColor = Color(0xFFFC8019),
    ),
    Email(
        id = 12,
        sender = "Nisha Patel",
        subject = "Conference talk accepted!",
        preview = "Great news! Your talk \"Building Contextual UIs with Compose Multiplatform\" has been accepted for Droidcon India 2026. Congratulations!",
        time = "Sat",
        avatarColor = Color(0xFFD32F2F),
    ),
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GmailExample(onBack: () -> Unit) {
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }
    val selectionMode = selectedIds.isNotEmpty()

    Scaffold(
        topBar = {
            MaterialContextualTopAppBar(
                selectedCount = selectedIds.size,
                onClearSelection = { selectedIds = emptySet() },
                contextualNavigationIcon = {
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear selection")
                    }
                },
                contextualActions = {
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.Archive, contentDescription = "Archive")
                    }
                    IconButton(onClick = { selectedIds = emptySet() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.MarkEmailRead, contentDescription = "Mark as read")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                defaultBar = {
                    TopAppBar(
                        title = { Text("Inbox") },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        },
                    )
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(vertical = 4.dp),
        ) {
            items(sampleEmails, key = { it.id }) { email ->
                val isSelected = email.id in selectedIds

                EmailRow(
                    email = email,
                    isSelected = isSelected,
                    selectionMode = selectionMode,
                    onLongClick = {
                        selectedIds = selectedIds + email.id
                    },
                    onClick = {
                        if (selectionMode) {
                            selectedIds = if (isSelected) {
                                selectedIds - email.id
                            } else {
                                selectedIds + email.id
                            }
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EmailRow(
    email: Email,
    isSelected: Boolean,
    selectionMode: Boolean,
    onLongClick: () -> Unit,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        } else {
            Color.Transparent
        },
        animationSpec = tween(200),
        label = "rowBg",
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick,
                )
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // Avatar / Checkbox
            AnimatedContent(
                targetState = isSelected,
                transitionSpec = {
                    (scaleIn(tween(200)) + fadeIn(tween(200))) togetherWith
                        (scaleOut(tween(200)) + fadeOut(tween(200)))
                },
                label = "avatar",
            ) { selected ->
                if (selected) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(email.avatarColor),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = email.sender.first().toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = email.sender,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = email.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = email.subject,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = email.preview,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 72.dp),
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        )
    }
}
