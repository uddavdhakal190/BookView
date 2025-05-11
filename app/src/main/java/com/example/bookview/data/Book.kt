package com.example.bookview.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data class representing a book from the Google Books API
@Parcelize
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String?,
    val thumbnailUrl: String?,
    val publishedDate: String?,
    val pageCount: Int?,
    val averageRating: Double?,
    val infoLink: String?,
    val previewLink: String?
) : Parcelable

// Response wrapper for the Google Books API
data class BooksResponse(
    val items: List<BookItem>?
)

// Individual book item from the API response
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

// Volume information containing book details
data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val publishedDate: String?,
    val pageCount: Int?,
    val averageRating: Double?,
    val infoLink: String?,
    val previewLink: String?
)

// Image links for book covers
data class ImageLinks(
    val thumbnail: String?
) 