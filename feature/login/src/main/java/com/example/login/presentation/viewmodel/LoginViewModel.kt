package com.example.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.common.resource.ResourceUi
import com.example.domain.usecase.story.GetStoryDataUseCase
import com.example.login.presentation.state.loginstate.LoginFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val getStoryDataUseCaseImplement: GetStoryDataUseCase): ViewModel()  {

    private val _loginState= MutableStateFlow(LoginFragmentState(ResourceUi.Idle()))
     val loginState=_loginState.asStateFlow()

    init {
        getStoryData()
    }
    fun getStoryData(){
        viewModelScope.launch {
            getStoryDataUseCaseImplement.invoke().collect{
                when(it){
                    is Resource.Success->{
                        _loginState.value=LoginFragmentState(ResourceUi.Success(it.data))
                    }

                    is Resource.Error->{
                        _loginState.value=LoginFragmentState(ResourceUi.Error(text = it.error))
                    }
                    is Resource.Loading->{
                        _loginState.value=LoginFragmentState(ResourceUi.Loading())
                        }
                }
            }

        }
    }
}