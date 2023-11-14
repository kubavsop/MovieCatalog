package com.example.moviescatalog.presentation.feature_film_screen

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.domain.common.model.ModifiedMoviesDetails
import com.example.domain.common.model.MovieDetails
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FragmentFilmBinding
import com.example.moviescatalog.databinding.ReviewDialogBinding
import com.example.moviescatalog.databinding.ReviewToolsLayoutBinding
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.FilmRecyclerViewItem
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.ReviewListAdapter
import com.example.moviescatalog.presentation.util.getAverageRatingColor
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlin.math.abs


class FilmFragment : Fragment() {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!

    private var _dialogBinding: ReviewDialogBinding? = null
    private val dialogBinding get() = _dialogBinding!!

    private var _popupWindowBinding: ReviewToolsLayoutBinding? = null

    private val popupWindowBinding get() = _popupWindowBinding!!

    private lateinit var dialog: Dialog
    private lateinit var popupWindow: PopupWindow

    private val viewModel: FilmViewModel by activityViewModels()
    private val args: FilmFragmentArgs by navArgs()
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openMainFromFilmScreen()
        fun openAuthSelectionFromFilm()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as? FragmentCallBack
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


        popupWindow = createPopupWindow()
        dialog = createDialog()

        addAppBarOffsetChangedListener()

        with(binding) {
            movieContent.adapter = ReviewListAdapter(
                onAddClick = ::onAddClick,
                showAsDropDown = ::showAsDropDown
            )

            movieContent.itemAnimator = null
            backspace.setOnClickListener { fragmentCallBack?.openMainFromFilmScreen() }
        }

        with(dialogBinding) {
            firstStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(FIRST_STAR))
                ratingChanged(FIRST_STAR)
            }
            secondStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(SECOND_STAR))
                ratingChanged(SECOND_STAR)
            }
            thirdStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(THIRD_STAR))
                ratingChanged(THIRD_STAR)
            }
            fourthStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(FOURTH_STAR))
                ratingChanged(FOURTH_STAR)
            }
            fifthStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(FIFTH_STAR))
                ratingChanged(FIFTH_STAR)
            }
            sixthStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(SIXTH_STAR))
                ratingChanged(SIXTH_STAR)
            }
            seventhStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(SEVENTH_STAR))
                ratingChanged(SEVENTH_STAR)
            }
            eighthStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(EIGHTH_STAR))
                ratingChanged(EIGHTH_STAR)
            }
            ninthStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(NINTH_STAR))
                ratingChanged(NINTH_STAR)
            }
            tenthStar.setOnClickListener {
                viewModel.onEvent(FilmEvent.RatingChanged(TENTH_STAR))
                ratingChanged(TENTH_STAR)
            }
            checkbox.setOnClickListener { viewModel.onEvent(FilmEvent.IsAnonymousChanged(checkbox.isChecked)) }
            reviewEditText.addTextChangedListener(createReviewTextChangedListener())
            cancellation.setOnClickListener { dialog.hide() }
        }

        viewModel.onEvent(FilmEvent.GetMovieDetails(args.id))
    }

    private fun handleState(state: FilmState) {
        when (state) {
            FilmState.Initial -> Unit
            FilmState.Loading -> showProgress()
            FilmState.AuthorisationError -> fragmentCallBack?.openAuthSelectionFromFilm()
            is FilmState.Content -> showContent(state.movieDetails)
            is FilmState.ReviewDialogChanged -> updateReviewDialog(state.isSaveActive)

            is FilmState.ReviewDialog -> showReviewDialog(
                state.rating,
                state.reviewText,
                state.isAnonymous
            )
        }
    }

    private fun showReviewDialog(rating: Int, reviewText: String, isAnonymous: Boolean) {
        ratingChanged(rating)
        with(dialogBinding) {
            reviewEditText.setText(reviewText)
            checkbox.isChecked = isAnonymous
            save.isEnabled = false
        }
        dialog.show()
    }

    private fun updateReviewDialog(
        isSaveActive: Boolean
    ) {
        dialogBinding.save.isEnabled = isSaveActive
    }

    private fun showContent(movieDetails: ModifiedMoviesDetails) {

        val averageRatingBackground =
            AppCompatResources.getDrawable(requireContext(), R.drawable.average_rating_background)

        val ratingColor = getAverageRatingColor(movieDetails.averageRating)

        averageRatingBackground?.colorFilter =
            PorterDuffColorFilter(requireContext().getColor(ratingColor), PorterDuff.Mode.SRC_IN)


        with(binding) {
            title.text = movieDetails.name
            averageRating.text = movieDetails.averageRating.toString()
            averageRating.background = averageRatingBackground
            favoriteButton.isChecked = movieDetails.inFavorite
            favoriteButton.setOnClickListener { onFavoriteClick(favoriteButton.isChecked) }

            toolbarTitle.text = movieDetails.name
            toolbarFavoriteButton.isChecked = movieDetails.inFavorite
            toolbarFavoriteButton.setOnClickListener { onFavoriteClick(toolbarFavoriteButton.isChecked) }


            poster.load(movieDetails.poster) {
                crossfade(true)
            }
            (movieContent.adapter as? ReviewListAdapter)?.submitList(movieDetails.toFilmItem())
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

    private fun onFavoriteClick(isAdd: Boolean) {
        viewModel.onEvent(FilmEvent.FavoriteChanged(isAdd = isAdd))
    }

    private fun onAddClick() {
        viewModel.onEvent(FilmEvent.OpenReviewDialog())
        dialogBinding.save.setOnClickListener {
            dialog.hide()
            viewModel.onEvent(FilmEvent.SaveReview())
        }
    }

    private fun showAsDropDown(
        view: View,
        id: String,
        reviewText: String,
        rating: Int,
        isAnonymous: Boolean
    ) {
        with(popupWindowBinding) {
            delete.setOnClickListener {
                viewModel.onEvent(FilmEvent.DeleteReview(id))
                popupWindow.dismiss()
            }
            edit.setOnClickListener {
                viewModel.onEvent(
                    FilmEvent.OpenReviewDialog(
                        rating = rating,
                        reviewText = reviewText,
                        isAnonymous = isAnonymous
                    )
                )
                popupWindow.dismiss()
            }
        }

        dialogBinding.save.setOnClickListener {
            viewModel.onEvent(FilmEvent.SaveReview(id))
            dialog.hide()
        }
        popupWindow.showAsDropDown(view)
    }

    private fun ratingChanged(rating: Int) {
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
            stars[i].isChecked = i <= rating - 1
        }
    }

    private fun createPopupWindow(): PopupWindow {
        _popupWindowBinding = ReviewToolsLayoutBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )

        val popupWindow = PopupWindow(requireContext())

        popupWindow.isClippingEnabled = true
        popupWindow.contentView = popupWindowBinding.root
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return popupWindow
    }

    private fun createDialog(): Dialog {
        _dialogBinding = ReviewDialogBinding.inflate(
            LayoutInflater.from(requireContext()),
            binding.root,
            false
        )

        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        return dialog
    }

    private fun addAppBarOffsetChangedListener() {
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

            with(binding) {
                if (abs(verticalOffset) >= totalScrollRange) {
                    toolbarTitle.isVisible = true
                    toolbarFavoriteButton.isVisible = true
                } else {
                    toolbarTitle.visibility = View.INVISIBLE
                    toolbarFavoriteButton.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun createReviewTextChangedListener() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            viewModel.onEvent(FilmEvent.ReviewTextChanged(s.toString()))
        }
    }

    private fun ModifiedMoviesDetails.toFilmItem(): List<FilmRecyclerViewItem> {
        return listOf(
            FilmRecyclerViewItem.HeaderItem(
                ageLimit = ageLimit,
                budget = budget,
                country = country,
                description = description,
                director = director,
                fees = fees,
                genres = genres,
                inFavorite = inFavorite,
                name = name,
                poster = poster,
                tagline = tagline,
                time = time,
                year = year,
                haveReview = haveReview,
                id = id,
                averageRating = averageRating
            )
        ) + reviews.map { review ->
            FilmRecyclerViewItem.ReviewItem(
                author = review.author,
                createDateTime = review.createDateTime,
                id = review.id,
                isAnonymous = review.isAnonymous,
                rating = review.rating,
                reviewText = review.reviewText,
                isMine = review.isMine
            )
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
        const val ZERO = 0
    }

    override fun onDestroyView() {
        binding.movieContent.adapter = null
        _binding = null
        _dialogBinding = null
        _popupWindowBinding = null
        super.onDestroyView()
    }
}