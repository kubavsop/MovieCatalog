package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FilmScreenHeaderBinding
import com.example.moviescatalog.databinding.ReviewListItemBinding
import com.example.moviescatalog.presentation.util.getAverageRatingColor
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

            val context = binding.root.context
            val averageRatingBackground =
                AppCompatResources.getDrawable(context, R.drawable.average_rating_background)

            val ratingColor = getAverageRatingColor(headerItem.averageRating)

            averageRatingBackground?.colorFilter =
                PorterDuffColorFilter(context.getColor(ratingColor), PorterDuff.Mode.SRC_IN)


            with(binding) {
                title.text = headerItem.name
                description.text = headerItem.description
                yearDescription.text = headerItem.year.toString()
                countryDescription.text = headerItem.country
                taglineDescription.text = headerItem.tagline
                directorDescription.text = headerItem.director
                budgetDescription.text = headerItem.budget.toString()
                collectionDescription.text = headerItem.fees.toString()
                ageDescription.text = headerItem.ageLimit.toString()
                durationDescription.text = headerItem.time.toString()
                averageRating.text = headerItem.averageRating.toString()
                averageRating.background = averageRatingBackground
            }
        }
    }

    inner class ReviewViewHolder(private val binding: ReviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewItem: FilmRecyclerViewItem.ReviewItem) {
//            val context = binding.root.context
//            val view = LayoutInflater.from(context)
//                .inflate(R.layout.test_layout, binding.root, false)
//
//            val popupWindow = PopupWindow(context)
//            popupWindow.contentView = view
//            popupWindow.isFocusable = true
//
//            binding.editButton.setOnClickListener { popupWindow.showAsDropDown(binding.editButton,100,100) }
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