package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import android.app.Dialog
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FilmScreenHeaderBinding
import com.example.moviescatalog.databinding.ReviewDialogBinding
import com.example.moviescatalog.databinding.ReviewListItemBinding
import com.example.moviescatalog.presentation.util.getAverageRatingColor
import com.example.moviescatalog.presentation.util.getRatingColor
import java.lang.IllegalArgumentException

class ReviewListAdapter(
    private val onFavoriteClick: (isAdd: Boolean, id: String) -> Unit,
    private val onAddClick: () -> Unit
) : ListAdapter<FilmRecyclerViewItem, RecyclerView.ViewHolder>(DIFF) {

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
            is HeaderViewHolder -> holder.bind(
                item as FilmRecyclerViewItem.HeaderItem,
                onFavoriteClick,
                onAddClick
            )

            is ReviewViewHolder -> holder.bind(item as FilmRecyclerViewItem.ReviewItem)
        }
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is FilmRecyclerViewItem.HeaderItem -> headerViewType
        is FilmRecyclerViewItem.ReviewItem -> reviewViewType
    }


    inner class HeaderViewHolder(private val binding: FilmScreenHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            headerItem: FilmRecyclerViewItem.HeaderItem,
            onFavoriteClick: (isAdd: Boolean, id: String) -> Unit,
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
                        headerItem.id
                    )
                }
                addReviewButton.isVisible = !headerItem.haveReview
                addReviewButton.setOnClickListener { onAddClick() }
            }
        }
    }

    inner class ReviewViewHolder(private val binding: ReviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewItem: FilmRecyclerViewItem.ReviewItem) {
            val context = binding.root.context.applicationContext
//            val view = LayoutInflater.from(context)
//                .inflate(R.layout.test_layout, binding.root, false)
//
//            val popupWindow = PopupWindow(context)
//            popupWindow.contentView = view
//            popupWindow.isFocusable = true
//
//            binding.editButton.setOnClickListener { popupWindow.showAsDropDown(binding.editButton,100,100) }

            val userRatingBackground =
                AppCompatResources.getDrawable(context, R.drawable.user_rating)

            val userRatingColor = getRatingColor(reviewItem.rating)

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
                return oldItem is FilmRecyclerViewItem.HeaderItem && newItem is FilmRecyclerViewItem.HeaderItem
            }

            override fun areContentsTheSame(
                oldItem: FilmRecyclerViewItem,
                newItem: FilmRecyclerViewItem
            ): Boolean {
                if (oldItem is FilmRecyclerViewItem.ReviewItem && newItem is FilmRecyclerViewItem.ReviewItem) {
                    return oldItem == newItem
                }
                return oldItem is FilmRecyclerViewItem.HeaderItem && newItem is FilmRecyclerViewItem.HeaderItem
            }
        }
    }
}