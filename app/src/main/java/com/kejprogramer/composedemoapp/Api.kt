package com.kejprogramer.composedemoapp

import retrofit2.http.GET

interface Api {
    @GET("compose_test")
    suspend fun getComposeList(): List<ItemData>
}