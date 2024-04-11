package com.wgllss.sample.feature_system.savestatus

import com.tencent.mmkv.MMKV

object MMKVHelp {
    private val mmkv by lazy { MMKV.defaultMMKV() }

    /**
     * 设置桌面歌词开关
     */
    fun setLockerSwitch(isOpen: Boolean) = mmkv.encode("open_locker_ui", isOpen)

    /**
     * 桌面歌词开关是否打开
     */
    fun isOpenLockerUI() = mmkv.decodeBool("open_locker_ui")

    /**
     * 设置播放模式 单曲循环 随机 顺序播放
     */
    fun setPlayMode(playMode: Int) = mmkv.encode("PlayMode", playMode)

    /**
     * 得到播放模式
     */
    fun getPlayMode() = mmkv.decodeInt("PlayMode")

    fun saveHomeTab1Data(data: String) = mmkv.encode("home_fragment_tab1", data)

    fun getHomeTab1Data() = mmkv.decodeString("home_fragment_tab1")

    fun saveSkinPath(path: String) = mmkv.encode("skin_path_key", path)

    fun getSkinPath() = mmkv.decodeString("skin_path_key")

    fun saveJsPath(path: String) = mmkv.encode("webview_js_Key", path)

    fun getJsPath() = mmkv.decodeString("webview_js_Key") ?: ""

    fun saveWebResPath(path: String) = mmkv.encode("web_res_path_key", path)

    fun getWebResPath() = mmkv.decodeString("web_res_path_key")

}