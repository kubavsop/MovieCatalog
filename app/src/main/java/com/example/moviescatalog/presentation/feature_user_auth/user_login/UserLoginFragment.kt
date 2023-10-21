package com.example.moviescatalog.presentation.feature_user_auth.user_login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moviescatalog.databinding.FragmentUserLoginBinding
import com.example.moviescatalog.presentation.util.mainActivity

class UserLoginFragment : Fragment() {
    private var _binding: FragmentUserLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserLoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        binding.loginTo.setOnClickListener {
            viewModel.login(
                username = binding.loginEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
        }
        binding.backspace.setOnClickListener { mainActivity.openAuthSelectionFromLogin() }
    }

    private fun handleState(state: UserLoginState) {
        when(state) {
            UserLoginState.Initial -> Unit
            UserLoginState.Success -> Unit
            UserLoginState.Loading -> Unit
            is UserLoginState.Error -> Unit
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}