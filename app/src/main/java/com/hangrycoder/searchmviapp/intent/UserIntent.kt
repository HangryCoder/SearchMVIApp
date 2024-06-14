package com.hangrycoder.searchmviapp.intent

sealed class UserIntent {
    object Idle: UserIntent()
    data class Search(val query: String): UserIntent()
}