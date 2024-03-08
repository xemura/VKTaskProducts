package com.xenia.vktaskproducts.ui.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.vktaskproducts.model.Product
import com.xenia.vktaskproducts.model.Products
import com.xenia.vktaskproducts.network.ProductApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


sealed interface ProductUiState {
    data class Success(val products: List<Product>?) : ProductUiState
    data object Error : ProductUiState
    data object Loading : ProductUiState
}


class ViewModel: ViewModel() {
    var productsUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    val productsList: MutableState<ProductUiState> = mutableStateOf(ProductUiState.Loading)

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            productsUiState = ProductUiState.Loading
            productsUiState = try {

                val apiInterface = ProductApi.retrofitService

                val data = mutableListOf<Product>()

                apiInterface.getProducts().enqueue(object : Callback<Products> {
                    override fun onResponse(call: Call<Products>, response: Response<Products>) {
                        if (response.isSuccessful) {
                            val products = response.body()?.products

                            productsList.value = ProductUiState.Success(products)
                            if (products != null) {
                                for (element in products) {
                                    data.add(element)
                                }
                            }
                            Log.d("MainActivity", "get")
                        } else {
                            Log.d("MainActivity", "Failed")
                        }
                    }

                    override fun onFailure(call: Call<Products>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })

                ProductUiState.Success(data)

            } catch (e: IOException) {
                ProductUiState.Error
            } catch (e: HttpException) {
                ProductUiState.Error
            }
        }
    }
}