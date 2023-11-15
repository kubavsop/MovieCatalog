package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Shader
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.UpdateAppearance
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getColor
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
                description.maxLines = MAX_COLLAPSED_LINES



                description.viewTreeObserver.addOnPreDrawListener(object :
                    ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        description.viewTreeObserver.removeOnPreDrawListener(this)

                        if (description.lineCount > MAX_COLLAPSED_LINES) {
                            moreDetailsButton.isVisible = true
//                            formatDescriptionText()
                        } else {
                            moreDetailsButton.isVisible = false
                        }
                        return true
                    }
                })


                yearDescription.text = headerItem.year.asString(context)
                countryDescription.text = headerItem.country.asString(context)
                taglineDescription.text = headerItem.tagline.asString(context)
                directorDescription.text = headerItem.director.asString(context)
                budgetDescription.text = headerItem.budget.asString(context)
                collectionDescription.text = headerItem.fees.asString(context)
                ageDescription.text = headerItem.ageLimit.asString(context)
                durationDescription.text = headerItem.time.asString(context)
                addReviewButton.isVisible = !headerItem.haveReview
                addReviewButton.setOnClickListener { onAddClick() }

                moreDetailsButton.setOnClickListener {
                    if (moreDetailsButton.isChecked) {
                        description.maxLines = Int.MAX_VALUE
//                        formatDescriptionCheckedText()

                    } else {
                        description.maxLines = MAX_COLLAPSED_LINES
//                        formatDescriptionText()
                    }
                }
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

//        private fun formatDescriptionDefaultText() {
//            val context = binding.root.context.applicationContext
//            with(binding) {
//                val textColor = getColor(context, R.color.white)
//                val spannableString = SpannableString(description.text)
//                val startOfLine = description.layout.getLineStart(MAX_COLLAPSED_LINES - 1)
//                val endOfLine = description.layout.getLineEnd(MAX_COLLAPSED_LINES - 1)
//
//                spannableString.setSpan(
//                    ForegroundColorSpan(textColor),
//                    startOfLine,
//                    endOfLine,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                description.text = spannableString
//            }
//        }
//
//        private fun formatDescriptionText() {
//            val context = binding.root.context.applicationContext
//            val textColor = getColor(context,R.color.white)
//            val backgroundColor = getColor(context,R.color.background_color)
//            with(binding) {
//
//                val defaultLinearGradient: Shader = LinearGradient(
//                    0f,
//                    0f,
//                    0f,
//                    description.textSize,
//                    intArrayOf(
//                        textColor,
//                        backgroundColor,
//                        backgroundColor,
//                    ),
//                    floatArrayOf(0f, 2f, 50f), Shader.TileMode.CLAMP
//                )
//
//                val spannableString = SpannableString(description.text)
//                val startOfLine = description.layout.getLineStart(MAX_COLLAPSED_LINES - 1)
//                val endOfLine = description.layout.getLineEnd(MAX_COLLAPSED_LINES - 1)
//
//                spannableString.setSpan(
//                    ShaderSpan(defaultLinearGradient),
//                    startOfLine,
//                    endOfLine,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                description.text = spannableString
//            }
//        }
//
//        private fun formatDescriptionCheckedText() {
//            val context = binding.root.context.applicationContext
//            val textColor = getColor(context,R.color.white)
//            val backgroundColor = getColor(context,R.color.background_color)
//            with(binding) {
//
//                val defaultLinearGradient: Shader = LinearGradient(
//                    0f,
//                    0f,
//                    0f,
//                    description.textSize,
//                    intArrayOf(
//                        textColor,
//                        textColor,
//                        textColor,
//                    ),
//                    floatArrayOf(0f, 78f, 100f), Shader.TileMode.CLAMP
//                )
//
//                val spannableString = SpannableString(description.text)
//                val startOfLine = description.layout.getLineStart(MAX_COLLAPSED_LINES - 1)
//                val endOfLine = description.layout.getLineEnd(MAX_COLLAPSED_LINES - 1)
//
//                spannableString.setSpan(
//                    ShaderSpan(defaultLinearGradient),
//                    startOfLine,
//                    endOfLine,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//
//                description.text = spannableString
//            }
//        }

        inner class ShaderSpan(private val shader: Shader) : CharacterStyle(), UpdateAppearance {

            override fun updateDrawState(tp: TextPaint) {
                tp.shader = shader
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

    private companion object {
        const val MAX_COLLAPSED_LINES = 2
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