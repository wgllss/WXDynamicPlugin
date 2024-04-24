package com.wgllss.core.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import com.wgllss.core.units.WLog
import com.wgllss.core.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType
import kotlin.system.measureTimeMillis

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
abstract class BaseMVVMActivity<VM : BaseViewModel, VB : ViewDataBinding>(@LayoutRes private val contentLayoutId: Int) : BaseActivity() {

    protected val viewModel by lazyViewModels()
    protected lateinit var binding: VB


    override fun initControl(savedInstanceState: Bundle?) {
        val time = measureTimeMillis {
            binding = DataBindingUtil.setContentView(this, contentLayoutId)
            binding.lifecycleOwner = this@BaseMVVMActivity
        }
        WLog.e(this, "time:${time}")
    }

    override fun bindEvent() {
        viewModel?.run {
            showUIDialog.observe(this@BaseMVVMActivity, Observer { it ->
                if (it.isShow) showloading(it.msg) else hideLoading()
            })
            errorMsgLiveData.observe(this@BaseMVVMActivity, Observer {
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