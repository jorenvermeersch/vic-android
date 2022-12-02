package com.example.vic.screens.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vic.R
import com.example.vic.databinding.FragmentNavigationBarBinding

class NavigationBarFragment : Fragment() {

    private lateinit var binding: FragmentNavigationBarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_navigation_bar, container, false)

        return binding.root
    }
}
