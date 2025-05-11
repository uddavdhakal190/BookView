package com.example.bookview.api

import com.example.bookview.data.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Interface for the Google Books API service
interface BookApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20
    ): BooksResponse
} 