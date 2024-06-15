package com.hangrycoder.searchmviapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hangrycoder.searchmviapp.intent.UserIntent
import com.hangrycoder.searchmviapp.repository.TransactionRepository
import com.hangrycoder.searchmviapp.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TransactionRepository) :
    ViewModel() {

    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)

    private var _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    init {
        handleIntent()
    }

    @OptIn(FlowPreview::class)
    private fun handleIntent() {
        viewModelScope.launch {
            userIntent
                .consumeAsFlow()
                .debounce(500)
                .collect {
                    when (it) {
                        is UserIntent.Idle -> {
                            //Do Nothing
                        }

                        is UserIntent.Search -> {
                            searchTransactions(it.query)
                        }
                    }
                }
        }
    }

    private fun searchTransactions(query: String) {
        if (query.isEmpty()) {
            _searchState.value = SearchState.Success(null)
            return
        }
        _searchState.value = SearchState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.searchTransactions(query)
                _searchState.value = SearchState.Success(result)
            } catch (e: Exception) {
                println(e.printStackTrace())
                _searchState.value = SearchState.Error("No results. Try another keyword")
            }
        }
    }
}