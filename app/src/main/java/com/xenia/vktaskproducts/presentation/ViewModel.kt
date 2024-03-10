package com.xenia.vktaskproducts.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.xenia.vktaskproducts.data.mappers.toProduct
import com.xenia.vktaskproducts.data.remote.APIService
import com.xenia.vktaskproducts.data.remote.ProductDto
import com.xenia.vktaskproducts.data.remote.ProductsDto
import com.xenia.vktaskproducts.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsApi: APIService
): ViewModel() {

//    val productPagingFlow = pager
//        .flow
//        .map { pagingData ->
//            pagingData.map { it.toProduct() }
//        }
//        .cachedIn(viewModelScope)

    val productsList = mutableListOf<Product>()

    fun getNewData(skip: Int, limit: Int) {
        viewModelScope.launch {
            val response = productsApi.getProducts(skip, limit)
            response.enqueue(object : Callback<ProductsDto> {
                override fun onResponse(
                    call: Call<ProductsDto>,
                    response: Response<ProductsDto>
                ) {
                    if (response.isSuccessful) {
                        val products = response.body()?.products

                        products?.forEach {
                            productsList.add(it.toProduct())

                        }
                        Log.d("MainActivity", "get")
                    } else {
                        Log.d("MainActivity", "Failed")
                    }
                }

                override fun onFailure(call: Call<ProductsDto?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

    }
}
