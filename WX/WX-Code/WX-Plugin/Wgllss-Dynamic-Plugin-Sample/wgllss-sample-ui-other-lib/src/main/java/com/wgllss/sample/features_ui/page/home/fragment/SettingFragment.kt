package com.wgllss.sample.features_ui.page.home.fragment

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.permissions.PermissionInterceptor
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.widget.DividerGridItemDecoration
import com.wgllss.core.widget.OnRecyclerViewItemClickListener
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.home.adapter.SkinAdapter
import com.wgllss.sample.features_ui.page.home.viewmodels.SettingViewModel

class SettingFragment : BasePluginFragment<SettingViewModel>("fragment_setting") {

    private lateinit var material_switch_settion: SwitchMaterial
    private lateinit var material_notification_switch: SwitchMaterial
    private lateinit var view_title_bar_bg: View
    private lateinit var title: TextView
    private lateinit var material_locker_setting: TextView
    private lateinit var material_notification: TextView
    private lateinit var setting_skin: TextView
    private lateinit var skin_list: RecyclerView
    private val skinAdapter by lazy { SkinAdapter() }

//    private val settingViewModelL = viewModels<SettingViewModel>()

    override fun activitySameViewModel() = false

    override fun findView(context: Context, containerView: View) {
        view_title_bar_bg = findViewByID("view_title_bar_bg")
        title = findViewByID("title")
        material_locker_setting = findViewByID("material_locker_setting")
        setting_skin = findViewByID("setting_skin")
        material_notification = findViewByID("material_notification")
        material_switch_settion = findViewByID("material_switch_settion")
        material_notification_switch = findViewByID("material_notification_switch")
        skin_list = findViewByID("skin_list")
        material_switch_settion.switchMinWidth = context.getIntToDip(60f).toInt()
        material_notification_switch.switchMinWidth = context.getIntToDip(60f).toInt()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.start()
        skin_list.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireActivity(), 5)
            adapter = skinAdapter
            addOnItemTouchListener(object : OnRecyclerViewItemClickListener(this) {
                override fun onItemClickListener(itemRootView: View, position: Int) {
                    skinAdapter.getItem(position)?.let {
                        viewModel.downloadSkin(context, it)
                    }
                }
            })
            val size = context.getIntToDip(10.0f).toInt()
            val itemDecoration = View(context)
            itemDecoration.layoutParams = ViewGroup.LayoutParams(size, size)
            itemDecoration.setBackgroundColor(Color.parseColor("#00000000"))
            addItemDecoration(DividerGridItemDecoration(context, GridLayoutManager.VERTICAL, itemDecoration))
        }
        material_switch_settion.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                viewModel.isOpenLockerUI.value?.takeUnless {
                    it
                }?.let {
                    XXPermissions.with(requireActivity())
                        .permission(Permission.SYSTEM_ALERT_WINDOW)
                        .interceptor(object : PermissionInterceptor() {
                            override fun deniedPermissions(activity: Activity, allPermissions: List<String>, deniedPermissions: List<String>, never: Boolean, callback: OnPermissionCallback?) {
                                super.deniedPermissions(activity, allPermissions, deniedPermissions, never, callback)
                                viewModel.setLockerSwitch(false)
                            }
                        })
                        .request(OnPermissionCallback { permissions, all ->
                            if (!all) {
                                viewModel.setLockerSwitch(false)
                                return@OnPermissionCallback
                            }
                            viewModel.setLockerSwitch(true)
                        })
                }
            } else {
                viewModel.setLockerSwitch(false)
            }
        }
        material_notification_switch.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                viewModel.isNotificationOpen.value?.takeUnless {
                    it
                }?.let {
                    setNotificationPermissions()
                }
            }
        }
        viewModel.isOpenLockerUI.observe(viewLifecycleOwner) {
            material_switch_settion.isChecked = it
        }
        viewModel.isNotificationOpen.observe(viewLifecycleOwner) {
            material_notification_switch.isChecked = it
        }
        setNotificationPermissions()
        viewModel.downloadResult.observe(viewLifecycleOwner) {
            PluginManager.instance.run {
                it.fileAbsolutePath.takeUnless { path ->
                    path == getCurrentSkinPath()
                }?.run {
                    switchSkinResources(this)
                    callAllActivity(getPluginSkinResources())
                }
            }
        }
        viewModel.liveDataSkinList.observe(viewLifecycleOwner) {
            skinAdapter.notifyData(it)
        }
    }

    private fun setNotificationPermissions() {
        XXPermissions.with(requireActivity())
            .permission(Permission.NOTIFICATION_SERVICE)
            .interceptor(object : PermissionInterceptor() {
                override fun deniedPermissions(activity: Activity, allPermissions: List<String>, deniedPermissions: List<String>, never: Boolean, callback: OnPermissionCallback?) {
                    super.deniedPermissions(activity, allPermissions, deniedPermissions, never, callback)
                    viewModel.setNotificationOpen(false)
                }
            })
            .request(OnPermissionCallback { _, all ->
                if (!all) {
                    viewModel.setNotificationOpen(false)
                    return@OnPermissionCallback
                }
                viewModel.setNotificationOpen(true)
            })
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.run {
            setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, view_title_bar_bg)
            setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, title)
            setTextColor(skinRes, "textColorPrimary", SkinContains.packageName, material_locker_setting, material_notification, setting_skin)

            material_switch_settion.thumbTintList = getColorStatusList(skinRes, "switch_thumb_selector", SkinContains.packageName)
            material_switch_settion.trackTintList = getColorStatusList(skinRes, "switch_track_selector", SkinContains.packageName)

            material_notification_switch.thumbTintList = getColorStatusList(skinRes, "switch_thumb_selector", SkinContains.packageName)
            material_notification_switch.trackTintList = getColorStatusList(skinRes, "switch_track_selector", SkinContains.packageName)
        }
    }
}