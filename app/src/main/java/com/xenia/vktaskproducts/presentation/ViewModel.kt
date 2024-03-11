package com.xenia.vktaskproducts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.vktaskproducts.data.mappers.toProduct
import com.xenia.vktaskproducts.data.remote.APIService
import com.xenia.vktaskproducts.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsApi: APIService,
): ViewModel() {
    suspend fun getNewData(
        skip: Int,
        limit: Int
    ): List<Product> {
        val productList = viewModelScope.async {
            val list = mutableListOf<Product>()
            productsApi.getProducts(skip, limit).products.forEach {
                list.add(it.toProduct())
            }
            list
        }
        return productList.await()
    }
}
