package com.wgllss.core.activity

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

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
abstract class BaseViewModePluginActivity<VM : BaseViewModel>(protected val layoutName: String, packageNamePlugin: String) : BaseViewPluginResActivity(packageNamePlugin) {

    protected val viewModel by lazyViewModels()

    override fun initControl(savedInstanceState: Bundle?) {
        ResourceUtils.setContentLayout(this, resourcesPlugin, layoutName, packageNamePlugin)
    }

    override fun bindEvent() {
        viewModel?.run {
            showUIDialog.observe(this@BaseViewModePluginActivity, Observer { it ->
                if (it.isShow) showloading(it.msg) else hideLoading()
            })
            errorMsgLiveData.observe(this@BaseViewModePluginActivity, Observer {
                onToast(it)
            })
        }
    }

    override fun initValue() {
    }


    @MainThread
    inline fun lazyViewModels(): Lazy<VM> {
        val cls = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        return ViewModelLazy(cls.kotlin, { viewModelStore }, { defaultViewModelProviderFactory })
    }
}