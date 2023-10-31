package com.example.moviescatalog.presentation.feature_main_screen

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.MovieElement
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.MovieListItemBinding

class MovieListAdapter :
    PagingDataAdapter<MovieElement, MovieListAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieElement: MovieElement) {
            val context = binding.root.context
            val averageRatingBackground =
                AppCompatResources.getDrawable(context, R.drawable.average_rating_background)

            val ratingColor = when (movieElement.averageRating) {
                in 9.0..10.0 -> R.color.color_rating_9_to
                in 8.0..8.9 -> R.color.color_rating_8_to_9
                in 6.0..7.9 -> R.color.color_rating_6_to_8
                in 4.0..5.9 -> R.color.color_rating_4_to_6
                in 3.0..3.9 -> R.color.color_rating_3_to_4
                in 0.0..2.9 -> R.color.color_rating_to_3
                else -> R.color.color_rating_9_to
            }

            averageRatingBackground?.colorFilter =
                PorterDuffColorFilter(context.getColor(ratingColor), PorterDuff.Mode.SRC_IN)

            with(binding) {
                filmImage.load(movieElement.poster)
                movieTitle.text = movieElement.name
                date.text = "${movieElement.year} · ${movieElement.country}"
                averageRating.text = movieElement.averageRating.toString()
                averageRating.background = averageRatingBackground
            }

            for (genre in movieElement.genres) {
                val tv = LayoutInflater.from(context)
                    .inflate(R.layout.genre_text_view, binding.root, false).apply {
                        id = View.generateViewId()
                    }
                (tv as? TextView)?.text = genre

                binding.root.addView(tv)
                binding.genres.addView(tv)
            }
        }
    }

    var movies: PagingData<MovieElement> = PagingData.empty()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieListItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}

private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieElement>() {
    override fun areItemsTheSame(oldItem: MovieElement, newItem: MovieElement): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieElement, newItem: MovieElement): Boolean =
        oldItem == newItem
}