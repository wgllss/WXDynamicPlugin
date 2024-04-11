package com.wgllss.core.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wgllss.core.viewmodel.BaseViewModel

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
abstract class BaseViewModelClassActivity : BaseActivity() {

    val viewModel by lazy { ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(getViewModelClass()) }

    override fun bindEvent() {
        viewModel?.run {
            showUIDialog.observe(this@BaseViewModelClassActivity, Observer { it ->
                if (it.isShow) showloading(it.msg) else hideLoading()
            })
            errorMsgLiveData.observe(this@BaseViewModelClassActivity, Observer {
                onToast(it)
            })
        }
    }

    override fun initValue() {
    }

    open abstract fun getViewModelClass(): Class<out BaseViewModel>

}