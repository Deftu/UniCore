package xyz.unifycraft.unicore.api.utils.http

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * UniCore's HTTP utilities.
 */
interface HttpRequester {
    val httpClient: OkHttpClient
    /**
     * Makes a request using the
     * request provided using
     * OkHttp and handles the
     * response wit the callback.
     *
     * @param request The request to make.
     * @param block The callback to handle the response.
     * @return The response.
     */
    fun <T> request(request: Request, block: (Response) -> T): T
}