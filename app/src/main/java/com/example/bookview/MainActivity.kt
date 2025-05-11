package com.example.bookview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookview.ui.BookListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BookListFragment.newInstance())
                .commit()
        }
    }
}


