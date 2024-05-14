package com.infinity.mental.ui.fragment.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.infinity.mental.R
import com.infinity.mental.databinding.FragmentFifthScreenBinding
import com.infinity.mental.databinding.FragmentSixthScreenBinding
import com.infinity.mental.ui.activity.MainActivity
import com.infinity.mental.utils.startNewActivity

class SixthScreen : Fragment() {
    private var _binding: FragmentSixthScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSixthScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.btnNext.setOnClickListener {
            requireActivity().startNewActivity(MainActivity::class.java)
//            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
            onBoardingIsFinished()
        }

        return view
    }
    private fun onBoardingIsFinished() {

        val sharedPreferences =
            requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("finished", true)
        editor.apply()
    }

}