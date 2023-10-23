package com.example.moviescatalog.presentation.feature_user_auth.auth_selection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviescatalog.databinding.FragmentAuthSelectionBinding

class AuthSelectionFragment : Fragment() {
    private var _binding: FragmentAuthSelectionBinding? = null
    private val binding get() = _binding!!
    private var fragmentCallBack: FragmentCallBack? = null

    interface FragmentCallBack {
        fun openDetailedUserRegistrationFromSelection()
        fun openUserAuthorization()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallBack = context as FragmentCallBack
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registration.setOnClickListener { fragmentCallBack?.openDetailedUserRegistrationFromSelection() }
        binding.loginTo.setOnClickListener { fragmentCallBack?.openUserAuthorization() }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}