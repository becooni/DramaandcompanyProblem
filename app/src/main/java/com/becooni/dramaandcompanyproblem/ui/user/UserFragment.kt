package com.becooni.dramaandcompanyproblem.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.becooni.dramaandcompanyproblem.R
import com.becooni.dramaandcompanyproblem.databinding.FragmentUserBinding
import com.becooni.dramaandcompanyproblem.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUserBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = sharedViewModel
    }
}