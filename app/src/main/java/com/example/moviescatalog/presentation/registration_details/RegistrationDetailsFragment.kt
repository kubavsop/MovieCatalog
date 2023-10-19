package com.example.moviescatalog.presentation.registration_details

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviescatalog.databinding.FragmentRegistrationDetailsBinding

class RegistrationDetailsFragment : Fragment() {

    private var _binding: FragmentRegistrationDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistrationDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        val datePicker = createDatePickerDialog()

        binding.emailEditText.addTextChangedListener(getAfterTextChangedListener(DetailsEditTextChanged.EMAIL_CHANGED))
        binding.firstNameEditText.addTextChangedListener(getAfterTextChangedListener(DetailsEditTextChanged.FIRST_NAME_CHANGED))
        binding.loginEditText.addTextChangedListener(getAfterTextChangedListener(DetailsEditTextChanged.LOGIN_CHANGED))
        binding.birthdayText.setOnClickListener { datePicker.show() }
    }


    private fun handleState(state: RegistrationDetailsFormState) {
        binding.loginEditTextContainer.error = state.loginError?.asString(requireContext())
        binding.emailEditTextContainer.error = state.emailError?.asString(requireContext())
        binding.firstNameEditTextContainer.error = state.firstNameError?.asString(requireContext())
        binding.birthdayText.text = state.birthday

        val hasEmpty = listOf(
            binding.loginEditText.text.toString(),
            binding.emailEditText.text.toString(),
            binding.loginEditText.text.toString()
        ).any { it.isEmpty() }
        binding.continueRegistration.isEnabled = state.isValid && !hasEmpty
    }

    private fun createDatePickerDialog(): DatePickerDialog {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                viewModel.onEvent(
                    RegistrationDetailsEvent.BirthdayChanged(
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
        minDateCalendar.set(MIN_YEAR, MIN_MONTH, MIN_DAY)
        datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis

        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.set(MAX_YEAR, MAX_MONTH, MAX_DAY)
        datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis

        return datePickerDialog
    }

    private fun getAfterTextChangedListener(editTextChanged: DetailsEditTextChanged) =
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                val value = s.toString()
                when (editTextChanged) {
                    DetailsEditTextChanged.EMAIL_CHANGED -> viewModel.onEvent(
                        RegistrationDetailsEvent.EmailChanged(
                            value
                        )
                    )

                    DetailsEditTextChanged.FIRST_NAME_CHANGED -> viewModel.onEvent(
                        RegistrationDetailsEvent.FirstNameChanged(
                            value
                        )
                    )

                    DetailsEditTextChanged.LOGIN_CHANGED -> viewModel.onEvent(
                        RegistrationDetailsEvent.LoginChanged(
                            value
                        )
                    )
                }
            }
        }

    private companion object {
        const val MIN_YEAR = 1880
        const val MAX_YEAR = 2020
        const val MIN_MONTH = 0
        const val MIN_DAY = 1
        const val MAX_MONTH = 11
        const val MAX_DAY = 31
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}