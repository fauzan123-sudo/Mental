package com.infinity.mental.data.model.blog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelDetailBlog(
    val image: String? = "",
    val content: String? = ""
) : Parcelable