package com.infinity.mental.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.infinity.mental.R
import com.infinity.mental.adapter.AdapterBlog
import com.infinity.mental.data.model.blog.Data
import com.infinity.mental.databinding.FragmentHomeBinding
import com.infinity.mental.ui.fragment.BaseFragment
import com.infinity.mental.ui.viewModel.MainViewModel
import com.infinity.mental.utils.NetworkResult
import com.infinity.mental.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    AdapterBlog.ItemListener {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapterBlog: AdapterBlog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearch()

        setUpData("")
        binding.mcvBlog.setOnClickListener {
            try {
//                if (findNavController().currentDestination?.id == R.id.homeFragment) {
                    findNavController().navigate(R.id.action_homeFragment_to_blogFragment)
//                }
            } catch (e: Exception) {
                showErrorMessage(e.toString())
                Log.e("TAG", "error navigation: ", e)
            }
        }

        binding.mcvDepression.setOnClickListener {
            try {
//                if (findNavController().currentDestination?.id == R.id.homeFragment) {
                    findNavController().navigate(R.id.action_homeFragment_to_assessmentFragment)
//                }
            } catch (e: Exception) {
                showErrorMessage(e.toString())
                Log.e("TAG", "error navigation: ", e)
            }

        }

    }

    private fun setUpSearch() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etSearch.clearFocus()
                hideKeyboard(requireView())
                val inputText = binding.etSearch.text.toString()
                setUpData(inputText)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setUpData(blog: String?) {
        viewModel.getBlog(blog)
        viewModel.listCategoryResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoading()
                    val response = it.data!!
                    loadRecyclerview(response.data)
                }

                is NetworkResult.Loading -> {
                    showLoading()
                }

                is NetworkResult.Error -> {
                    hideLoading()
                    showErrorMessage(it.message!!)
                }
            }
        }
    }

    private fun loadRecyclerview(data: List<Data>) {
        adapterBlog.differ.submitList(data)
        with(binding.rvBlog) {
            layoutManager =
                LinearLayoutManager(
                    requireContext()
                )
            adapter = adapterBlog
            adapterBlog.listener = this@HomeFragment
        }
    }

    override fun clicked(data: Data) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailBlogFragment(data)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        setUpSearch()
        setUpData("")
    }
}