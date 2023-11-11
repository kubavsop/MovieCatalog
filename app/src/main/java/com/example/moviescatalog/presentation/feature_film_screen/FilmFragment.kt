package com.example.moviescatalog.presentation.feature_film_screen

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.moviescatalog.databinding.FragmentFilmBinding
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.FilmRecyclerViewItem
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.ReviewListAdapter
import kotlin.math.abs


class FilmFragment : Fragment() {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilmViewModel by activityViewModels()
    private val args: FilmFragmentArgs by navArgs()
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openMainFromFilmScreen()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as FragmentCallBack
    }
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

//        val dialog = Dialog(requireContext())
//       dialog.setContentView(R.layout.review_dialog)
//        dialog.show()

        binding.appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val totalScrollRange = binding.appBarLayout.totalScrollRange
            val absVerticalOffset = abs(verticalOffset)
            var alpha = (absVerticalOffset / totalScrollRange.toFloat()) - 0.2

            if (alpha < 0) {
                alpha = 0.0
            }

            val colorFilter = PorterDuffColorFilter(Color.argb((alpha * 255).toInt(), 0, 0, 0), PorterDuff.Mode.SRC_ATOP)
            binding.poster.colorFilter = colorFilter
        }




        binding.movieContent.adapter = ReviewListAdapter()
        binding.backspace.setOnClickListener { fragmentCallBack?.openMainFromFilmScreen() }
        viewModel.movieDetails(args.id)
    }

    private fun handleState(state: FilmState) {
        when (state) {
            FilmState.Initial -> Unit
            FilmState.Loading -> showProgress()
            is FilmState.Content -> showContent(state.movieDetails)
        }
    }

    private fun showContent(movieDetails: List<FilmRecyclerViewItem>) {
        with(binding) {
            poster.load((movieDetails[HEADER_INDEX] as  FilmRecyclerViewItem.HeaderItem).poster) {
                crossfade(true)
            }
            (movieContent.adapter as? ReviewListAdapter)?.submitList(movieDetails)
            progressBar.isVisible = false
            movieContent.isVisible = true
            appBarLayout.isVisible = true
        }
    }

    private fun showProgress() {
        with(binding) {
            progressBar.isVisible = true
            movieContent.isVisible = false
            appBarLayout.isVisible = false
        }
    }

    private companion object {
        const val HEADER_INDEX = 0
    }

    override fun onDestroyView() {
        binding.movieContent.adapter = null
        _binding = null
        super.onDestroyView()
    }
}