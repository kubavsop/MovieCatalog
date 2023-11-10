package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FilmScreenHeaderBinding
import com.example.moviescatalog.databinding.ReviewListItemBinding
import java.lang.IllegalArgumentException

class ReviewListAdapter : ListAdapter<FilmRecyclerViewItem, RecyclerView.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            headerViewType -> HeaderViewHolder(
                FilmScreenHeaderBinding.inflate(inflater, parent, false)
            )

            reviewViewType -> ReviewViewHolder(
                ReviewListItemBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as FilmRecyclerViewItem.HeaderItem)
            is ReviewViewHolder -> holder.bind(item as FilmRecyclerViewItem.ReviewItem)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is FilmRecyclerViewItem.HeaderItem -> headerViewType
        is FilmRecyclerViewItem.ReviewItem -> reviewViewType
    }


    inner class HeaderViewHolder(private val binding: FilmScreenHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(headerItem: FilmRecyclerViewItem.HeaderItem) {
            
        }
    }

    inner class ReviewViewHolder(private val binding: ReviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewItem: FilmRecyclerViewItem.ReviewItem) {

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
                if (oldItem is FilmRecyclerViewItem.ReviewItem && newItem is FilmRecyclerViewItem.ReviewItem) {
                    return newItem.review.id == oldItem.review.id
                }
                return oldItem is FilmRecyclerViewItem.HeaderItem && newItem is FilmRecyclerViewItem.HeaderItem
            }

            override fun areContentsTheSame(
                oldItem: FilmRecyclerViewItem,
                newItem: FilmRecyclerViewItem
            ): Boolean {
                if (oldItem is FilmRecyclerViewItem.ReviewItem && newItem is FilmRecyclerViewItem.ReviewItem) {
                    return newItem.review == oldItem.review
                }
                return oldItem is FilmRecyclerViewItem.HeaderItem && newItem is FilmRecyclerViewItem.HeaderItem
            }
        }
    }
}