package com.example.bookview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookview.R
import com.example.bookview.api.BookApiService
import com.example.bookview.repository.BookRepository
import com.example.bookview.viewmodel.BookViewModel
import com.example.bookview.viewmodel.BookViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Fragment to display the list of books
class BookListFragment : Fragment() {
    
    private lateinit var viewModel: BookViewModel
    private lateinit var adapter: BookAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(BookApiService::class.java)
        val repository = BookRepository(api)
        val factory = BookViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BookViewModel::class.java]
        
        // Setup RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = BookAdapter { book ->
            // Handle book item click
            val detailFragment = BookDetailFragment.newInstance(book)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        
        // Observe books data
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books.collectLatest { books ->
                adapter.submitList(books)
            }
        }
        
        // Observe loading state
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest {
                // Handle loading state
            }
        }
        
        // Observe error state
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        }
        
        // Load books
        viewModel.loadBooks()
    }
    
    companion object {
        fun newInstance() = BookListFragment()
    }
} 