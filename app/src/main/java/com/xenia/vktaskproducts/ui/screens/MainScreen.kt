package com.xenia.vktaskproducts.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.xenia.vktaskproducts.R
import com.xenia.vktaskproducts.model.Product
import com.xenia.vktaskproducts.ui.theme.LightGrey

@Composable
fun MainScreen(
    marsUiState: ProductUiState,
    modifier: Modifier = Modifier,
) {
    when (marsUiState) {
        is ProductUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ProductUiState.Success -> ResultScreen(
            marsUiState.products,
            modifier = modifier.fillMaxWidth()
        )
        is ProductUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_loading),
        contentDescription = "Loading..."
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "Loading failed", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ResultScreen(products: List<Product>?, modifier: Modifier = Modifier) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (products != null ) {
            Log.d("tagError", "HERE NOT FOUND")

            items(products) { item ->
                Card(
                    modifier = Modifier.padding(4.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LightGrey
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    LoadImageFromUrl(item.thumbnail)

                    Text(
                        text = item.title,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp).fillMaxWidth()
                    )
                    Text(
                        text = item.description,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp),
                        color = Color.Gray
                    )
                }
            }
        }
        else {
            Log.d("tagError", "NOT FOUND")
        }
    }
}

@Composable
fun LoadImageFromUrl(url: String) {
    val painter: Painter = rememberImagePainter(
        data = url,
        builder = {
            // Optional: set custom parameters like size, scale type, placeholder, etc.
            size(300, 300)
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_connection_error)
            crossfade(true)
        }
    )
    Image(
        painter = painter,
        contentDescription = "Изображение товара",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}