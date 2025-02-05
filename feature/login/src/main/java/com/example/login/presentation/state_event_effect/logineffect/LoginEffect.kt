package com.example.login.presentation.state_event_effect.logineffect

import com.example.common.resource.UiText
import com.example.domain.model.story.StoryUiData

sealed interface LoginEffect {
    data class ShowAlert(val message: UiText?) : LoginEffect
    data object NavigateDontHaveAccountFragment : LoginEffect
    data class NavigateStoryFragment(val storyUiData: StoryUiData):LoginEffect
    data object NavigateProfileFragment : LoginEffect
    class ChangeLanguage(val language: String) : LoginEffect
}