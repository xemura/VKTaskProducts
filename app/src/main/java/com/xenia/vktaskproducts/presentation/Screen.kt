package com.xenia.vktaskproducts.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.xenia.vktaskproducts.R
import com.xenia.vktaskproducts.domain.Product
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PaginationExample(
    paddingValues: PaddingValues,
    productViewModel: ProductViewModel
) {
    val page = remember { mutableIntStateOf(0) }
    val loading = remember { mutableStateOf(false) }
    val itemList = remember { mutableStateListOf<Product>() }
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(itemList) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 10.dp),
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
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = item.description,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        color = Color.Gray
                    )
                    Text(
                        text = "Price: ${item.price} $",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp),
                    )
                }
            }
        }

        item {
            if (loading.value) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp), strokeWidth = 2.dp)
                }
            }
        }
    }

    LaunchedEffect(key1 = page.intValue) {
        loading.value = true
        itemList.addAll(getData(page.intValue, productViewModel))
        loading.value = false
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { index ->
                if (!loading.value && index != null && index >= itemList.size - 1) {
                    page.intValue++
                }
            }
    }
}

@Composable
fun LoadImageFromUrl(url: String?) {
    val painter: Painter = rememberImagePainter(
        data = url,
        builder = {
            size(200, 200)
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_connection_error)
            crossfade(true)
        }
    )
    Image(
        painter = painter,
        contentDescription = "Изображение товара",
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}

suspend fun getData(page: Int, productViewModel: ProductViewModel): List<Product> {
    return productViewModel.getNewData(page*20, 20)
}