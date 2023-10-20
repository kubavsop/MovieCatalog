package com.example.moviescatalog.presentation.password_registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.moviescatalog.databinding.FragmentPasswordRegistrationBinding
import com.example.moviescatalog.presentation.util.mainActivity

class PasswordRegistrationFragment : Fragment() {
    private var _binding: FragmentPasswordRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordRegistrationViewModel by activityViewModels()
    private val args: PasswordRegistrationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)

        val afterTextPasswordChangedListener = getAfterTextPasswordChangedListener()
        binding.passwordEditText.addTextChangedListener(afterTextPasswordChangedListener)
        binding.repeatedPasswordEditText.addTextChangedListener(afterTextPasswordChangedListener)
        binding.backspace.setOnClickListener { mainActivity.openDetailedUserRegistrationFromPasswordRegistration() }
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
    }

    private fun handleState(state: PasswordRegistrationState) {
        binding.passwordEditTextContainer.error = state.passwordError?.asString(requireContext())
        binding.repeatedPasswordEditTextContainer.error =
            state.repeatedPasswordError?.asString(requireContext())

        val hasEmpty = listOf(
            binding.passwordEditText.text.toString(),
            binding.repeatedPasswordEditText.text.toString()
        ).any { it.isEmpty() }

        binding.register.isEnabled = state.isValid && !hasEmpty
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}