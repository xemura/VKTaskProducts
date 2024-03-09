package com.xenia.vktaskproducts.domain

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val thumbnail: String?,
)