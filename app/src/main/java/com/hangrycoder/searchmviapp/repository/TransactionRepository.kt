package com.hangrycoder.searchmviapp.repository

import com.hangrycoder.searchmviapp.apiservice.ApiService
import com.hangrycoder.searchmviapp.model.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val apiService: ApiService) : Repository {
    override suspend fun searchTransactions(query: String): List<Transaction> {
        return apiService.search(query)
    }
}