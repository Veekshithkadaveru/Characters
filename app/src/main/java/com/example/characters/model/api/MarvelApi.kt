package com.example.characters.model.api

import com.example.characters.model.CharactersApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi{
    @GET("characters")
    fun getCharacters(@Query("nameStartsWith")name:String):Call<CharactersApiResponse>
}