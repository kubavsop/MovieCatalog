package com.example.moviescatalog.presentation.feature_user_auth.password_registration

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.moviescatalog.databinding.FragmentPasswordRegistrationBinding
import com.example.moviescatalog.presentation.UiText
import com.example.moviescatalog.presentation.util.setClearFocusOnDoneClick
import com.example.moviescatalog.presentation.util.setContainerError

class PasswordRegistrationFragment : Fragment() {
    private var _binding: FragmentPasswordRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordRegistrationViewModel by activityViewModels()
    private val args: PasswordRegistrationFragmentArgs by navArgs()
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openUserLoginFromPasswordRegistration()
        fun openDetailedUserRegistrationFromPasswordRegistration()
        fun openMainFromPasswordRegistration()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as? FragmentCallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        val afterTextPasswordChangedListener = getAfterTextPasswordChangedListener()
        binding.passwordEditText.addTextChangedListener(afterTextPasswordChangedListener)
        binding.repeatedPasswordEditText.addTextChangedListener(afterTextPasswordChangedListener)
        binding.backspace.setOnClickListener { fragmentCallBack?.openDetailedUserRegistrationFromPasswordRegistration() }
        binding.register.setOnClickListener {
            viewModel.onEvent(
                PasswordRegistrationEvent.Register(
                    userName = args.userName,
                    name = args.name,
                    email = args.email,
                    password = binding.passwordEditText.text.toString(),
                    birthDate = args.birthDate,
                    gender = args.gender
                )
            )
        }
        binding.signIn.setOnClickListener { fragmentCallBack?.openUserLoginFromPasswordRegistration() }
        binding.repeatedPasswordEditText.setClearFocusOnDoneClick()
        viewModel.onEvent(PasswordRegistrationEvent.Content)
    }

    private fun handleState(state: PasswordRegistrationState) {
        binding.passwordEditTextContainer.setContainerError(state.passwordError, requireContext())
        binding.repeatedPasswordEditTextContainer.setContainerError(state.repeatedPasswordError, requireContext())

        val hasEmpty = listOf(
            binding.passwordEditText.text.toString(),
            binding.repeatedPasswordEditText.text.toString()
        ).any { it.isEmpty() }

        binding.register.isEnabled = state.isValid && !hasEmpty

        if (state.isLoading) showProgressBar()
        if (state.isRegistered) goToMainScreen()
        if (state.registrationError != null) showError(state.registrationError)
    }

    private fun goToMainScreen() {
        binding.progressBar.isVisible = false
        fragmentCallBack?.openMainFromPasswordRegistration()
    }

    private fun showError(msg: UiText) {
        with(binding) {
            progressBar.isVisible = false
            passwordEditTextContainer.error = EMPTY_ERROR
            if (passwordEditTextContainer.childCount == 2) {
                passwordEditTextContainer.getChildAt(ERROR_MESSAGE_INDEX).visibility = View.GONE;
            }
            repeatedPasswordEditTextContainer.error = msg.asString(requireContext())
        }
    }
    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    private fun getAfterTextPasswordChangedListener() =
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.onEvent(
                    PasswordRegistrationEvent.PasswordChanged(
                        password = binding.passwordEditText.text.toString(),
                        repeatedPassword = binding.repeatedPasswordEditText.text.toString()
                    )
                )
            }
        }

    private companion object {
        const val EMPTY_ERROR = " "
        const val ERROR_MESSAGE_INDEX = 1
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}