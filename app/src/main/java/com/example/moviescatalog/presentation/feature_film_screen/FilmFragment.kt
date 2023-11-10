package com.example.moviescatalog.presentation.feature_film_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.domain.model.MovieDetails
import com.example.moviescatalog.databinding.FragmentFavoriteBinding
import com.example.moviescatalog.databinding.FragmentFilmBinding
import com.example.moviescatalog.databinding.FragmentMainBinding
import com.example.moviescatalog.presentation.MainActivity
import com.example.moviescatalog.presentation.feature_favorite_screen.FavoriteViewModel


class FilmFragment : Fragment() {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmViewModel by activityViewModels()
    private val args: FilmFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        viewModel.movieDetails(args.id)
    }

    private fun handleState(state: FilmState) {
//        when(state) {
//            FilmState.Initial -> Unit
//            FilmState.Loading -> showProgress()
//            is FilmState.Content -> showContent(state.movieDetails)
//        }
    }

//    private fun showContent(movieDetails: MovieDetails) {
//        binding.progressBar.isVisible = false
//        binding.test.text = movieDetails.name
//    }
//
//    private fun showProgress() {
//        binding.progressBar.isVisible = true
//    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}