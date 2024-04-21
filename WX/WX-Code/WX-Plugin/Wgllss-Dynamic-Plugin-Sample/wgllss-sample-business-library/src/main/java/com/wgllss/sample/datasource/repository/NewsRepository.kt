package com.wgllss.sample.datasource.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wgllss.core.units.WLog
import com.wgllss.sample.data.NewsBean
import com.wgllss.sample.datasource.net.MyApi
import com.wgllss.sample.datasource.net.RetrofitUtils
import com.wgllss.sample.feature_system.room.CollectDataBase
import com.wgllss.sample.feature_system.room.help.RoomDBMigration
import com.wgllss.sample.feature_system.room.table.CollectTableBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.buffer
import okio.sink
import okio.source
import org.jsoup.Jsoup
import java.io.File

class NewsRepository private constructor(private val context: Context) {

    private val apiL by lazy { RetrofitUtils.getInstance(context).create(MyApi::class.java) }
    private val collectDataBaseL by lazy { CollectDataBase.getInstance(context, RoomDBMigration.instance) }

    companion object {

        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NewsRepository(context).also { instance = it }
        }
    }

    suspend fun getNetTabInfo(path: String, start: Int, end: Int) = flow {
        val str = apiL.getNetTabInfo(path, start.toString(), end.toString(), start).let {
            it.substring(9, it.length - 1)
        }
        val map = Gson().fromJson<MutableMap<String, MutableList<NewsBean>>>(str, object : TypeToken<Map<String, MutableList<NewsBean>>>() {}.type)
        emit(map[path]!!)
    }

    suspend fun getNewsDetailInfo(url: String) = flow {
        val html = apiL.getNewsDetailInfo(url)
        val document = Jsoup.parse(html, "https://m.163.com/")
        val script = document.select("script")
        script?.remove()
        val wyxwapp = document.select(".s-tip js-open-app")
        wyxwapp?.remove()
        val nav = document.select("nav")
        nav?.remove()
        val newHtml = document.html()

        val dir = "down_dir"
        val fileName = "a1234234.html"
        val file = File(StringBuilder(context.filesDir.absolutePath).append(File.separator).append(dir).append(File.separator).append(fileName).toString())
        File(file.parent).takeUnless { it.exists() }?.run { mkdirs() }
        val inputStream = newHtml.byteInputStream()
        if (inputStream != null) {
            val sinkBuffer = file.sink().buffer()
            val bufferedSource = inputStream.source().buffer()
            sinkBuffer.write(bufferedSource.readByteArray())
            sinkBuffer.close()
            bufferedSource.close()
            inputStream.close()
        }
        emit(StringBuilder().append("file:///").append(file.absolutePath).toString())
    }

    suspend fun addToCollection(it: NewsBean) = flow {
        it.run {
            val count = collectDataBaseL.collectDao().queryByUUID(id)
            if (count > 0) {
                emit("已经收藏过")
            } else {
                collectDataBaseL.collectDao().insertCollectBean(CollectTableBean(id, title, imgsrc, System.currentTimeMillis()))
                emit("收藏成功")
            }
        }
    }

    suspend fun getAllList() = flow {
        emit(collectDataBaseL.collectDao().getList())
    }

    suspend fun deleteFromId(id: Long) = flow {
        collectDataBaseL.collectDao().deleteFromID(id)
        emit(0)
    }
}