package com.xenia.vktaskproducts.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import javax.inject.Inject

//class ProductsSource @Inject constructor(
//    private val productsApi: APIService
//): PagingSource<Int, ProductDto>() {
//
//    private var skip = 0
//
//    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(10) ?: anchorPage?.nextKey?.minus(10)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
//        return try {
//            delay(2000L)
//            val response = productsApi.getProducts(skip, 10).products.sortedBy { it.id }
//            skip += 10
//            Log.d("LoadParams", response.toString())
//            LoadResult.Page(
//                data = response,
//                prevKey = if (skip == 1) null else skip - 10,
//                nextKey = if (response.isEmpty()) null else skip.plus(10)
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}