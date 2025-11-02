# Compose Reorderable

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg?style=flat-square)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-purple.svg?style=flat-square)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
[![YouTube Demo](https://img.shields.io/badge/YouTube-Demo-red?style=flat-square)](https://www.youtube.com/watch?v=YOUR_VIDEO_ID)

A Jetpack Compose (Android) library that enables **reordering by drag-and-drop** in LazyLists and LazyGrids with smooth animations.  
Created by **Asadullah Hil Galib**.

---

## 🎬 Demo Video

Watch the library in action:  
[![Compose Reorderable Demo](https://img.youtube.com/vi/c11t-5dnAfg/0.jpg)](https://www.youtube.com/watch?v=c11t-5dnAfg)

---

## 🧩 Features

- Reorderable **LazyColumn** and **LazyRow**  
- Reorderable **LazyVerticalGrid** and **LazyHorizontalGrid**  
- Supports **drag handles**, **animations**, and **custom styling**  
- Simple and lightweight, built for **Jetpack Compose Material3**  

---

## 📦 Installation

Add the library to your project:

```kotlin
dependencies {
    implementation("com.github.codergalib2005:compose-reorderable:<latest_version>")
}
```


## ⚙️ How to Use

### 1. Create a reorderable state

**For LazyList:**

```kotlin
val state = rememberReorderableLazyListState(onMove = { from, to -> 
    data.value = data.value.toMutableList().apply {
        add(to.index, removeAt(from.index))
    }
})
```

## For LazyGrid:


```kotlin
val state = rememberReorderableLazyGridState(
    onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }
)
```

## 2. Add the reorderable modifier

## ⚙️ How to Use (Vertical & Horizontal)

### 1. Vertical Reorderable List (LazyColumn)

```kotlin
@Composable
fun VerticalReorderList() {
    val data = remember { mutableStateOf(List(100) { "Item $it" }) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data.value, { it }) { item ->
            ReorderableItem(state, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(item)
                }
            }
        }
    }
}
```
### 2. Vertical Reorderable Grid (LazyVerticalGrid)

```kotlin
@Composable
fun VerticalReorderGrid() {
    val data = remember { mutableStateOf(List(100) { "Item $it" }) }
    val state = rememberReorderableLazyGridState(
        dragCancelledAnimation = NoDragCancelledAnimation(),
        onMove = { from, to ->
            data.value = data.value.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        }
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = state.gridState,
        modifier = Modifier.reorderable(state)
    ) {
        items(data.value, { it }) { item ->
            ReorderableItem(state, key = item, defaultDraggingModifier = Modifier) { isDragging ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.detectReorderAfterLongPress(state)
                    )
                }
            }
        }
    }
}
```

### 3. Horizontal Reorderable List (LazyRow)

```kotlin
@Composable
fun HorizontalReorderList() {
    val data = remember { mutableStateOf(List(100) { "Item $it" }) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })
    LazyRow(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(data.value, { it }) { item ->
            ReorderableItem(state, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp)
                ) {
                    Text(item)
                }
            }
        }
    }
}
```
### 4. Horizontal Reorderable Grid (LazyHorizontalGrid)
```kotlin
@Composable
fun HorizontalReorderGrid() {
    val data = remember { mutableStateOf(List(100) { "Item $it" }) }
    val state = rememberReorderableLazyGridState(
        dragCancelledAnimation = NoDragCancelledAnimation(),
        onMove = { from, to ->
            data.value = data.value.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        }
    )
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        state = state.gridState,
        modifier = Modifier.reorderable(state)
    ) {
        items(data.value, { it }) { item ->
            ReorderableItem(state, key = item, defaultDraggingModifier = Modifier) { isDragging ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(4.dp)
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.detectReorderAfterLongPress(state)
                    )
                }
            }
        }
    }
}
```


## 📝 Notes

- First visible item may not animate — this is a known issue.
- Drag handle and animation can be customized using detectReorder and defaultDraggingModifier.
- Works with Jetpack Compose Material3 theme.
- Supports keyed and indexed-only lists (animated items only work with keyed lists).

# 👤 Author

## Asadullah Hil Galib
GitHub: [https://github.com/codergalib2005](https://github.com/codergalib2005)

YouTube: [https://www.youtube.com/@devgalib](https://www.youtube.com/@devgalib)


## 📜 License
```kotlin
Copyright 2025 Asadullah Hil Galib

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
