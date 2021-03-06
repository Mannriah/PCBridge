package com.projectcitybuild.api.client

import com.projectcitybuild.api.interfaces.BanApiInterface
import com.projectcitybuild.api.interfaces.AuthApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PCBClient(private val authToken: String,
                private val baseUrl: String) {

    val instance: Retrofit = build()

    val banApi: BanApiInterface = instance.create(BanApiInterface::class.java)
    val authApi: AuthApiInterface = instance.create(AuthApiInterface::class.java)

    private fun build() : Retrofit {
        val authenticatedClient = makeAuthenticatedClient(authToken)
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(authenticatedClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun makeAuthenticatedClient(token: String) : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientFactory = OkHttpClient().newBuilder()
                .addInterceptor { chain ->
                    // Add access token as header to each API request
                    val request = chain.request()
                    val requestBuilder = request.newBuilder().header("Authorization", "Bearer $token")
                    val nextRequest = requestBuilder.build()

                    chain.proceed(nextRequest)
                }
                .addInterceptor(interceptor)

        return clientFactory.build()
    }
}