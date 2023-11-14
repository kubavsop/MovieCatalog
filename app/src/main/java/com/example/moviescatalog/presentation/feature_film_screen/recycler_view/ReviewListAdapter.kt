package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        private var genres = mutableListOf<View>()

        fun bind(
            headerItem: FilmRecyclerViewItem.HeaderItem,
            onAddClick: () -> Unit
        ) {

            val context = binding.root.context.applicationContext

            with(binding) {
                description.text = headerItem.description
                yearDescription.text = headerItem.year.toString()
                countryDescription.text = headerItem.country
                taglineDescription.text = headerItem.tagline
                directorDescription.text = headerItem.director
                budgetDescription.text = headerItem.budget.toString()
                collectionDescription.text = headerItem.fees.toString()
                ageDescription.text = headerItem.ageLimit.toString()
                durationDescription.text = headerItem.time.toString()
                addReviewButton.isVisible = !headerItem.haveReview
                addReviewButton.setOnClickListener { onAddClick() }
            }


            for (genre in genres) {
                binding.root.removeView(genre)
            }

            genres = mutableListOf()

            for (genre in headerItem.genres) {
                val tv = LayoutInflater.from(context)
                    .inflate(R.layout.genre_text_view_movie_screen, binding.root, false).apply {
                        id = View.generateViewId()
                    }
                (tv as? TextView)?.text = genre

                binding.root.addView(tv)
                binding.genres.addView(tv)

                genres.add(tv)
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