package com.hangrycoder.searchmviapp.apiservice

import com.hangrycoder.searchmviapp.model.Transaction
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //Base - https://api.jsonbin.io/
    @GET("/v3/b/6638ada3acd3cb34a843b4a2?meta=false")
    suspend fun search(@Query("term") query: String): List<Transaction>
}