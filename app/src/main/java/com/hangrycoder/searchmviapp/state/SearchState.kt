package com.hangrycoder.searchmviapp.state

import com.hangrycoder.searchmviapp.model.Transaction

sealed class SearchState {
    object Idle : SearchState()
    object Loading : SearchState()
    data class Success(val transactions: List<Transaction>) : SearchState()
    data class Error(val error: String) : SearchState()
}