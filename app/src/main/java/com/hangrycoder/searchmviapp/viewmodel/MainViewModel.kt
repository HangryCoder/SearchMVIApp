package com.hangrycoder.searchmviapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hangrycoder.searchmviapp.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TransactionRepository) :
    ViewModel() {

    fun searchTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.searchTransactions("So")
        }
    }
}