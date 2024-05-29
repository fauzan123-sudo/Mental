package com.infinity.mental.ui.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.infinity.mental.adapter.AdapterExpert
import com.infinity.mental.adapter.AdapterResult
import com.infinity.mental.data.model.result.Artikel
import com.infinity.mental.data.model.result.DiagnosaDipilih
import com.infinity.mental.data.model.result.Hasil
import com.infinity.mental.data.model.result.Pakar
import com.infinity.mental.databinding.FragmentResultBinding
import com.infinity.mental.ui.fragment.BaseFragment
import com.infinity.mental.ui.viewModel.MainViewModel
import com.infinity.mental.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate) {

    private val args: ResultFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapterExpert: AdapterExpert

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadApi()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun loadExpert(data: List<Pakar>) {
        adapterExpert.differ.submitList(data)
        with(binding.rvExpert) {
            layoutManager =
                LinearLayoutManager(
                    requireContext()
                )
            adapter = adapterExpert
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadApi() {
        viewModel.requestResultQuestionnaire(args.dataDiagnostic)
        viewModel.resultQuestionnaire.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    hideLoading()
                    val data = it.data!!.data
                    binding.tvDiagnosaId.text = data.diagnosa.diagnosa_id
                    binding.tvDepressionLevel.text =
                        data.diagnosa_dipilih.kode_depresi.kode_depresi + "|" + data.diagnosa_dipilih.kode_depresi.depresi
                    binding.tvPersen.text =
                        data.hasil.value + "%"
                    loadExpert(data.pakar)
//                    loadUserValue(data.gejala_by_user)
                    loadResult1(data.cf_kombinasi.cf)
                    loadResult2(data.hasil, data.diagnosa_dipilih)
                    loadLastResult(data.artikel)
                }

                is NetworkResult.Loading -> {
                    showLoading()
                }

                is NetworkResult.Error -> {
                    hideLoading()
                    Log.e("TAG", "errornya di : ${it.message}")
                    showErrorMessage(it.message!!)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadResult2(hasil: Hasil, diagnosaDipilih: DiagnosaDipilih) {
        binding.tvResult2.text =
            diagnosaDipilih.kode_depresi.kode_depresi + "| ${diagnosaDipilih.value}"
        val formattedCf = String.format("%.2f", hasil.value.toDouble())
        binding.tvResult2Content.text =
            "Jadi dapat disimpulkan bahwa pasien mengalami tingkat depresi yaitu Depresi Ringan dengan tingkat kepastian yaitu ${formattedCf}%"
    }

    private fun loadLastResult(artikel: Artikel) {
        Glide.with(requireContext())
            .load(artikel.url_gambar)
//            .placeholder(R.drawable.progress_animation)
            .into(binding.ivUserImage)

        binding.tvDescribe.text = artikel.isi
    }

    private fun loadResult1(cf: List<Double>) {
        val adapterResult = AdapterResult(cf)
        with(binding.rvValue1) {
            layoutManager =
                LinearLayoutManager(
                    requireContext()
                )
            adapter = adapterResult
        }
    }

    private fun loadUserValue(gejalaByUser: List<List<Any>>) {
        val gejalaList = gejalaByUser.map {
            Gejala_Nilai(it[0] as String, it[1] as Double)
        }
        Log.d("TAG", "load gejalaByUser: $gejalaList")
        val adapterUserValue = AdapterUserValue(gejalaList)
        with(binding.rvUserValue) {
            layoutManager =
                LinearLayoutManager(
                    requireContext()
                )
            adapter = adapterUserValue
        }
    }

}