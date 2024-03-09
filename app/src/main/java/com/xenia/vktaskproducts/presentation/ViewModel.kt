package com.xenia.vktaskproducts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.xenia.vktaskproducts.data.mappers.toProduct
import com.xenia.vktaskproducts.data.remote.ProductDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    pager: Pager<Int, ProductDto>
): ViewModel() {

    val productPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toProduct() }
        }
        .cachedIn(viewModelScope)
}