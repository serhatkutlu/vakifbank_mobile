package com.example.story.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoryViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<StoryEvent>()

    private val _effect = MutableSharedFlow<StoryEffect>()
    val effect = _effect.asSharedFlow()


    init {
        collectEvent()
    }

    fun setEvent(event: StoryEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }

    }

    private fun collectEvent() {
        viewModelScope.launch {
            _event.collect { event ->
                when (event) {
                    is StoryEvent.NextClicked -> {
                        _effect.emit(StoryEffect.NextPage)
                    }

                    is StoryEvent.PreviousClicked -> {
                        _effect.emit(StoryEffect.PreviousPage)
                    }

                    is StoryEvent.OnSwipeUp -> {
                    }

                    is StoryEvent.OnSwipeDown -> {
                        _effect.emit(StoryEffect.Close)
                    }
                }
            }


        }
    }

}

sealed interface StoryEvent {
    data object NextClicked : StoryEvent
    data object PreviousClicked : StoryEvent
    data object OnSwipeUp : StoryEvent
    data object OnSwipeDown : StoryEvent

}
sealed interface StoryEffect{
    data object NextPage : StoryEffect
    data object PreviousPage : StoryEffect
    data object Close : StoryEffect
}