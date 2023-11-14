package com.example.moviescatalog.presentation.feature_favorite_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.MovieElement
import com.example.moviescatalog.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by activityViewModels()
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openAuthSelectionFromFavorite()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as? FragmentCallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 2) 2 else 1
            }
        }

        binding.favoriteList.adapter = FavoriteListAdapter()
        binding.favoriteList.layoutManager = layoutManager

        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        viewModel.loadMovies()
    }

    private fun handleState(state: FavoriteState) {
        when (state) {
            FavoriteState.Initial -> Unit
            FavoriteState.Loading -> showProgressBar()
            FavoriteState.Empty -> showEmpty()
            FavoriteState.AuthorisationError -> fragmentCallBack?.openAuthSelectionFromFavorite()
            is FavoriteState.Content -> showContent(state.movies)
        }
    }

    private fun showEmpty() {
        with(binding){
            progressBar.isVisible = false
            favoriteList.isVisible = false
            emptyInfo.isVisible = true
        }
    }

    private fun showContent(movies: List<MovieElement>) {
        with(binding){
            emptyInfo.isVisible = false
            progressBar.isVisible = false
            favoriteList.isVisible = true
            (favoriteList.adapter as? FavoriteListAdapter)?.movies = movies
        }
    }

    private fun showProgressBar() {
        with(binding){
            emptyInfo.isVisible = false
            favoriteList.isVisible = false
            progressBar.isVisible = true
        }
    }

    override fun onDestroyView() {
        binding.favoriteList.adapter = null
        _binding = null
        super.onDestroyView()
    }
}