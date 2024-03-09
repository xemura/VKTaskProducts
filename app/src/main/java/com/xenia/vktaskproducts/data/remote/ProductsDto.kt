package com.xenia.vktaskproducts.data.remote

data class ProductsDto(
    val limit: Int,
    val products: List<ProductDto>,
    val skip: Int,
    val total: Int
)