package com.infinity.mental.data.model.result

data class ModelResultResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)