package com.example.moviescatalog.presentation.feature_main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.MovieElement
import com.example.moviescatalog.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        viewModel.carouselMovies()


        binding.moviesList.setHasFixedSize(false)
        binding.moviesList.isNestedScrollingEnabled = false

        binding.moviesList.adapter = MovieListAdapter()
        lifecycleScope.launch {
            viewModel.moviePagingFlow.collectLatest { pagingData ->
                (binding.moviesList.adapter as? MovieListAdapter)?.submitData(pagingData)
            }
        }
    }

    private fun handleState(state: List<MovieElement>) {

        val imageList = state.map { CarouselItem(imageUrl = it.poster) }

        binding.carousel.setData(imageList)
    }

    override fun onDestroyView() {
        binding.moviesList.adapter = null
        _binding = null
        super.onDestroyView()
    }
}