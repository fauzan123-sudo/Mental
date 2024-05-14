package com.infinity.mental.ui.fragment.faq

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.infinity.mental.databinding.FragmentFaqBinding
import com.infinity.mental.ui.fragment.BaseFragment

class FaqFragment : BaseFragment<FragmentFaqBinding>(FragmentFaqBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}