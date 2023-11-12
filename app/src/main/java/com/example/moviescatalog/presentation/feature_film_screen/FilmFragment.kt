package com.example.moviescatalog.presentation.feature_film_screen

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.moviescatalog.databinding.FragmentFilmBinding
import com.example.moviescatalog.databinding.ReviewDialogBinding
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.FilmRecyclerViewItem
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.ReviewListAdapter
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.DetailsEditTextChanged
import com.example.moviescatalog.presentation.feature_user_auth.registration_details.RegistrationDetailsEvent
import kotlin.math.abs


class FilmFragment : Fragment() {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    private var _dialogBinding: ReviewDialogBinding? = null
    private val dialogBinding get() = _dialogBinding!!

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

        _dialogBinding = ReviewDialogBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )

        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        binding.appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val totalScrollRange = binding.appBarLayout.totalScrollRange
            val absVerticalOffset = abs(verticalOffset)
            var alpha = (absVerticalOffset / totalScrollRange.toFloat()) - 0.2

            if (alpha < 0) {
                alpha = 0.0
            }

            val colorFilter = PorterDuffColorFilter(
                Color.argb((alpha * 255).toInt(), 0, 0, 0),
                PorterDuff.Mode.SRC_ATOP
            )
            binding.poster.colorFilter = colorFilter
        }

        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        binding.movieContent.adapter = ReviewListAdapter(::onFavoriteClick) {
            viewModel.onEvent(FilmEvent.OpenReviewDialog)
            dialog.show()
        }

        binding.movieContent.itemAnimator = null

        binding.backspace.setOnClickListener { fragmentCallBack?.openMainFromFilmScreen() }
        with(dialogBinding) {
            firstStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(FIRST_STAR)) }
            secondStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(SECOND_STAR)) }
            thirdStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(THIRD_STAR)) }
            fourthStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(FOURTH_STAR)) }
            fifthStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(FIFTH_STAR)) }
            sixthStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(SIXTH_STAR)) }
            seventhStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(SEVENTH_STAR)) }
            eighthStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(EIGHTH_STAR)) }
            ninthStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(NINTH_STAR)) }
            tenthStar.setOnClickListener { viewModel.onEvent(FilmEvent.RatingChanged(TENTH_STAR)) }
            reviewEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    viewModel.onEvent(FilmEvent.ReviewTextChanged(s.toString()))
                }
            })
            save.setOnClickListener {
                dialog.hide()
                viewModel.onEvent(
                    FilmEvent.SaveReview(
                        reviewEditText.text.toString(),
                        isAnonymous = checkbox.isChecked
                    )
                )
            }
        }

        viewModel.onEvent(FilmEvent.GetMovieDetails(args.id))
    }

    private fun handleState(state: FilmState) {
        when (state) {
            FilmState.Initial -> Unit
            FilmState.Loading -> showProgress()
            is FilmState.Content -> showContent(state.movieDetails)
            is FilmState.ReviewDialog -> showReviewDialog(
                state.rating,
                state.isNotEmptyRating,
                state.isNotEmptyText
            )
        }
    }

    private fun showReviewDialog(rating: Int?, isNotEmptyRating: Boolean, isNotEmptyText: Boolean) {
        onStarClick(rating)
        with(dialogBinding) {
            save.isEnabled = isNotEmptyRating && isNotEmptyText
        }
    }

    private fun showContent(movieDetails: List<FilmRecyclerViewItem>) {
        with(binding) {
            poster.load((movieDetails[HEADER_INDEX] as FilmRecyclerViewItem.HeaderItem).poster) {
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

    private fun onFavoriteClick(isAdd: Boolean, id: String) {
        viewModel.onEvent(FilmEvent.FavoriteChanged(isAdd = isAdd, id = id))
    }

    private fun onStarClick(starPosition: Int?) {
        if (starPosition == null) return
        val stars = listOf(
            dialogBinding.firstStar,
            dialogBinding.secondStar,
            dialogBinding.thirdStar,
            dialogBinding.fourthStar,
            dialogBinding.fifthStar,
            dialogBinding.sixthStar,
            dialogBinding.seventhStar,
            dialogBinding.eighthStar,
            dialogBinding.ninthStar,
            dialogBinding.tenthStar,
        )

        for (i in stars.indices) {
            stars[i].isChecked = i <= starPosition - 1
        }
    }

    private companion object {
        const val FIRST_STAR = 1
        const val SECOND_STAR = 2
        const val THIRD_STAR = 3
        const val FOURTH_STAR = 4
        const val FIFTH_STAR = 5
        const val SIXTH_STAR = 6
        const val SEVENTH_STAR = 7
        const val EIGHTH_STAR = 8
        const val NINTH_STAR = 9
        const val TENTH_STAR = 10
        const val HEADER_INDEX = 0
    }

    override fun onDestroyView() {
        binding.movieContent.adapter = null
        _binding = null
        super.onDestroyView()
    }
}