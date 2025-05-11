package com.example.bookview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookview.data.Book
import com.example.bookview.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel class to manage book data and UI state
class BookViewModel(private val repository: BookRepository) : ViewModel() {
    
    // StateFlow to hold the list of books
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    // StateFlow to hold loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // StateFlow to hold error state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Function to load books
    fun loadBooks(query: String = "android") {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _books.value = repository.getBooks(query)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
} 