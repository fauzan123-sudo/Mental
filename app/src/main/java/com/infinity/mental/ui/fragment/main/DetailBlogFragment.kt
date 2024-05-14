package com.infinity.mental.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.infinity.mental.databinding.FragmentDetailBlogBinding
import com.infinity.mental.ui.fragment.BaseFragment
import com.infinity.mental.utils.Constants
import com.infinity.mental.utils.Constants.BASE_IMAGE

class DetailBlogFragment :
    BaseFragment<FragmentDetailBlogBinding>(FragmentDetailBlogBinding::inflate) {

    private val args:DetailBlogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = args.detailBlog.judul
        binding.tvDescribe.text = args.detailBlog.isi
        Glide.with(requireContext())
            .load(BASE_IMAGE + args.detailBlog.url_gambar)
//                    .error(R.drawable.ic_no_image)
//                    .placeholder(R.drawable.progress_animation)
            .into(binding.ivContent)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}