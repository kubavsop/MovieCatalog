package com.example.moviescatalog.presentation.feature_main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.feature_main_screen.model.MovieElement
import com.example.moviescatalog.databinding.MovieListItemBinding

class MovieListAdapter : PagingDataAdapter<MovieElement, MovieListAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieElement: MovieElement) {
            with(binding) {
                filmImage.load(movieElement.poster)
                movieTitle.text = movieElement.averageRating.toString()
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