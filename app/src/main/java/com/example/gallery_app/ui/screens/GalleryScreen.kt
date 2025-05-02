package com.example.gallery_app.ui.screens


import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.example.gallery_app.model.ImageData
import com.example.gallery_app.ui.components.ImageCard

@Composable
fun GalleryScreen(imageList: List<ImageData>, onImageClick: (String) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(imageList) { image ->
            ImageCard(url = image.url, onClick = { onImageClick(image.url) })
        }
    }
}
