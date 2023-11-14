package com.example.moviescatalog.presentation.feature_user_auth.user_login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.domain.model.User
import com.example.moviescatalog.databinding.FragmentUserLoginBinding
import com.example.moviescatalog.presentation.util.setClearFocusOnDoneClick

class UserLoginFragment : Fragment() {
    private var _binding: FragmentUserLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserLoginViewModel by activityViewModels()
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openAuthSelectionFromLogin()
        fun openRegistrationDetailsFromUserLogin()

        fun openMainFromUserLogin()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as? FragmentCallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        binding.loginTo.setOnClickListener {
            viewModel.onEvent(
                UserLoginEvent.Login(
                    username = binding.loginEditText.text.toString(),
                    password = binding.passwordEditText.text.toString()
                )
            )
        }
        binding.backspace.setOnClickListener { fragmentCallBack?.openAuthSelectionFromLogin() }
        binding.register.setOnClickListener { fragmentCallBack?.openRegistrationDetailsFromUserLogin() }
        binding.passwordEditText.setClearFocusOnDoneClick()
        viewModel.onEvent(UserLoginEvent.Initial)
    }

    private fun handleState(state: UserLoginState) {
        when (state) {
            UserLoginState.Initial -> Unit
            UserLoginState.Success -> goToMainScreen()
            UserLoginState.Loading -> showProgressBar()
            is UserLoginState.Error -> showError(state)
        }
    }

    private fun goToMainScreen() {
        binding.progressBar.isVisible = false
        fragmentCallBack?.openMainFromUserLogin()
    }

    private fun showProgressBar() {
        with(binding) {
            loginEditTextContainer.error = null
            passwordEditTextContainer.error = null
            progressBar.isVisible = true
        }
    }

    private fun showError(state: UserLoginState.Error) {
        with(binding) {
            progressBar.isVisible = false
            loginEditTextContainer.error = EMPTY_ERROR
            if (loginEditTextContainer.childCount == 2) {
                loginEditTextContainer.getChildAt(ERROR_MESSAGE_INDEX).visibility = View.GONE;
            }
            passwordEditTextContainer.error = state.msg.asString(requireContext())
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