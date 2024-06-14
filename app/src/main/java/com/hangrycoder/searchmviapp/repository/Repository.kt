package com.hangrycoder.searchmviapp.repository

import com.hangrycoder.searchmviapp.model.Transaction

interface Repository {
    suspend fun searchTransactions(query: String): List<Transaction>
}