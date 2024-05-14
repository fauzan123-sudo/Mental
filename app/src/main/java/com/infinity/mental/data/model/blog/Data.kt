package com.infinity.mental.data.model.blog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val id: Int,
    val isi: String,
    val judul: String,
    val slug: String,
    val url_gambar: String
) : Parcelable