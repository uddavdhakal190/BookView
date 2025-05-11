package com.example.bookview.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookview.R
import com.example.bookview.data.Book

// Adapter class for displaying books in a RecyclerView
class BookAdapter(private val onItemClick: (Book) -> Unit) :
    ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnail: ImageView = itemView.findViewById(R.id.bookThumbnail)
        private val title: TextView = itemView.findViewById(R.id.bookTitle)
        private val authors: TextView = itemView.findViewById(R.id.bookAuthors)
        private val publishedDate: TextView = itemView.findViewById(R.id.bookPublishedDate)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(book: Book) {
            title.text = book.title
            authors.text = book.authors.joinToString(", ")
            publishedDate.text = book.publishedDate

            // Load book thumbnail using Glide
            Glide.with(itemView.context)
                .load(book.thumbnailUrl)
                .placeholder(R.drawable.ic_book_placeholder)
                .error(R.drawable.ic_book_placeholder)
                .into(thumbnail)
        }
    }

    private class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
} 