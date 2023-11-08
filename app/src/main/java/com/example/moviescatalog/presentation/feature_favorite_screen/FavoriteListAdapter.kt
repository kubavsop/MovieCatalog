package com.example.moviescatalog.presentation.feature_favorite_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.MovieElement
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FavoriteListItemBinding

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(private val binding: FavoriteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieElement: MovieElement) {
            with(binding) {
                filmImage.load(movieElement.poster) {
                    placeholder(R.drawable.test_film_image)
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