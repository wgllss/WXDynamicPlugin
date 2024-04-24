package com.wgllss.sample.features_ui.page.other2.activity

import android.os.Bundle
import com.wgllss.core.ex.setFramgment
import com.wgllss.sample.features_ui.page.base.BasePluginActivity
import com.wgllss.sample.features_ui.page.other2.fragment.SampleMusicFragment
import com.wgllss.sample.features_ui.page.other2.viewmodel.PlayModel

class AudioActivity : BasePluginActivity<PlayModel>("activity_play") {
    private val playFragmentL by lazy { SampleMusicFragment() }

    override fun initControl(savedInstanceState: Bundle?) {
        super.initControl(savedInstanceState)
        activity.setFramgment(playFragmentL, getPluginID("content"))
        window.setBackgroundDrawable(null)
    }

    override fun lazyInitValue() {
        viewModel.run {
            start()
            rootMediaId.observe(activity) {
                it?.let {
                    subscribeByMediaID(it)
                    playMp3()
                }
            }
        }
    }
}