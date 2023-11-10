package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FilmScreenHeaderBinding
import com.example.moviescatalog.databinding.ReviewListItemBinding
import com.example.moviescatalog.presentation.feature_main_screen.recycler_view.MovieListAdapter

class ReviewListAdapter : ListAdapter<FilmRecyclerViewItem, RecyclerView.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    inner class HeaderViewHolder(private val binding: FilmScreenHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    inner class ReviewViewHolder(private val binding: ReviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    companion object {
        val headerViewType = R.layout.film_screen_header
        val reviewViewType = R.layout.review_list_item

        val DIFF = object : DiffUtil.ItemCallback<FilmRecyclerViewItem>() {
            override fun areItemsTheSame(
                oldItem: FilmRecyclerViewItem,
                newItem: FilmRecyclerViewItem
            ): Boolean {
                if (oldItem is FilmRecyclerViewItem.MovieReviewItem && newItem is FilmRecyclerViewItem.MovieReviewItem) {
                    return newItem.review.id == oldItem.review.id
                }
                return oldItem is FilmRecyclerViewItem.HeaderMovieItem && newItem is FilmRecyclerViewItem.HeaderMovieItem
            }

            override fun areContentsTheSame(
                oldItem: FilmRecyclerViewItem,
                newItem: FilmRecyclerViewItem
            ): Boolean {
                if (oldItem is FilmRecyclerViewItem.MovieReviewItem && newItem is FilmRecyclerViewItem.MovieReviewItem) {
                    return newItem.review == oldItem.review
                }
                return oldItem is FilmRecyclerViewItem.HeaderMovieItem && newItem is FilmRecyclerViewItem.HeaderMovieItem
            }
        }
    }
}