package com.example.common.resource

sealed class ResourceUi<T>(
    val data: T? = null,
    val error: UiText? = null
) {
    class Success<T>(data: T? = null) : ResourceUi<T>(data)

    class Error<T>(data: T? = null, text: UiText?) : ResourceUi<T>(data = data, error = text)

    class Idle<T> : ResourceUi<T>()

    class Loading<T> : ResourceUi<T>()


}


