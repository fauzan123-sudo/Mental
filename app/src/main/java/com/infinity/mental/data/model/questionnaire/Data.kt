package com.infinity.mental.data.model.quisioner

data class Data(
    val count_gejala: Int,
    val gejala: Gejala,
    val kondisi_user: List<KondisiUser>
)