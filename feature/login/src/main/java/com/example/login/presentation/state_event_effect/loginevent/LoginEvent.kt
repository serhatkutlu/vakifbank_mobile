package com.example.login.presentation.state_event_effect.loginevent

import com.example.domain.model.story.StoryUiData

sealed interface LoginEvent{
    data object LoginClicked : LoginEvent
    data class StoryClicked(val storyUiData: StoryUiData): LoginEvent
    data object DontHaveAccountClicked : LoginEvent
    data object ProfileClicked : LoginEvent
    class SwapLanguageClicked(val language: String) : LoginEvent

}