package com.dshovhenia.playgroundapp.injection.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import com.dshovhenia.playgroundapp.data.model.VimeoMetadataUser
import com.dshovhenia.playgroundapp.data.model.VimeoMetadataVideo
import com.dshovhenia.playgroundapp.data.remote.VimeoApiService
import com.dshovhenia.playgroundapp.data.remote.authentication.AuthenticationInterceptor
import com.dshovhenia.playgroundapp.data.remote.authentication.VimeoServiceAuthenticator
import com.dshovhenia.playgroundapp.data.remote.deserializer.VimeoMetadataUserDeserializer
import com.dshovhenia.playgroundapp.data.remote.deserializer.VimeoMetadataVideoDeserializer

@Module
class NetworkModule {

  @Provides
  fun loggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
    interceptor.level = HttpLoggingInterceptor.Level.HEADERS
    return interceptor
  }

  @Provides
  fun okHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authenticationInterceptor: AuthenticationInterceptor,
    vimeoServiceAuthenticator: VimeoServiceAuthenticator
  ): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
      .addInterceptor(authenticationInterceptor).authenticator(vimeoServiceAuthenticator).build()
  }

  @Provides
  fun gson(): Gson {
    return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").registerTypeHierarchyAdapter(
      VimeoMetadataUser::class.java, VimeoMetadataUserDeserializer()
    ).registerTypeHierarchyAdapter(
      VimeoMetadataVideo::class.java, VimeoMetadataVideoDeserializer()
    ).create()
  }

  @Provides
  fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder().baseUrl("https://api.vimeo.com/")
      .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
  }

  @Provides
  fun vimeoApiService(vimeoRetrofit: Retrofit): VimeoApiService {
    return vimeoRetrofit.create(VimeoApiService::class.java)
  }

}
