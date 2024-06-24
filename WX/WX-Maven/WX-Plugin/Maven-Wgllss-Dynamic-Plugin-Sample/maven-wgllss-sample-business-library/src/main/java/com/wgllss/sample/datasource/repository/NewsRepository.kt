package com.wgllss.sample.datasource.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wgllss.sample.data.NewsBean
import com.wgllss.sample.datasource.net.MyApi
import com.wgllss.sample.datasource.net.RetrofitUtils
import com.wgllss.sample.feature_system.room.CollectDataBase
import com.wgllss.sample.feature_system.room.help.RoomDBMigration
import com.wgllss.sample.feature_system.room.table.CollectTableBean
import com.wgllss.sample.feature_system.savestatus.MMKVHelp
import kotlinx.coroutines.flow.flow
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
        if (start == 0 && "BAI6I0O5wangning" == path) {
            MMKVHelp.saveHomeTab1Data(str)
        }
    }

    suspend fun getNewsDetailInfo(id: String, fileName: String) = flow {
        val dir = "down_dir"
        val file = File(StringBuilder(context.filesDir.absolutePath).append(File.separator).append(dir).append(File.separator).append(fileName).toString())
        if (!file.exists()) {
            File(file.parent).takeUnless { it.exists() }?.run { mkdirs() }
            val html = apiL.getNewsDetailInfo("https://3g.163.com/all/article/${id}.html#offset=1")
            val document = Jsoup.parse(html, "https://3g.163.com/")
            document.select("script").remove()
            document.select(".main-openApp").remove()
            document.select(".operate").remove()
            document.select(".js-open-app").remove()
            document.select(".comment").remove()
            document.select(".recommend").remove()
            document.select("head")?.append("<script type=\"text/javascript\" src=\"../js/jquery.js\"></script>")
            document.select("head")?.append("<script type=\"text/javascript\" src=\"../js/jquery.lazyload.js\"></script>")
            document.select("head")?.append("<script> function loadImage(){setTimeout(function (){\$(\"img.lazy\").lazyload();window.scrollTo('0','1');window.scrollTo('1','0');}, 300);}</script>")

            val lint = document.select("link")
            lint?.forEach {
                val link = it?.attr("abs:href")
                if (link != null && link!!.contains("0ccc5aad.js")) {
                    it.remove()
                }
            }

            val figure = document.select("figure")
            figure?.forEach {
                it.select("div")?.takeIf { d ->
                    d.size > 1
                }?.get(1)?.remove()
            }
            val nav = document.select("header")
            nav?.remove()

            val newHtml = document.html()
                .replace("image-lazy image-error", "lazy")
                .replace("data-src", "data-original")
                .replace("image-lazy image-reload", "lazy")

            val inputStream = newHtml.byteInputStream()
            if (inputStream != null) {
                val sinkBuffer = file.sink().buffer()
                val bufferedSource = inputStream.source().buffer()
                sinkBuffer.write(bufferedSource.readByteArray())
                sinkBuffer.close()
                bufferedSource.close()
                inputStream.close()
            }
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