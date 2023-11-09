package com.example.moviescatalog.presentation.feature_main_screen.recycler_view

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
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.MainScreenHeaderBinding
import com.example.moviescatalog.databinding.MovieListItemBinding
import com.example.moviescatalog.presentation.util.getRatingColor
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.lang.IllegalArgumentException

class MovieListAdapter(
    private val movieClickListener: (String) -> Unit
) :
    PagingDataAdapter<MainRecyclerViewItem, RecyclerView.ViewHolder>(COMPARATOR) {

    var movies: PagingData<MainRecyclerViewItem> = PagingData.empty()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            headerViewType -> HeaderViewHolder(
                MainScreenHeaderBinding.inflate(inflater, parent, false)
            )

            movieViewType -> MovieViewHolder(
                MovieListItemBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            when(holder) {
                is HeaderViewHolder -> holder.bind(it as MainRecyclerViewItem.HeaderItem, movieClickListener)
                is MovieViewHolder -> holder.bind(it as MainRecyclerViewItem.MovieItem, movieClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) headerViewType else movieViewType
    }

    inner class HeaderViewHolder(private val binding: MainScreenHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(headerItem: MainRecyclerViewItem.HeaderItem, movieClickListener: (String) -> Unit) {
            val movies = headerItem.movies

            val carouselListener = object : CarouselListener {
                override fun onClick(position: Int, carouselItem: CarouselItem) {
                    super.onClick(position, carouselItem)
                    movieClickListener(movies[position].id)
                }
            }
            val imageList = movies.map { CarouselItem(imageUrl = it.poster) }

            with(binding) {
                carousel.carouselListener = carouselListener
                carousel.setData(imageList)
            }
        }

//        private fun getCarouselClickListener(movieClickListener: (String) -> Unit) =
//            object : CarouselListener {
//                override fun onClick(position: Int, carouselItem: CarouselItem) {
//                    super.onClick(position, carouselItem)
//                    val id = carouselItem.headers?.get("id")
//                    id?.let { movieClickListener(it) }
//                }
//            }
    }

    inner class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var genres = mutableListOf<View>()

        fun bind(movieItem: MainRecyclerViewItem.MovieItem, movieClickListener: (String) -> Unit) {
            val movieElement = movieItem.movieElement
            val context = binding.root.context
            val averageRatingBackground =
                AppCompatResources.getDrawable(context, R.drawable.average_rating_background)

            val ratingColor = getRatingColor(movieElement.averageRating)

            averageRatingBackground?.colorFilter =
                PorterDuffColorFilter(context.getColor(ratingColor), PorterDuff.Mode.SRC_IN)

            with(binding) {
                filmImage.load(movieElement.poster) {
                    placeholder(R.drawable.test_film_image)
                }
                movieTitle.text = movieElement.name
                date.text = "${movieElement.year} · ${movieElement.country}"
                averageRating.text = movieElement.averageRating.toString()
                averageRating.background = averageRatingBackground
            }

            for (genre in genres) {
                binding.root.removeView(genre)
            }

            genres = mutableListOf()

            for (genre in movieElement.genres) {
                val tv = LayoutInflater.from(context)
                    .inflate(R.layout.genre_text_view, binding.root, false).apply {
                        id = View.generateViewId()
                    }
                (tv as? TextView)?.text = genre

                binding.root.addView(tv)
                binding.genres.addView(tv)

                genres.add(tv)
            }

            itemView.setOnClickListener { movieClickListener(movieElement.id) }

        }
    }

    private companion object {
        val headerViewType = R.layout.main_screen_header
        val movieViewType = R.layout.movie_list_item
    }
}

private val COMPARATOR = object : DiffUtil.ItemCallback<MainRecyclerViewItem>() {
    override fun areItemsTheSame(
        oldItem: MainRecyclerViewItem,
        newItem: MainRecyclerViewItem
    ): Boolean {
        if (oldItem is MainRecyclerViewItem.MovieItem && newItem is MainRecyclerViewItem.MovieItem) {
            return newItem.movieElement.id == oldItem.movieElement.id
        }
        return oldItem is MainRecyclerViewItem.HeaderItem && newItem is MainRecyclerViewItem.HeaderItem
    }

    override fun areContentsTheSame(
        oldItem: MainRecyclerViewItem,
        newItem: MainRecyclerViewItem
    ): Boolean {
        if (oldItem is MainRecyclerViewItem.MovieItem && newItem is MainRecyclerViewItem.MovieItem){
            return newItem.movieElement == oldItem.movieElement
        }
        return oldItem is MainRecyclerViewItem.HeaderItem && newItem is MainRecyclerViewItem.HeaderItem
    }
}