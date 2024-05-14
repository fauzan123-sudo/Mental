package com.infinity.mental.data.model.blog

data class ModelBlogResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)