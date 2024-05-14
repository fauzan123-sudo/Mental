package com.infinity.mental.data.repositories

import com.infinity.mental.data.network.MainApi
import java.util.concurrent.locks.Condition
import javax.inject.Inject


class MainRepository @Inject constructor(private val api: MainApi) : BaseRepository() {

    suspend fun getBlog() = safeApiCall {
        api.getBlog()
    }

    suspend fun searchBlog(blog:String?) = safeApiCall {
        api.searchBlog(blog)
    }

    suspend fun getQuestionnaire(number: Int) = safeApiCall {
        api.getQuestionnaire(number)
    }

    suspend fun postQuestionnaire(condition: String) = safeApiCall {
        api.postQuestionnaire(condition)
    }

    suspend fun sendResultQuestionnaire(condition: String) = safeApiCall {
        api.sendQuestionnaireResult(condition)
    }
}