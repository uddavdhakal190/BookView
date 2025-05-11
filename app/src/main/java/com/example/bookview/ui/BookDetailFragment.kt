package com.example.bookview.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.bookview.R
import com.example.bookview.data.Book

// Fragment to display detailed information about a book
@Suppress("DEPRECATION")
class BookDetailFragment : Fragment() {

    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            book = it.getParcelable(ARG_BOOK) ?: throw IllegalArgumentException("Book argument required")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set book details
        view.findViewById<TextView>(R.id.bookTitle).text = book.title
        view.findViewById<TextView>(R.id.bookAuthors).text = book.authors.joinToString(", ")
        view.findViewById<TextView>(R.id.bookDescription).text = book.description
        view.findViewById<TextView>(R.id.bookPublishedDate).text = book.publishedDate
        view.findViewById<TextView>(R.id.bookPageCount).text = 
            getString(R.string.page_count, book.pageCount ?: 0)
        view.findViewById<TextView>(R.id.bookRating).text = 
            getString(R.string.rating, book.averageRating ?: 0.0)

        // Load book thumbnail
        Glide.with(this)
            .load(book.thumbnailUrl)
            .placeholder(R.drawable.ic_book_placeholder)
            .error(R.drawable.ic_book_placeholder)
            .into(view.findViewById(R.id.bookThumbnail))

        // Setup info link button
        view.findViewById<Button>(R.id.infoLinkButton).apply {
            isEnabled = !book.infoLink.isNullOrEmpty()
            setOnClickListener {
                book.infoLink?.let { url ->
                    startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                }
            }
        }

        // Setup preview link button
        view.findViewById<Button>(R.id.previewLinkButton).apply {
            isEnabled = !book.previewLink.isNullOrEmpty()
            setOnClickListener {
                book.previewLink?.let { url ->
                    startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
                }
            }
        }
    }

    companion object {
        private const val ARG_BOOK = "book"

        fun newInstance(book: Book) = BookDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_BOOK, book)
            }
        }
    }
} 