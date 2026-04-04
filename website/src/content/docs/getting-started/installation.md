---
title: Installation
description: Add Compose Contextual AppBar to your project
---

## Gradle Dependency

Add the dependency to your `commonMain` source set:

```kotlin
// build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.aldefy:contextual-appbar:1.0.0-alpha01")
        }
    }
}
```

### Version Catalog

```toml
# gradle/libs.versions.toml
[versions]
compose-contextual-appbar = "1.0.0-alpha01"

[libraries]
compose-contextual-appbar = { module = "io.github.aldefy:contextual-appbar", version.ref = "compose-contextual-appbar" }
```

Then reference it:

```kotlin
commonMain.dependencies {
    implementation(libs.compose.contextual.appbar)
}
```

## Single-Platform (Android-only)

If you are not using Kotlin Multiplatform, add to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.aldefy:contextual-appbar:1.0.0-alpha01")
}
```

## Platform Artifacts

Gradle resolves the correct artifact automatically. For reference:

| Platform | Artifact ID |
|----------|-------------|
| Android | `contextual-appbar-android` |
| JVM Desktop | `contextual-appbar-jvm` |
| iOS arm64 | `contextual-appbar-iosarm64` |
| iOS Simulator arm64 | `contextual-appbar-iossimulatorarm64` |
| iOS x64 | `contextual-appbar-iosx64` |
| WasmJs | `contextual-appbar-wasmjs` |

## Requirements

- Kotlin 2.1.0+
- Compose Multiplatform 1.7.0+
- Android minSdk 23 (if targeting Android)

## Dependencies

`compose-contextual-appbar` depends on:

- `compose.runtime`
- `compose.foundation`
- `compose.ui`
- `compose.animation`
- `compose.material3`

Material 3 is required because `MaterialContextualTopAppBar` uses `TopAppBar` and `MaterialTheme` color tokens internally. If you only use `ContextualTopAppBar` (the raw composable), you can skip the Material 3 import in your own code.
