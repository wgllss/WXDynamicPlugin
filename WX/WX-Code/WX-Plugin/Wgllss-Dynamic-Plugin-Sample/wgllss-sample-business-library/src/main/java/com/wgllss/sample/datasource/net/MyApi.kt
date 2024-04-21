package com.wgllss.sample.datasource.net

import retrofit2.http.*

/**
 * http://3g.163.com/touch/reconstruct/article/list/BA10TA81wangning/10-20.html?size=0&hasad=10
 *
 * http://3g.163.com/touch/reconstruct/article/list/BBM54PGAwangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BA10TA81wangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BA8E6OEOwangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BA8F6ICNwangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BA8EE5GMwangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BA8DOPCSwangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BAI67OGGwangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BA8D4A3Rwangning/0-10.html?size=0&hasad=0
 * http://3g.163.com/touch/reconstruct/article/list/BAI6I0O5wangning/0-10.html?size=0&hasad=0
 */
interface MyApi {

    @GET("http://3g.163.com/touch/reconstruct/article/list/{path}/{start}-{end}.html?")
    suspend fun getNetTabInfo(@Path("path") path: String, @Path("start") start: String, @Path("end") end: String, @Query("hasad") hasad: Int, @Query("size") size: Int = 0): String

    @GET
    suspend fun getNewsDetailInfo(@Url url: String, @Header("User-Agent") user_Agent: String = "Mozilla/5.0 (iPhone; CPU iPhone OS 14_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.3 Mobile/15E148 Safari/604.1"): String
}