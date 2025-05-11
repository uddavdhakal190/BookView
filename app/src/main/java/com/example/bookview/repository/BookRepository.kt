package com.example.bookview.repository

import com.example.bookview.api.BookApiService
import com.example.bookview.data.Book
import com.example.bookview.data.BookItem

// Repository class to handle book data operations
class BookRepository(private val apiService: BookApiService) {
    
    // Fetch books from the API and transform them into our Book model
    suspend fun getBooks(query: String = "android"): List<Book> {
        return try {
            val response = apiService.searchBooks(query)
            response.items?.map { it.toBook() } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Extension function to convert BookItem to Book
    private fun BookItem.toBook(): Book {
        return Book(
            id = id,
            title = volumeInfo.title,
            authors = volumeInfo.authors ?: emptyList(),
            description = volumeInfo.description,
            thumbnailUrl = volumeInfo.imageLinks?.thumbnail,
            publishedDate = volumeInfo.publishedDate,
            pageCount = volumeInfo.pageCount,
            averageRating = volumeInfo.averageRating,
            infoLink = volumeInfo.infoLink,
            previewLink = volumeInfo.previewLink
        )
    }
} 