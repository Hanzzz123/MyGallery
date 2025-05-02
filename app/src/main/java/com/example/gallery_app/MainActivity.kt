package com.example.gallery_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.gallery_app.ui.theme.MyGalleryTheme

data class ImageData(val url: String)

class MainActivity : ComponentActivity() {

    private val dummyImages = listOf(
        ImageData("https://picsum.photos/id/237/400/300"),
        ImageData("https://picsum.photos/id/238/400/300"),
        ImageData("https://picsum.photos/id/239/400/300"),
        ImageData("https://picsum.photos/id/240/400/300"),
        ImageData("https://picsum.photos/id/241/400/300"),
        ImageData("https://picsum.photos/id/242/400/300"),
        ImageData("https://picsum.photos/id/243/400/300"),
        ImageData("https://picsum.photos/id/244/400/300"),
        ImageData("https://picsum.photos/id/250/400/300"),
        ImageData("https://picsum.photos/id/251/400/300"),
        ImageData("https://picsum.photos/id/297/400/300"),
        ImageData("https://picsum.photos/id/201/400/300"),
        ImageData("https://picsum.photos/id/299/400/300"),
        ImageData("https://picsum.photos/id/300/400/300")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            MyGalleryTheme(darkTheme = isDarkTheme) {
                GalleryApp(
                    imageList = dummyImages,
                    isDarkTheme = isDarkTheme,
                    toggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Composable
fun GalleryApp(
    imageList: List<ImageData>,
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit

) {
    var selectedImage by remember { mutableStateOf<String?>(null) }

    if (selectedImage != null) {
        DetailScreen(imageUrl = selectedImage!!) {
            selectedImage = null
        }
    } else {
        GalleryScreen(
            imageList = imageList,
            onImageClick = { selectedImage = it },
            isDarkTheme = isDarkTheme,
            toggleTheme = toggleTheme,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    imageList: List<ImageData>,
    onImageClick: (String) -> Unit,
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Gallery")},
                actions ={
                    IconButton(onClick = toggleTheme) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Filled.Star else Icons.Filled.Star,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(imageList) { image ->
                ImageCard(image = image, onClick = onImageClick)
            }
        }
    }
}

@Composable
fun ImageCard(image: ImageData, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(image.url) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(image.url),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(4f / 3f)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(imageUrl: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Image Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}