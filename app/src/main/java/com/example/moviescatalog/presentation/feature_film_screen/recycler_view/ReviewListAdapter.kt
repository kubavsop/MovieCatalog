package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FilmScreenHeaderBinding
import com.example.moviescatalog.databinding.ReviewListItemBinding
import com.example.moviescatalog.presentation.util.getAverageRatingColor
import com.example.moviescatalog.presentation.util.getRatingColor

class ReviewListAdapter(
    private val onFavoriteClick: (isAdd: Boolean) -> Unit,
    private val onAddClick: () -> Unit,
    private val showAsDropDown: (
        view: View,
        id: String,
        reviewText: String,
        rating: Int,
        isAnonymous: Boolean
    ) -> Unit,
) : ListAdapter<FilmRecyclerViewItem, ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(
                item as FilmRecyclerViewItem.HeaderItem,
                onFavoriteClick,
                onAddClick
            )

            is ReviewViewHolder -> holder.bind(
                item as FilmRecyclerViewItem.ReviewItem,
                showAsDropDown
            )
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is FilmRecyclerViewItem.HeaderItem -> headerViewType
        is FilmRecyclerViewItem.ReviewItem -> reviewViewType
    }


    inner class HeaderViewHolder(private val binding: FilmScreenHeaderBinding) :
        ViewHolder(binding.root) {
        fun bind(
            headerItem: FilmRecyclerViewItem.HeaderItem,
            onFavoriteClick: (isAdd: Boolean) -> Unit,
            onAddClick: () -> Unit
        ) {

            val context = binding.root.context.applicationContext
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
                favoriteButton.isChecked = headerItem.inFavorite
                favoriteButton.setOnClickListener {
                    onFavoriteClick(
                        favoriteButton.isChecked,
                    )
                }
                addReviewButton.isVisible = !headerItem.haveReview
                addReviewButton.setOnClickListener { onAddClick() }
            }
        }
    }

    inner class ReviewViewHolder(private val binding: ReviewListItemBinding) :
        ViewHolder(binding.root) {
        fun bind(
            reviewItem: FilmRecyclerViewItem.ReviewItem,
            showAsDropDown: (
                view: View,
                id: String,
                reviewText: String,
                rating: Int,
                isAnonymous: Boolean
            ) -> Unit,
        ) {
            val context = binding.root.context


            val userRatingBackground =
                AppCompatResources.getDrawable(context, R.drawable.user_rating)

            val userRatingColor = getRatingColor(reviewItem.rating)

            binding.editButton.setOnClickListener {
                showAsDropDown(
                    binding.editButton,
                    reviewItem.id,
                    reviewItem.reviewText,
                    reviewItem.rating,
                    reviewItem.isAnonymous
                )
            }

            userRatingBackground?.colorFilter =
                PorterDuffColorFilter(
                    context.getColor(userRatingColor),
                    PorterDuff.Mode.SRC_IN
                )

            if (!reviewItem.isAnonymous) {
                with(binding) {
                    profileImage.load(reviewItem.author?.avatar) {
                        crossfade(true)
                        placeholder(R.drawable.anonymous)
                        error(R.drawable.anonymous)
                    }
                    login.text = reviewItem.author?.nickName
                }
            } else {
                binding.login.text = context.getString(R.string.anonymous_user)
                binding.profileImage.load(R.drawable.anonymous)
            }

            with(binding) {
                myReview.isVisible = reviewItem.isMine
                editButton.isVisible = reviewItem.isMine
                reviewText.text = reviewItem.reviewText
                date.text = reviewItem.createDateTime
                userRating.background = userRatingBackground
                userRating.text = reviewItem.rating.toString()
            }
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
                    return newItem.id == oldItem.id
                }
                if (oldItem is FilmRecyclerViewItem.HeaderItem && newItem is FilmRecyclerViewItem.HeaderItem) {
                    return oldItem.id == newItem.id
                }
                return false
            }

            override fun areContentsTheSame(
                oldItem: FilmRecyclerViewItem,
                newItem: FilmRecyclerViewItem
            ): Boolean {
                if (oldItem is FilmRecyclerViewItem.ReviewItem && newItem is FilmRecyclerViewItem.ReviewItem) {
                    return oldItem == newItem
                }
                if (oldItem is FilmRecyclerViewItem.HeaderItem && newItem is FilmRecyclerViewItem.HeaderItem) {
                    return oldItem == newItem
                }
                return false
            }
        }
    }
}