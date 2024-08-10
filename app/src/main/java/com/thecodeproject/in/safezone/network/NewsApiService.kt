package com.thecodeproject.`in`.safezone.network

import com.thecodeproject.`in`.safezone.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiService {
    @GET("/v2/everything?q=natural%20%disaster&apiKey=dda30d9f895e4af4b091f22bc53a797d")
    suspend fun getNewsArticles(): Response<NewsResponse>
}