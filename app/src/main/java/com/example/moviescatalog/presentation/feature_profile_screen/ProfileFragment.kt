package com.example.moviescatalog.presentation.feature_profile_screen

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.moviescatalog.R
import com.example.moviescatalog.databinding.FragmentProfileBinding
import com.example.moviescatalog.presentation.feature_profile_screen.state.Gender
import com.example.moviescatalog.presentation.feature_profile_screen.state.ProfileEditTextChanged
import com.example.moviescatalog.presentation.feature_profile_screen.state.ProfileState
import com.example.moviescatalog.presentation.util.setContainerError

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by activityViewModels()
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openAuthSelectionScreenFromProfile()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as? FragmentCallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        val datePicker = createDatePickerDialog()

        with(binding) {
            birthdayText.setOnClickListener { datePicker.show() }
            firstNameEditText.addTextChangedListener(getAfterTextChangedListener(ProfileEditTextChanged.FIRST_NAME_CHANGED))
            avatarLinkEditText.addTextChangedListener(getAfterTextChangedListener(ProfileEditTextChanged.AVATAR_CHANGED))
            emailEditText.addTextChangedListener(getAfterTextChangedListener(ProfileEditTextChanged.EMAIL_CHANGED))
            maleButton.setOnClickListener { viewModel.onEvent(ProfileEvent.GenderChanged(Gender.MALE)) }
            femaleButton.setOnClickListener { viewModel.onEvent(ProfileEvent.GenderChanged(Gender.FEMALE)) }
            apply.setOnClickListener { viewModel.onEvent(
                ProfileEvent.ChangeProfile(
                    avatarLink = avatarLinkEditText.text.toString(),
                    email = emailEditText.text.toString(),
                    name = firstNameEditText.text.toString(),
                )
            ) }
            cancel.setOnClickListener { viewModel.onEvent(ProfileEvent.Cancel) }
            exit.setOnClickListener { viewModel.onEvent(ProfileEvent.Exit) }
        }

        viewModel.onEvent(ProfileEvent.ShowProfile)
    }

    private fun handleState(state: ProfileState) {
        when (state) {
            ProfileState.Initial -> Unit
            ProfileState.Loading -> showProgressBar()
            is ProfileState.Profile -> showProfile(state)
            is ProfileState.ProfileChanged -> showChanges(state)
            is ProfileState.Exit -> fragmentCallBack?.openAuthSelectionScreenFromProfile()
            is ProfileState.AuthorisationError -> fragmentCallBack?.openAuthSelectionScreenFromProfile()
        }
    }

    private fun showProgressBar() {
        with(binding) {
            progressBar.isVisible = true
            profileInfo.visibility = View.INVISIBLE
        }
    }

    private fun showProfile(profile: ProfileState.Profile) {
        with(binding) {
            profileImage.load(profile.avatarLink) {
                crossfade(true)
                placeholder(R.drawable.anonymous)
                error(R.drawable.anonymous)
            }
            avatarLinkEditText.setText(profile.avatarLink)
            birthdayText.text = profile.birthDate
            emailEditText.setText(profile.email)
            login.text = profile.nickName
            firstNameEditText.setText(profile.name)
            progressBar.isVisible = false
            profileInfo.isVisible = true
            maleButton.isChecked = profile.gender == Gender.MALE
            femaleButton.isChecked = profile.gender == Gender.FEMALE
        }
    }

    private fun showChanges(state: ProfileState.ProfileChanged) {
        with(binding) {
            emailEditTextContainer.setContainerError(state.emailError, requireContext())
            firstNameEditTextContainer.setContainerError(
                state.firstNameError,
                requireContext()
            )

            birthdayText.text = state.birthDate
            maleButton.isChecked = state.gender == Gender.MALE
            femaleButton.isChecked = state.gender == Gender.FEMALE
            apply.isEnabled = state.isValid
        }
    }


    private fun getAfterTextChangedListener(editTextChanged: ProfileEditTextChanged) =
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                val value = s.toString()
                when (editTextChanged) {
                    ProfileEditTextChanged.EMAIL_CHANGED -> viewModel.onEvent(
                        ProfileEvent.EmailChanged(value)
                    )

                    ProfileEditTextChanged.AVATAR_CHANGED -> viewModel.onEvent(
                        ProfileEvent.AvatarChanged(value)
                    )

                    ProfileEditTextChanged.FIRST_NAME_CHANGED -> viewModel.onEvent(
                        ProfileEvent.FirstNameChanged(value)
                    )
                }
            }
        }

    private fun createDatePickerDialog(): DatePickerDialog {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                viewModel.onEvent(
                    ProfileEvent.BirthdayChanged(
                        year,
                        monthOfYear,
                        dayOfMonth
                    )
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.set(
            MIN_YEAR,
            MIN_MONTH,
            MIN_DAY
        )
        datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis

        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.set(
            MAX_YEAR,
            MAX_MONTH,
            MAX_DAY
        )
        datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis

        return datePickerDialog
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val MIN_YEAR = 1880
        const val MAX_YEAR = 2020
        const val MIN_MONTH = 0
        const val MIN_DAY = 1
        const val MAX_MONTH = 11
        const val MAX_DAY = 31
    }
}