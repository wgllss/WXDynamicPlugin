package com.wgllss.sample.features_ui.page.other2.fragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.wgllss.core.units.ResourceUtils
import com.wgllss.dynamic.host.lib.provider.ProviderAuthority
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel

class SampleContentProviderFragment : BasePluginFragment<SampleActivityViewModel>("fragment_provider_sample"), View.OnClickListener {
    private lateinit var btn_query: TextView
    private lateinit var btn_del: TextView
    private lateinit var btn_update: TextView
    private lateinit var btn_insert: TextView
    private lateinit var txt_query_result: TextView

    private val content_authority: String = StringBuilder("com.wgllss.dynamic.host.sample").append(ProviderAuthority.authority).toString()

    private val base_content_uri = Uri.parse("content://$content_authority")

    private val uri = base_content_uri.buildUpon().appendPath("com.wgllss.dynamic.provider.TestContentProvider").build()

    override fun findView(context: Context, containerView: View) {
        btn_query = findViewByID("btn_query")
        btn_del = findViewByID("btn_del")
        btn_update = findViewByID("btn_update")
        btn_insert = findViewByID("btn_insert")
        txt_query_result = findViewByID("txt_query_result")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_query.setOnClickListener(this)
        btn_del.setOnClickListener(this)
        btn_update.setOnClickListener(this)
        btn_insert.setOnClickListener(this)
        activity?.run {
            contentResolver.registerContentObserver(uri, true, object : ContentObserver(Handler()) {
                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    super.onChange(selfChange, uri)
                    Toast.makeText(this@run, uri.toString() + " onChange", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    @SuppressLint("Range")
    override fun onClick(v: View) {
        when (v) {
            btn_query -> {
                activity?.run {
                    val sb = StringBuilder()
                    //可以 把 selection 当 sql 语句使用
                    val cursor = contentResolver.query(uri, null, "select * from collect_tab", null, null)
                    val count = cursor?.count ?: 0
                    if (count > 0) {
                        while (cursor?.moveToNext() == true) {
                            val name = cursor.getString(cursor.getColumnIndex("title"))
                            sb.append(name).append("\n")
                        }
                    }
                    txt_query_result.text = sb.toString()
                    cursor?.close()
                }
            }
            btn_del -> {
                activity?.run {
                    //测试 一下把 id >0 的全部删除
                    contentResolver.delete(uri, "id > ?", arrayOf("0"))
                    onClick(btn_query)
                }
            }
            btn_update -> {
                activity?.run {
                    //可以 把 selection 当 sql 语句使用
                    val cursor = contentResolver.query(uri, null, "select * from collect_tab", null, null)
                    val count = cursor?.count ?: 0
                    if (count > 0 && cursor?.moveToNext() == true) {
                        //moveToNext 这里测试 只修改一个
                        val contentValues = ContentValues()
                        contentValues.put("title", "AAAAAAAAAAAAAAAA")
                        val id = cursor.getLong(cursor.getColumnIndex("id"))
                        contentResolver.update(uri, contentValues, "id = ?", arrayOf(id.toString()))
                    }
                    cursor?.close()
                    onClick(btn_query)
                }
            }
            btn_insert -> {
                activity?.run {
                    val contentValues = ContentValues()
                    contentValues.put("id", 1232L)
                    contentValues.put("title", "这是一个添加的title")
                    contentValues.put("imgUrl", "https://img0.baidu.com/it/u=1640392721,3287967405&fm=253&fmt=auto&app=138&f=JPEG")
                    contentValues.put("createTime", System.currentTimeMillis())
                    contentResolver.insert(uri, contentValues)
                    onClick(btn_query)
                }
            }
            else -> {

            }
        }
    }


    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_query, btn_del, btn_update, btn_insert)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_query, btn_del, btn_update, btn_insert)
    }
}