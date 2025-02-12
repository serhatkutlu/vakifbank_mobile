package com.example.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.resource.Resource
import com.example.common.resource.ResourceUi
import com.example.common.resource.UiText
import com.example.domain.usecase.story.GetStoryDataUseCase
import com.example.login.R
import com.example.login.presentation.state_event_effect.loginstate.LoginFragmentState
import com.example.login.model.UserInformation
import com.example.login.presentation.state_event_effect.logineffect.LoginEffect
import com.example.login.presentation.state_event_effect.loginevent.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val getStoryDataUseCaseImplement: GetStoryDataUseCase) :
    ViewModel() {

    private val _loginState = MutableStateFlow(
        LoginFragmentState(
            ResourceUi.Idle(),
            UserInformation.Idle
        )
    )
    val loginState = _loginState.asStateFlow()

    private val _event: MutableSharedFlow<LoginEvent> = MutableSharedFlow()
    //val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    private val _effect: MutableSharedFlow<LoginEffect> = MutableSharedFlow()
    val effect: SharedFlow<LoginEffect> = _effect.asSharedFlow()

    init {
        getStoryData()
        collectEvent()
    }

    private fun getStoryData() {
        viewModelScope.launch {
            getStoryDataUseCaseImplement.invoke().collect { storyData ->
                when (storyData) {
                    is Resource.Success -> {
                        _loginState.update {
                            it.copy(storyData = ResourceUi.Success(storyData.data))
                        }
                    }

                    is Resource.Error -> {
                        _effect.emit(LoginEffect.ShowAlert(storyData.error))

                    }

                    is Resource.Loading -> {
                        _loginState.update {
                            it.copy(storyData = ResourceUi.Loading())
                        }
                    }
                }
            }

        }
    }

    fun setEvent(event: LoginEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    private fun collectEvent() {
        viewModelScope.launch {
            _event.collect {
                when (it) {
                    is LoginEvent.LoginClicked -> {
                        loginRequest()
                    }

                    is LoginEvent.DontHaveAccountClicked -> {

                    }

                    is LoginEvent.ProfileClicked -> {

                    }

                    is LoginEvent.SwapLanguageClicked -> {

                    }

                    is LoginEvent.StoryClicked -> {
                        _effect.emit(LoginEffect.NavigateStoryFragment(it.ordinal, _loginState.value.storyData.data ?: listOf()))
                    }

                }

            }

        }
    }

    fun setLoginStateUserInformation(userInformation: UserInformation) {
        _loginState.update {
            it.copy(userInformation = userInformation)
        }
    }

    private fun loginRequest() {
        viewModelScope.launch {
            _effect.emit(LoginEffect.ShowAlert(UiText.StringResource(R.string.this_feature_not_developed_yet)))
        }
    }
}