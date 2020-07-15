package com.pratthamarora.rickandmorty_graphql

import android.content.Context
import com.apollographql.apollo.ApolloClient

object AplClient {
    private var apClient: ApolloClient? = null
    private const val BASE_URL = "https://rickandmortyapi.com/graphql/"
    
    @JvmStatic
    fun getApolloClient(context: Context): ApolloClient? {

        //If Apollo Client is not null, return it else make a new Apollo Client.
        //Helps in singleton pattern.
        apClient?.let {
            return it
        } ?: kotlin.run {
            apClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .build()
        }
        return apClient
    }
}