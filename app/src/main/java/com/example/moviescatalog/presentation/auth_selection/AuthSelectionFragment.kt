package com.example.moviescatalog.presentation.auth_selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviescatalog.databinding.FragmentAuthSelectionBinding
import com.example.moviescatalog.presentation.util.mainActivity

class AuthSelectionFragment : Fragment() {
    private var _binding: FragmentAuthSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthSelectionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registration.setOnClickListener { mainActivity.openDetailedUserRegistrationFromSelection() }
        binding.loginTo.setOnClickListener { mainActivity.openUserAuthorization() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}