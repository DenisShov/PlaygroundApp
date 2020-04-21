package com.dshovhenia.playgroundapp.data.remote.authentication

import android.util.Base64
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import com.dshovhenia.playgroundapp.BuildConfig
import com.dshovhenia.playgroundapp.data.cache.preferences.model.AccessToken
import com.dshovhenia.playgroundapp.data.cache.preferences.PreferencesHelper
import com.dshovhenia.playgroundapp.data.remote.service.VimeoApiService
import javax.inject.Inject

class VimeoServiceAuthenticator @Inject constructor(
  // Lazy computes its value on the first call to get() and remembers that same value for all subsequent calls to get().
  val mVimeoApiService: Lazy<VimeoApiService>, val mPrefHelper: PreferencesHelper
) : Authenticator {

  private var newAccessToken: AccessToken? = null

  override fun authenticate(route: Route?, response: Response): Request? {
    Timber.i("calling authenticator - responseCount: %s", responseCount(response))
    // If we’ve failed 2 times, give up.
    if (responseCount(response) >= 2) {
      return null
    }

    // Get the new Unauthenticated access token
    runBlocking(Dispatchers.IO) {
      launch {
        runCatching {
          newAccessToken = mVimeoApiService.get().getUnauthenticatedToken(
            basicAuthorizationHeader(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
          )
        }.onSuccess {
          Timber.i("We hit onSuccess")
        }.onFailure {
          Timber.i("We hit onFailure")
        }
      }
    }

    var newRequest: Request? = null

    if (newAccessToken != null) {
      newRequest = response.request().newBuilder()
        .header("Authorization", newAccessToken!!.authorizationHeader).build()
      // We need to save the newAccessToken in SharedPref
      mPrefHelper.accessToken = newAccessToken!!
    } else {
      Timber.e("Failed to get Unauthenticated token")
    }
    return newRequest
  }

  private fun responseCount(response: Response?): Int {
    var res = response
    var result = 1
    while ((res?.priorResponse().also { res = it }) != null) {
      result++
    }
    return result
  }

  private fun basicAuthorizationHeader(clientId: String, clientSecret: String): String {
    val rawString = "$clientId:$clientSecret"
    return "basic " + Base64.encodeToString(rawString.toByteArray(), Base64.NO_WRAP)
  }

}