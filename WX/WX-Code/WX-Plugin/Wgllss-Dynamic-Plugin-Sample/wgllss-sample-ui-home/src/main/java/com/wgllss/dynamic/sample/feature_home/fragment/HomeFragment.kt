package com.wgllss.dynamic.sample.feature_home.fragment

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.ex.initColors
import com.wgllss.core.ex.parseErrorString
import com.wgllss.core.fragment.BaseViewModelFragment
import com.wgllss.core.units.WLog
import com.wgllss.core.widget.DividerGridItemDecoration
import com.wgllss.core.widget.OnRecyclerViewItemClickListener
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.dynamic.plugin.manager.PluginResource
import com.wgllss.dynamic.sample.feature_home.adapter.HomeNewsAdapter
import com.wgllss.dynamic.sample.feature_home.pkg.ResourceContains
import com.wgllss.dynamic.sample.feature_home.viewmodels.HomeTabViewModel
import com.wgllss.dynamic.sample.feature_startup.startup.HomeContains
import com.wgllss.dynamic.sample.feature_startup.startup.LaunchInflateKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseViewModelFragment<HomeTabViewModel>(ResourceContains.packageName) {

    var title: String = ""
    private var key: String = ""

    companion object {
        private const val TITLE_KEY = "TITLE_KEY"
        private const val KEY = "KEY"

        fun newInstance(titleS: String, keyS: String): HomeFragment {
            val fragment = HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE_KEY, titleS)
                    putString(KEY, keyS)
                }
                title = titleS
            }
            return fragment
        }
    }

    override fun getPluginResources(): Resources? = null

    override fun getSkinResources() = PluginResource.getSkinResources()

//    private val homeTabViewModel by lazy { viewModels<HomeTabViewModel>().value }

    private lateinit var rvPlList: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var homeNewsAdapter: HomeNewsAdapter

//    override fun activitySameViewModel() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(TITLE_KEY, "") ?: ""
        key = arguments?.getString(KEY, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!this::swipeRefreshLayout.isInitialized) {
            if ("BBM54PGAwangning" == key) {
                runBlocking {
                    swipeRefreshLayout = HomeContains.getViewByKey(inflater.context, LaunchInflateKey.home_fragment)!! as SwipeRefreshLayout
                    rvPlList = swipeRefreshLayout.findViewById(getSkinPluginID("home_recycle_view"))
                }
            } else {
                swipeRefreshLayout = SwipeRefreshLayout(inflater.context).apply {
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    initColors()
                }
                rvPlList = RecyclerView(inflater.context).apply {
                    val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    lp.gravity = Gravity.TOP and Gravity.LEFT
                    layoutParams = lp
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    val itemDecoration = View(context)
                    val size = context.getIntToDip(1.0f).toInt()
                    itemDecoration.layoutParams = ViewGroup.LayoutParams(size, size)
                    itemDecoration.setBackgroundColor(Color.parseColor("#60000000"))
                    addItemDecoration(DividerGridItemDecoration(context, GridLayoutManager.VERTICAL, itemDecoration))
                }
                swipeRefreshLayout.addView(rvPlList)
            }
        }
        return swipeRefreshLayout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.reset(key)
            viewModel.getData(key)
        }
        rvPlList?.run {
            addOnItemTouchListener(object : OnRecyclerViewItemClickListener(this) {
                override fun onItemClickListener(itemRootView: View, position: Int) {
                    activity?.run {
                        /** 方案一写法 other2 首次下载写法 start  **/
                        showloading("请稍后...")
                        val status = PluginManager.instance.isLoadSuccessByKey("classes_other2_dex", "classes_other2_res")
                        hideLoading()
                        if (!status) {
                            onToast("缺少插件")
                            return
                        }
                        PluginManager.instance.startPluginSingleTaskActivity(
                            this, "classes_other2_res",
                            "com.wgllss.sample.features_ui.page.other2.activity.WebViewActivity",
                            "com.wgllss.dynamic.sample.other2", Intent().apply {
                                putExtra("web_url_key", homeNewsAdapter.getItem(position).docid)
                                putExtra("title_key", homeNewsAdapter.getItem(position).title)
                                putExtra("docid_key", StringBuilder(homeNewsAdapter.getItem(position).docid).append(".html").toString())
                            }
                        )
                        /** 方案一写法 other2 首次下载写法 end  **/


                        /** 方案二写法 other2 点击时候下载安装 start  **/
//                        lifecycleScope.launch {
//                            PluginManager.instance.dynamicLoadPlugin(this@run, Pair("classes_other2_dex", 1000), Pair("classes_other2_res", 1000)).onStart { showloading("加载中...") }.onCompletion { hideLoading() }.catch {
//                                onToast(it.parseErrorString())
//                            }.collect {
//                                PluginManager.instance.startPluginSingleTaskActivity(
//                                    this@run, "classes_other2_res", "com.wgllss.sample.features_ui.page.other2.activity.WebViewActivity", "com.wgllss.dynamic.sample.other2", Intent().apply {
//                                        putExtra("web_url_key", homeNewsAdapter.getItem(position).docid)
//                                        putExtra("title_key", homeNewsAdapter.getItem(position).title)
//                                        putExtra("docid_key", StringBuilder(homeNewsAdapter.getItem(position).docid).append(".html").toString())
//                                    })
//                            }
//                        }
                        /** 方案二写法 other2 首次下载写法 end  **/
                    }
                }

                override fun onItemLongClickListener(itemRootView: View, position: Int) {
                    viewModel.addToCollection(homeNewsAdapter.getItem(position))
                }
            })
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (viewModel.enableLoadMore(key) && linearLayoutManager!!.itemCount == linearLayoutManager.findLastVisibleItemPosition() + 1) {
                        viewModel.getData(key)
                    }
                }
            })
        }
        if (rvPlList.adapter == null) {
            homeNewsAdapter = HomeNewsAdapter(getSkinResources(), packageName)
            homeNewsAdapter.skinRes = getSkinResources()
            rvPlList.adapter = homeNewsAdapter
        } else {
            homeNewsAdapter = rvPlList.adapter as HomeNewsAdapter
        }
        homeNewsAdapter?.itemCount?.takeIf {
            it > 0
        }?.let {
            viewModel.isLoadOffine = true
        }
        viewModel.getData(key)
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.run {
            initKey(key)
            result[key]?.observe(viewLifecycleOwner) {
                WLog.e(this@HomeFragment, key)
                homeNewsAdapter.notifyData(it, getSkinResources())
                homeNewsAdapter.addFooter()
            }
            enableLoadeMore[key]?.observe(viewLifecycleOwner) {
                if (!it) homeNewsAdapter.removeFooter()
            }
            showUIDialog.observe(viewLifecycleOwner) {
                if (!isLoadOffine) if (!isClick) {
                    swipeRefreshLayout.isRefreshing = it.isShow && !isLoadingMore(key)
                } else {
                    if (it.isShow) showloading(it.msg) else hideLoading()
                }
            }
            errorMsgLiveData.observe(viewLifecycleOwner) {
                onToast(it)
            }
            liveDataLoadSuccessCount.observe(viewLifecycleOwner) {
                if (it > 1) swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}