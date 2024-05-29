package com.infinity.mental.data.network

import com.infinity.mental.data.model.blog.ModelBlogResponse
import com.infinity.mental.data.model.quisioner.ModelQuestionnaire
import com.infinity.mental.data.model.quisioner.send.ModelSendQuisionnare
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {

    @GET("blog")
    suspend fun searchBlog(@Query("search") keyword: String?): Response<ModelBlogResponse>

    @GET("blog")
    suspend fun getBlog(): Response<ModelBlogResponse>

    @GET("quisioner/{number}")
    suspend fun getQuestionnaire(
        @Path("number") number: Int
    ): Response<ModelQuestionnaire>

    @FormUrlEncoded
    @POST("quisioner/store")
    suspend fun postQuestionnaire(@Field("kondisi") condition: String):
            Response<ModelSendQuisionnare>


    @FormUrlEncoded
    @POST("quisioner/result")
    suspend fun sendQuestionnaireResult(@Field("diagnosis_id") diagnosisId: String):
            Response<ModelResultResponse>

}