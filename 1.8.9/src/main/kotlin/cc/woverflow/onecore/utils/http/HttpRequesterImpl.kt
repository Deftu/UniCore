package cc.woverflow.onecore.utils.http

import cc.woverflow.onecore.api.OneCore
import cc.woverflow.onecore.api.utils.http.HttpRequester
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class HttpRequesterImpl : HttpRequester {
    override val httpClient = OkHttpClient.Builder()
        .addInterceptor { it.proceed(it.request().newBuilder().header("User-Agent", "Mozilla 4.76 (${OneCore.getName()}/${OneCore.getVersion()})").build()) }
        .build()
    override fun <T> request(request: Request, block: (Response) -> T): T {
        val response = httpClient.newCall(request).execute()
        val value = block.invoke(response)
        response.close()
        return value
    }
}