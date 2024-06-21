package com.example.worldnews.model

import java.util.*

data class Article(
    val fragmentSource: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String
)