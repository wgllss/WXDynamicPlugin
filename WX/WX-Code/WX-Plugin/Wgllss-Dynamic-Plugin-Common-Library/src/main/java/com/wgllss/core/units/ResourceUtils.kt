package com.wgllss.core.units

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton

object ResourceUtils {

    fun setContentLayout(activity: Activity, resourcesPlugin: Resources, layoutName: String, packageNamePlugin: String) {
        val layoutID = resourcesPlugin.getIdentifier(layoutName, "layout", packageNamePlugin)
        val view = LayoutInflater.from(activity).inflate(resourcesPlugin.getLayout(layoutID), activity.window.decorView as ViewGroup, false)
        activity.setContentView(view)
    }

    fun getPluginView(context: Context, resourcesPlugin: Resources, resName: String, packageNamePlugin: String): View {
        val layoutID = resourcesPlugin.getIdentifier(resName, "layout", packageNamePlugin)
        return LayoutInflater.from(context).inflate(resourcesPlugin.getLayout(layoutID), null, false)
    }

    fun getPluginID(resourcesPlugin: Resources, resName: String, packageNamePlugin: String) = resourcesPlugin.getIdentifier(resName, "id", packageNamePlugin)

    fun getPluginDrawable(resourcesPlugin: Resources, resName: String, packageNamePlugin: String): Drawable = resourcesPlugin.getDrawable(resourcesPlugin.getIdentifier(resName, "drawable", packageNamePlugin))

    fun getPluginColor(resourcesPlugin: Resources, resName: String, packageNamePlugin: String) = resourcesPlugin.getColor(resourcesPlugin.getIdentifier(resName, "color", packageNamePlugin))

    fun getPluginDimen(resourcesPlugin: Resources, resName: String, packageNamePlugin: String) = resourcesPlugin.getDimension(resourcesPlugin.getIdentifier(resName, "dimen", packageNamePlugin))

    fun getPluginString(resourcesPlugin: Resources, resName: String, packageNamePlugin: String) = resourcesPlugin.getString(resourcesPlugin.getIdentifier(resName, "string", packageNamePlugin))

    fun getColorStatusList(resourcesPlugin: Resources, resName: String, packageNamePlugin: String) = resourcesPlugin.getColorStateList(resourcesPlugin.getIdentifier(resName, "drawable", packageNamePlugin))

    fun setBackgroundColor(resourcesPlugin: Resources, resName: String, packageNamePlugin: String, vararg view: View) {
        view.forEach {
            it.setBackgroundColor(getPluginColor(resourcesPlugin, resName, packageNamePlugin))
        }
    }

    fun setBackgroundDrawable(resourcesPlugin: Resources, resName: String, packageNamePlugin: String, vararg view: View) {
        view.forEach {
            it.setBackgroundDrawable(getPluginDrawable(resourcesPlugin, resName, packageNamePlugin))
        }
    }

    fun setImageDrawable(resourcesPlugin: Resources, resName: String, packageNamePlugin: String, vararg view: ImageView) {
        view.forEach {
            it.setImageDrawable(getPluginDrawable(resourcesPlugin, resName, packageNamePlugin))
        }
    }

    fun setTextColor(resourcesPlugin: Resources, resName: String, packageNamePlugin: String, vararg view: TextView) {
        view.forEach {
            it.setTextColor(getPluginColor(resourcesPlugin, resName, packageNamePlugin))
        }
    }

    fun setTextColorHint(resourcesPlugin: Resources, resName: String, packageNamePlugin: String, vararg view: EditText) {
        view.forEach {
            it.setHintTextColor(getPluginColor(resourcesPlugin, resName, packageNamePlugin))
        }
    }

    fun setText(resourcesPlugin: Resources, resName: String, packageNamePlugin: String, vararg view: TextView) {
        view.forEach {
            it.text = getPluginString(resourcesPlugin, resName, packageNamePlugin)
        }
    }

    fun setTextHint(resourcesPlugin: Resources, resName: String, packageNamePlugin: String, vararg view: TextView) {
        view.forEach {
            it.hint = getPluginString(resourcesPlugin, resName, packageNamePlugin)
        }
    }

    fun setMaterialButtonBackgroundHint(resourcesPlugin: Resources, resName1: String, resName2: String, packageNamePlugin: String, vararg view: MaterialButton) {
        val colors = intArrayOf(getPluginColor(resourcesPlugin, resName1, packageNamePlugin), getPluginColor(resourcesPlugin, resName2, packageNamePlugin))
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_pressed)
        states[1] = intArrayOf(android.R.attr.state_enabled)
        view.forEach {
            it.backgroundTintList = ColorStateList(states, colors)
        }
    }

    fun setMaterialButtonBackgroundHint(colorStrPressed: String, colorStrEnable: String, vararg view: MaterialButton) {
        val colors = intArrayOf(Color.parseColor(colorStrPressed), Color.parseColor(colorStrEnable))
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_pressed)
        states[1] = intArrayOf(android.R.attr.state_enabled)
        view.forEach {
            it.backgroundTintList = ColorStateList(states, colors)
        }
    }
}