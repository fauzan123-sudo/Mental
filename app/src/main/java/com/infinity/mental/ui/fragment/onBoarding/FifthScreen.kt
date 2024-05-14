package com.infinity.mental.ui.fragment.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.infinity.mental.R
import com.infinity.mental.databinding.FragmentFifthScreenBinding


class FifthScreen : Fragment() {
    private var _binding: FragmentFifthScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFifthScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 5
        }
        return view
    }
}