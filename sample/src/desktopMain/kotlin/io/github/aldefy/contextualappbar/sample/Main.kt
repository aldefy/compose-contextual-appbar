package io.github.aldefy.contextualappbar.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ContextualAppBar Sample",
    ) {
        SampleApp()
    }
}
