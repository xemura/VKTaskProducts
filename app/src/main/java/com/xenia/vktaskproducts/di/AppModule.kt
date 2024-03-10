package com.xenia.vktaskproducts.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.xenia.vktaskproducts.data.remote.APIService
import com.xenia.vktaskproducts.data.remote.ProductDto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): APIService {
        return Retrofit.Builder()
            .baseUrl(APIService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

//    @Provides
//    @Singleton
//    fun providePager(
//        productApi: APIService
//    ): Pager<Int, ProductDto> {
//        return Pager(
//            config = PagingConfig(pageSize = 10),
//            pagingSourceFactory = {
//                ProductsSource(
//                    productsApi = productApi
//                )
//            }
//        )
//    }
}