package com.infinity.mental.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.mental.adapter.AdapterAssessment
import com.infinity.mental.data.model.blog.ModelBlogResponse
import com.infinity.mental.data.model.quisioner.ModelQuestionnaire
import com.infinity.mental.data.model.quisioner.send.ModelSendQuisionnare
import com.infinity.mental.data.model.result.ModelResultResponse
import com.infinity.mental.data.repositories.MainRepository
import com.infinity.mental.utils.NetworkResult
import com.infinity.mental.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val adapterAssessment: AdapterAssessment
) : ViewModel() {
    private var currentPage = 0

//    init {
//        requestQuestionnaire(currentPage)
//    }

//    val isLastData = MutableLiveData<Boolean>()

    private val _listBlogResponse = SingleLiveEvent<NetworkResult<ModelBlogResponse>>()
    val listCategoryResponse: LiveData<NetworkResult<ModelBlogResponse>>
        get() = _listBlogResponse

    private val _responseSendQuestionnaire = SingleLiveEvent<NetworkResult<ModelSendQuisionnare>>()
    val responseSendQuestionnaire: LiveData<NetworkResult<ModelSendQuisionnare>>
        get() = _responseSendQuestionnaire

    private val _listQuestionnaireResponse =
        SingleLiveEvent<NetworkResult<ModelQuestionnaire>>()
    val listQuestionnaireResponse: LiveData<NetworkResult<ModelQuestionnaire>>
        get() = _listQuestionnaireResponse

    private val _resultQuestionnaire =
        SingleLiveEvent<NetworkResult<ModelResultResponse>>()
    val resultQuestionnaire: LiveData<NetworkResult<ModelResultResponse>>
        get() = _resultQuestionnaire

    fun getBlog(blog:String? ="") {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _listBlogResponse.postValue(NetworkResult.Loading())
                _listBlogResponse.postValue(repository.searchBlog(blog))
            } else {

                _listBlogResponse.postValue(NetworkResult.Error("No Internet Connection"))
            }

        }
    }

    fun searchBlog(blog:String?) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _listBlogResponse.postValue(NetworkResult.Loading())
                _listBlogResponse.postValue(repository.searchBlog(blog))
            } else {

                _listBlogResponse.postValue(NetworkResult.Error("No Internet Connection"))
            }

        }
    }



    fun requestQuestionnaire(number: Int) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _listQuestionnaireResponse.postValue(NetworkResult.Loading())
                _listQuestionnaireResponse.postValue(repository.getQuestionnaire(number))
            } else {
//                isLastData.postValue(true)
                _listQuestionnaireResponse.postValue(NetworkResult.Error("No Internet Connection"))
            }
        }
    }

    fun loadNextQuestionnaire(parameter: Int) {
        viewModelScope.launch {
//            currentPage++
            requestQuestionnaire(parameter)
            resetSelection()
        }
    }

    fun requestSendQuestionnaire(condition: String) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _responseSendQuestionnaire.postValue(NetworkResult.Loading())
                _responseSendQuestionnaire.postValue(repository.postQuestionnaire(condition))
            } else {
                _responseSendQuestionnaire.postValue(NetworkResult.Error("No Internet Connection"))
            }
        }
    }
    fun requestResultQuestionnaire(condition: String) {
        viewModelScope.launch {
            val connected = CheckInternet().check()
            if (connected) {
                _resultQuestionnaire.postValue(NetworkResult.Loading())
                _resultQuestionnaire.postValue(repository.sendResultQuestionnaire(condition))
            } else {
                _resultQuestionnaire.postValue(NetworkResult.Error("No Internet Connection"))
            }
        }
    }

    fun resetSelection() {
        adapterAssessment.setSelected(-1)
    }
}