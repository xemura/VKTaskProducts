package com.xenia.vktaskproducts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.xenia.vktaskproducts.R
import com.xenia.vktaskproducts.model.Product

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    marsUiState: ProductUiState,
    modifier: Modifier = Modifier,
) {
    when (marsUiState) {
        is ProductUiState.Loading -> LoadingScreen()
        is ProductUiState.Success -> ResultScreen(
            paddingValues,
            marsUiState.products
        )
        is ProductUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}


@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(10.dp),
            color = Color.DarkGray
        )
        Text(text = "Loading...", modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
            textAlign = TextAlign.Center)
    }
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
fun ResultScreen(paddingValues: PaddingValues, products: List<Product>?) {

    if (products != null) {
        Column(
            modifier = Modifier
                .fillMaxSize().padding(top = paddingValues.calculateTopPadding())
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(5.dp)
            ) {
                CustomStaggeredVerticalGrid(
                    numColumns = 2,
                    modifier = Modifier.padding(5.dp)
                ) {
                    products.forEach { item ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.CenterHorizontally),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                LoadImageFromUrl(item.thumbnail)

                                Text(
                                    text = item.title,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                        .fillMaxWidth()
                                )
                                Text(
                                    text = item.description,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp),
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadImageFromUrl(url: String) {
    val painter: Painter = rememberImagePainter(
        data = url,
        builder = {
            size(300, 300)
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_connection_error)
            crossfade(true)
        }
    )
    Image(
        painter = painter,
        contentDescription = "Изображение товара",
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}

@Composable
fun CustomStaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurable, constraints ->
        // on below line we are creating a variable for our column width.
        val columnWidth = (constraints.maxWidth / numColumns)

        // on the below line we are creating and initializing our items constraint widget.
        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        // on below line we are creating and initializing our column height
        val columnHeights = IntArray(numColumns) { 0 }

        // on below line we are creating and initializing placeables
        val placeables = measurable.map { measurable ->
            // inside placeable we are creating
            // variables as column and placeables.
            val column = testColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)

            // on below line we are increasing our column height/
            columnHeights[column] += placeable.height
            placeable
        }

        // on below line we are creating a variable for
        // our height and specifying height for it.
        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        // on below line we are specifying height and width for our layout.
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            // on below line we are creating a variable for column y pointer.
            val columnYPointers = IntArray(numColumns) { 0 }

            // on below line we are setting x and y for each placeable item
            placeables.forEach { placeable ->
                // on below line we are calling test
                // column method to get our column index
                val column = testColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )

                // on below line we are setting
                // column y pointer and incrementing it.
                columnYPointers[column] += placeable.height
            }
        }
    }
}

// on below line we are creating a test column method for setting height.
private fun testColumn(columnHeights: IntArray): Int {
    // on below line we are creating a variable for min height.
    var minHeight = Int.MAX_VALUE

    // on below line we are creating a variable for column index.
    var columnIndex = 0

    // on below line we are setting column  height for each index.
    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }
    // at last we are returning our column index.
    return columnIndex
}