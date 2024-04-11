package com.wgllss.core.activity

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.wgllss.core.fragment.BasePluginResFragment
import com.wgllss.core.units.ResourceUtils

/**
 * contentLayoutId 不传给父类，否则 binding = DataBindingUtil.setContentView(this, contentLayoutId) 设置一次
 * 父类里面ComponentActivity还要设置一次 共2次 见下
 *
 *
 * @Override
 * protected void onCreate(@Nullable Bundle savedInstanceState) {
 *     // Restore the Saved State first so that it is available to
 *     // OnContextAvailableListener instances
 *    mSavedStateRegistryController.performRestore(savedInstanceState);
 *    mContextAwareHelper.dispatchOnContextAvailable(this);
 *    super.onCreate(savedInstanceState);
 *    mActivityResultRegistry.onRestoreInstanceState(savedInstanceState);
 *    ReportFragment.injectIfNeededIn(this);
 *    if (mContentLayoutId != 0) {
 *      setContentView(mContentLayoutId);
 *    }
 *  }
 */
abstract class BaseViewPluginResActivity(protected val packageNamePlugin: String) : BaseActivity() {

    protected lateinit var resourcesPlugin: Resources

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        getPluginResources()?.let {
            resourcesPlugin = it
        }
    }

    override fun onStart() {
        super.onStart()
        onChangeSkin(getSkinResources())
    }

    abstract fun getSkinResources(): Resources

    abstract fun getPluginResources(): Resources?

    protected fun getPluginDrawable(resName: String): Drawable = ResourceUtils.getPluginDrawable(resourcesPlugin, resName, packageNamePlugin)

    protected fun getPluginDrawable(skinRes: Resources, resName: String, skinPackageName: String): Drawable = ResourceUtils.getPluginDrawable(skinRes, resName, skinPackageName)

    protected fun getPluginID(resName: String) = ResourceUtils.getPluginID(resourcesPlugin, resName, packageNamePlugin)

    protected fun getPluginID(skinRes: Resources, resName: String, skinPackageName: String) = ResourceUtils.getPluginID(skinRes, resName, skinPackageName)

    open fun onChangeSkin(skinRes: Resources) {

    }

    open fun callChangeSkin(skinRes: Resources) {
        onChangeSkin(skinRes)
        supportFragmentManager.fragments.forEach {
            if (it.isAdded && it is BasePluginResFragment) {
                it.callChangeSkin(skinRes)
            }
        }
    }
}