package com.example.moviescatalog.presentation.feature_main_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.domain.model.MovieElement
import com.example.moviescatalog.databinding.FragmentMainBinding
import com.example.moviescatalog.presentation.feature_main_screen.recycler_view.MovieListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openFilmScreen(id: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as FragmentCallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =  MovieListAdapter(::handleMovieClick)
        binding.moviesList.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }

        adapter.addLoadStateListener { state ->
            binding.moviesList.isVisible = state.refresh != LoadState.Loading
            binding.progressBar.isVisible = state.refresh == LoadState.Loading
        }
    }

    private fun handleMovieClick(id: String) {
        fragmentCallBack?.openFilmScreen(id)
    }

    override fun onDestroyView() {
        binding.moviesList.adapter = null
        _binding = null
        super.onDestroyView()
    }
}