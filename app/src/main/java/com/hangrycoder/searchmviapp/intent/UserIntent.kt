package com.hangrycoder.searchmviapp.intent

sealed class UserIntent {
    data class Search(val query: String): UserIntent()
}