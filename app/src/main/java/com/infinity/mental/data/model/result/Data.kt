package com.infinity.mental.data.model.result

data class Data(
    val artikel: Artikel,
    val cf_kombinasi: CfKombinasi,
    val data_diagnosa: List<DataDiagnosa>,
    val diagnosa: Diagnosa,
    val diagnosa_dipilih: DiagnosaDipilih,
    val gejala: List<List<Any>>,
    val gejala_by_user: List<List<Any>>,
    val hasil: Hasil,
    val pakar: List<Pakar>
)