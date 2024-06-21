package com.example.worldnews.api

interface ResultInterface<T> {
    fun onSuccess(response: T)

    fun onFailure(error: NewsError)
}