package com.example.common.resource

sealed class Resource<T>(
    val data: T? = null,
    val error: UiText? = null
) {
    class Success<T>(data: T? = null) : Resource<T>(data)

    class Error<T>(data: T? = null, text: UiText) : Resource<T>(data = data, error = text)

    class Loading<T> : Resource<T>()

}

fun <T,R> Resource<T>.transform(transform: (T?) -> Resource<R>): Resource<R> {
    return when (this) {
        is Resource.Success -> {
            transform(this.data)
        }
        is Resource.Error -> Resource.Error(text = this.error!!)
        is Resource.Loading -> Resource.Loading()
    }
}

