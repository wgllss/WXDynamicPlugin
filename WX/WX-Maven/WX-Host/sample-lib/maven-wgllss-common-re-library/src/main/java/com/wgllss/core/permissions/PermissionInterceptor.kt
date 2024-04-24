package com.wgllss.core.permissions

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import com.hjq.permissions.*
import com.wgllss.core.re.R
import com.wgllss.core.widget.CommonToast

open class PermissionInterceptor : IPermissionInterceptor {

    override fun grantedPermissions(activity: Activity?, allPermissions: List<String?>?, grantedPermissions: List<String?>?, all: Boolean, callback: OnPermissionCallback?) {
        if (callback == null) {
            return
        }
        callback.onGranted(grantedPermissions, all)
    }

    override fun deniedPermissions(activity: Activity, allPermissions: List<String>, deniedPermissions: List<String>, never: Boolean, callback: OnPermissionCallback?) {
        callback?.onDenied(deniedPermissions, never)
        if (never) {
            if (deniedPermissions.size == 1 && Permission.ACCESS_MEDIA_LOCATION == deniedPermissions[0]) {
                CommonToast.show(R.string.common_permission_media_location_hint_fail)
                return
            }
            showPermissionSettingDialog(activity, allPermissions, deniedPermissions, callback)
            return
        }
        if (deniedPermissions.size == 1) {
            val deniedPermission = deniedPermissions[0]
            if (Permission.ACCESS_BACKGROUND_LOCATION == deniedPermission) {
                CommonToast.show(R.string.common_permission_background_location_fail_hint)
                return
            }
            if (Permission.BODY_SENSORS_BACKGROUND == deniedPermission) {
                CommonToast.show(R.string.common_permission_background_sensors_fail_hint)
                return
            }
        }
        val message: String
        val permissionNames: List<String> = PermissionNameConvert.permissionsToNames(activity, deniedPermissions)
        message = if (!permissionNames.isEmpty()) {
            activity.getString(R.string.common_permission_fail_assign_hint, PermissionNameConvert.listToString(permissionNames))
        } else {
            activity.getString(R.string.common_permission_fail_hint)
        }
        CommonToast.show(message)
    }

    /**
     * 显示授权对话框
     */
    private fun showPermissionSettingDialog(
        activity: Activity?, allPermissions: List<String>,
        deniedPermissions: List<String>, callback: OnPermissionCallback?
    ) {
        if (activity == null || activity.isFinishing ||
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed
        ) {
            return
        }
        val message: String
        val permissionNames: List<String> = PermissionNameConvert.permissionsToNames(activity, deniedPermissions)
        message = if (!permissionNames.isEmpty()) {
            activity.getString(R.string.common_permission_manual_assign_fail_hint, PermissionNameConvert.listToString(permissionNames))
        } else {
            activity.getString(R.string.common_permission_manual_fail_hint)
        }

        // 这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
        AlertDialog.Builder(activity)
            .setTitle(R.string.common_permission_alert)
            .setMessage(message)
            .setPositiveButton(R.string.common_permission_goto_setting_page, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                XXPermissions.startPermissionActivity(activity,
                    deniedPermissions, object : OnPermissionPageCallback {
                        override fun onGranted() {
                            if (callback == null) {
                                return
                            }
                            callback.onGranted(allPermissions, true)
                        }

                        override fun onDenied() {
                            showPermissionSettingDialog(
                                activity, allPermissions,
                                XXPermissions.getDenied(activity, allPermissions), callback
                            )
                        }
                    })
            })
            .show()
    }
}