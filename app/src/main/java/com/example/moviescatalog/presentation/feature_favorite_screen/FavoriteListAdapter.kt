package com.example.moviescatalog.presentation.feature_favorite_screen

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.MovieElement
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FavoriteListItemBinding
import com.example.moviescatalog.presentation.util.getRatingColor

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(private val binding: FavoriteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieElement: MovieElement) {
            val context = binding.root.context

            if (movieElement.userRating != null) {
                val userRatingBackground =
                    AppCompatResources.getDrawable(context, R.drawable.user_rating)

                val userRatingColor = getRatingColor(movieElement.userRating!!)

                userRatingBackground?.colorFilter =
                    PorterDuffColorFilter(
                        context.getColor(userRatingColor),
                        PorterDuff.Mode.SRC_IN
                    )

                with(binding) {
                    userRating.isVisible = true
                    userRating.text = movieElement.userRating.toString()
                    userRating.background = userRatingBackground
                }

            } else {
                binding.userRating.isVisible = false
            }

            with(binding) {
                filmImage.load(movieElement.poster) {
                    crossfade(true)
                }
                filmName.text = movieElement.name
            }
        }
    }

    var movies: List<MovieElement> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoriteListItemBinding.inflate(inflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(movies[position])
    }
}