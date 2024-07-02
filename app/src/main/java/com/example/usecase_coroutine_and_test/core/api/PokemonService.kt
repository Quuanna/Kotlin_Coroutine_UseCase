package com.example.usecase_coroutine_and_test.core.api

import com.example.usecase_coroutine_and_test.core.model.response.PokemonInfoResponse
import com.example.usecase_coroutine_and_test.core.model.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonInfoResponse
}