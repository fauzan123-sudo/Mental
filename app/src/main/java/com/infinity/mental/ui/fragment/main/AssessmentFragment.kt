package com.infinity.mental.ui.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.infinity.mental.adapter.AdapterAssessment
import com.infinity.mental.data.model.quisioner.KondisiUser
import com.infinity.mental.databinding.FragmentAssessmentBinding
import com.infinity.mental.ui.fragment.BaseFragment
import com.infinity.mental.ui.viewModel.MainViewModel
import com.infinity.mental.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AssessmentFragment :
    BaseFragment<FragmentAssessmentBinding>(FragmentAssessmentBinding::inflate) {
    private var counterNumber = 0
    private var previous = ""
    private val myViewModel: MainViewModel by viewModels()
    private var parameter = 0

    @Inject
    lateinit var adapterAssessment: AdapterAssessment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadApi(parameter)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadApi(parameters: Int) {
        Log.d("TAG", "loadApi: parameter $counterNumber")
        binding.btnNext.isEnabled = false
        myViewModel.requestQuestionnaire(parameters)
        myViewModel.listQuestionnaireResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoading()
                    val data = it.data!!.data
                    counterNumber++

                    val maxSymptom = data.count_gejala
//                    if (maxSymptom == currentPage) {
//                        finishButton()
//
//                    } else {
//                        nextButton()
//                    }
                    binding.tvCountSymptom.text = "$counterNumber dari $maxSymptom"
                    val userConditions = data.kondisi_user
                    val symptom = data.gejala
                    val symptomCode = data.gejala.kode_gejala
                    binding.tvSymptom.text = "apakah anda merasa " + symptom.gejala
                    loadRecyclerView(counterNumber, maxSymptom, symptomCode, userConditions)
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

    private fun loadRecyclerView(
        currentPage: Int,
        maxSymptom: Int,
        symptomCode: String,
        data: List<KondisiUser>
    ) {
        Log.d("new recyclerview", "loadRecyclerView: ")
        adapterAssessment.differ.submitList(data)
        with(binding.rvAssessment) {
            myViewModel.resetSelection()
            layoutManager =
                LinearLayoutManager(
                    requireContext()
                )
            adapter = adapterAssessment

            adapterAssessment.listener = object : AdapterAssessment.ItemListener {
                override fun itemSelected(data: KondisiUser) {
//                    Log.d("yang dipilih", symptomCode + " : " + data.nilai.toString())
                    val newData = "\"$symptomCode\":${data.nilai},"
                    val result = (previous + newData)
                    if (currentPage == maxSymptom) {
                        finishButton(result)
                    } else {
                        nextButton()
                    }
                    previous = result
                }
            }
        }
    }


    private fun nextButton() {
        binding.btnNext.isEnabled = true
        binding.btnNext.setOnClickListener {
            parameter++
//            myViewModel.loadNextQuestionnaire(parameter)
            loadApi(parameter)
        }
    }

    private fun finishButton(result: String) {
        val newResult = result.trimEnd(',')
        binding.btnNext.isEnabled = true
        binding.btnNext.text = "Selesai"
        binding.btnNext.setOnClickListener {
            Log.d("Data  kuisioner", "{$newResult}")
            myViewModel.requestSendQuestionnaire("{$newResult}")
            myViewModel.responseSendQuestionnaire.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Success -> {
                        hideLoading()
                        val data = it.data!!.data
                        Log.d("TAG", "result is : ${data.diagnosis_id}")
                        val action =
                            AssessmentFragmentDirections.actionAssessmentFragmentToResultFragment(
                                data.diagnosis_id
                            )
                        findNavController().navigate(action)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        counterNumber = 0
        parameter = 0
    }

}