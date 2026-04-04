package io.github.aldefy.contextualappbar

import androidx.compose.runtime.Composable

@Composable
internal actual fun PlatformBackHandler(enabled: Boolean, onBack: () -> Unit) {
    // No system back on desktop — contextual bar close is via X button only
}
