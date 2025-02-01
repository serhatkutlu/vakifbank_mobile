package com.example.login.presentation.state.loginstate

import com.example.common.resource.ResourceUi
import com.example.domain.model.story.StoryUiData

data class LoginFragmentState(val storyData: ResourceUi<List<StoryUiData>>)