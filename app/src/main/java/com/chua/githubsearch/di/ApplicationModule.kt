package com.chua.githubsearch.di

import com.chua.githubsearch.BuildConfig
import com.chua.githubsearch.domain.Item
import com.chua.githubsearch.network.ItemDto
import com.chua.githubsearch.repository.GithubRepository
import com.chua.githubsearch.repository.GithubRepositoryImpl
import com.chua.githubsearch.service.GithubService
import com.chua.githubsearch.util.DomainMapper
import com.chua.githubsearch.util.ItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    fun provideItemMapper(): DomainMapper<ItemDto, Item> = ItemMapper()

    @Provides
    @Singleton
    fun provideGithubRepository(
        githubService: GithubService,
        itemMapper: DomainMapper<ItemDto, Item>
    ): GithubRepository =
        GithubRepositoryImpl(githubService, itemMapper)


}