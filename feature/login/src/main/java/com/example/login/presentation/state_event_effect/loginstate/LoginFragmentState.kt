package com.example.login.presentation.state_event_effect.loginstate

import com.example.common.resource.ResourceUi
import com.example.domain.model.story.StoryUiData
import com.example.login.model.UserInformation

data class LoginFragmentState(val storyData: ResourceUi<List<StoryUiData>>,val userInformation: UserInformation)