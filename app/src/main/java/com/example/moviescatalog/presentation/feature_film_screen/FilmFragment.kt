package com.example.moviescatalog.presentation.feature_film_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moviescatalog.databinding.FragmentFavoriteBinding
import com.example.moviescatalog.databinding.FragmentFilmBinding
import com.example.moviescatalog.databinding.FragmentMainBinding
import com.example.moviescatalog.presentation.feature_favorite_screen.FavoriteViewModel


class FilmFragment : Fragment() {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}