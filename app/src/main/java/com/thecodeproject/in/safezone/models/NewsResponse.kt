package com.thecodeproject.`in`.safezone.models

data class NewsResponse(
    val articles: List<Articles>
)

data class Articles(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)