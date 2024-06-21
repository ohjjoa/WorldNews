package com.example.worldnews.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    fun getInternationalHeadlines(@Query("country") country: String,
                                  @Query("category") category: String,
                                  @Query("pageSize") pageSize: Int,
                                  @Query("page") page: Int): Call<NewsResponse>

    @GET("v2/everything")
    fun getKeywordNews(@Query("q") keyword: String,
                       @Query("pageSize") pageSize: Int,
                       @Query("page") page: Int): Call<NewsResponse>

    @GET("v2/sources")
    fun getNewsProviders(): Call<SourceResponse>

    companion object {
        private const val BASE_URL = "http://newsapi.org/"
        // TODO: API 키를 설정해주세요.
        private const val API_KEY = "571d532c69b04297b09c7333553f2687"

        fun create(): NewsService {
            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Api-Key", API_KEY)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)
        }
    }
}