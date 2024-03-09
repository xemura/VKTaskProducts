package com.xenia.vktaskproducts.data.mappers

import com.xenia.vktaskproducts.data.remote.ProductDto
import com.xenia.vktaskproducts.domain.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        thumbnail = thumbnail
    )
}