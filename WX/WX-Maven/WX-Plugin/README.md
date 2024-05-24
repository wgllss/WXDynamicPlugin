# WXDynamicPlugin 全动态化框架 接入详细指南

#### 特别注意：前期没完全明白前先，先按照下面的工程目录设计方式接入，等自己接入成功后，完整接入后再考虑，建议优化都可以，或许会明白为什么这样设计，同时确保已经看完了 [插件内部详细介绍](https://gitee.com/wgllss888/WXDynamicPlugin/blob/master/WX/WX-Maven/WX-Plugin/Maven-Wgllss-Dynamic-Plugin-Sample)    

### 一、接入Host设计（参照示例工程）
 <img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/host.jpeg" width="1014" height="364"/>    
  
1. host总共设计成5个工程，其中1个宿主sample工程 4个宿主工程依赖的sammple-lib工程,
2. host中主工程sample 依赖内容也先按照我的依赖设计 ，依赖4个我的动态化框架（Constant-Lib，Download-Lib，Lib-Impl,Version-Lib），4个设计的sample-lib 工程，4个ui需要的（appcompat，recyclerview，material，constraintlayout）,主工程sample必须要有4个文件（FaceImpl，VersionImpl，SampleApplication，assets下loading_1000）
3. sample-lib/common-re 工程中 ，到公共代码带res资源的模块lib，同时必须添加以下依赖    
    `implementation 'androidx.activity:activity-ktx:1.3.0-rc01'`  
    `implementation 'androidx.fragment:fragment-ktx:1.4.0-alpha04'`  
    `implementation "androidx.core:core-ktx:$rootProject.ext.kotlin_version"`  
 因为我全动态化框架是用kotlin +协程写的，上面三个必须要有  
    `implementation 'com.github.bumptech.glide:glide:4.13.2'` 图片加载    
    `androidx.startup:startup-runtime:1.1.1` 启动框架 ，我框架里面用到了，我放到了 business-re-lib里面去了 
4. sample-lib/business-re-lib 工程添加哪些依赖？
   自己app自己已经已知了的依赖框架： 总要有网络请求框架吧，retrofit,okhttp,或者权限框架吧，比如自己做音乐类app,视频类app,可以把想用的音视频sdk ，或者其他第三方框框架第一次就接入,我建议： 相关网络比如retrofit,okhttp,jetpack全套库，图片加载库，音视频库都可以在此lib工程下添加依赖进去，什么？前面不我是提出了，宿主就一个空壳子吗？因为这些基本万年不修改，没有必要做成插件，浪费下载的体积和时间，加载插件性能，要强行把宿主做成空壳子也行，那就需要自己单独特殊处理这些框架，感觉这部分又是万年不动，不要浪费太多精力特殊处理
5. sample-lib/maven-wgllss-dynamic-host-library  工程主要统一依赖, 并注册宿主必要的 androidManifest里面activity4中启动模式，service ，和首页HomeActivity
6. sample-lib/maven-wgllss-dynamic-host-skin-resource-lib 宿主工程必要的 资源，比如桌面icon,注册到androidManifest下需要的主题等，还有第一次展示loading页时下载插件时展示的图片等
7. sample-lib/下四个是lib工程，理论上宿主就总共5个工程，向外展示的代码就这么多，插件工程是可以依赖宿主下这4个lib工程的，我示例下也有，比如，宿主application 下 设置秤全局 application的context 在 common-re-lib工程中的AppGlobals，插件是可以直接依赖该 common-re-lib 使用的
### 二、插件接入设计
1. 前面介绍过，我框架设计的插件基本思路：   
14个插件工程 （其中maven-wgllss-sample-loader-version 工程打包成 2个工程 ：一个classes_version_dex，一个vc文件）    
4个用于方便打包的工程（Maven-Wgllss-Dynamic-Plugin-Generate下面）  
2个用于动态更新宿主里面逻辑的：  
&emsp;&emsp;1.1：动态实现更换下载插件地址，文件，以及debug 等 (Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl) 宿主默认有一份， 可以不用  
&emsp;&emsp;1.2：动态实现根据版本下载插件，加载插件 (Maven-Wgllss-Dynamic-Plugin-Loader-Impl) 宿主默认有一份，可以不用）   
2. 哪些插件工程程是必须？ 
 可对照[插件内部详细介绍](https://gitee.com/wgllss888/WXDynamicPlugin/blob/master/WX/WX-Maven/WX-Plugin/Maven-Wgllss-Dynamic-Plugin-Sample)   内相对应的模块详细说明研究   

| 工程名                                      | 介绍          | 是否必须 |
|--------------------------------------------|-------------|------|
| Maven-Wgllss-Dynamic-Plugin-Common-Library | 公共代码库插件模块   | 是    |
| maven-wgllss-sample-business-library       | 公共业务代码库插件模块 | 是    |
| maven-wgllss-sample-assets-source-apk       | Assets资源插件模块 | 如果没有可不用    |
| maven-wgllss-sample-skin-resource-apk       | 皮肤插件模块 | 是    |
| maven-wgllss-sample-ui-loading       | 启动页插件模块 | 是    |
| maven-wgllss-sample-ui-home       | 首页插件模块 | 是    |
| maven-wgllss-sample-ui-other-lib       | 首页之外fragment插件模块 | 如果没有可不用    |
| maven-wgllss-sample-ui-other       | 首页之外fragment布局资源插件模块 | 如果没有可不用    |
| maven-wgllss-sample-ui-other2-lib2       | 首页之外的Activity插件模块 | 如果没有可不用    |
| maven-wgllss-sample-ui-other2       | 首页之外的Activity插件模块布局资源 | 如果没有可不用    |
| maven-wgllss-sample-loader-version       | 各个插件版本模块 | 是    |
| Maven-Wgllss-Dynamic-Plugin-SDK下面2个工程       | 真正插件框架SDK代码  | 是    |
| Maven-Wgllss-Dynamic-Plugin-Generate下4个工程       | 辅助打包 | 是    |
| Maven-Wgllss-Dynamic-Plugin-Manager       | 管理插件中 activity跳转,service 启动绑定 ，皮肤，资源等 | 是    |
| Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl       | 动态实现更换下载插件地址，文件，以及debug 等 | 可不用    |
| Maven-Wgllss-Dynamic-Plugin-Loader-Impl       | 动态实现根据版本下载插件，加载插件 | 可不用    |


3. 配置里面哪些能改哪些不能改？  
&emsp;&emsp;3.1宿主中VersionImpl对应 升级插件时修改maven-wgllss-sample-loader-version工程里 LoaderVersionImpl  ，不动宿主  
&emsp;&emsp;3.2宿主中FaceImpl对于升级插件时 修改Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl工程里DownLoadFaceImpl ，不动宿主  
&emsp;&emsp;3.3宿主中SampleApplication内 ` WXDynamicLoader.instance.installPlugin(base, FaceImpl(), VersionImpl())`内部怎么判断下再，  
&emsp;&emsp;&emsp;&emsp;什么时候下载，什么时候加载如果逻辑需要修改，则修改Maven-Wgllss-Dynamic-Plugin-Loader-Impl内的 LoaderManagerImpl类下方法，  
&emsp;&emsp;&emsp;&emsp;可以自己在该工程下见文件实现自己修改，父类方法可以重载修改，里面内容代码也可以全部copy过来全部自己实现，不动宿主    
&emsp;&emsp;3.4、要添加删除的模块还有哪些不能自定义？  
&emsp;&emsp;&emsp;&emsp;FaceImpl和DownLoadFaceImpl中  getMapDLU的map key不能自定义，如下：

```
 /**
     * 下面要添加删除 map内内容 map的key 不能自定义
     * 即:VERSION,COMMON,WEB_ASSETS,COMMON_BUSINESS,HOME,RESOURCE_SKIN,RUNTIME,MANAGER,FIRST,CLMD,CDLFD不能动
     */
    override fun getMapDLU() = mutableMapOf(
        VERSION to realUrl("classes_version_dex"), // 对应 maven-wgllss-sample-loader-version打包后插件
        COMMON to realUrl("classes_common_lib_dex"), // 对应 Maven-Wgllss-Dynamic-Plugin-Common-Library打包后插件
        WEB_ASSETS to realUrl("classes_business_web_res"), // 对应 maven-wgllss-sample-assets-source-apk打包后插件
        COMMON_BUSINESS to realUrl("classes_business_lib_dex"), // 对应 maven-wgllss-sample-business-library打包后插件
        HOME to realUrl("classes_home_dex"), // 对应 maven-wgllss-sample-ui-home打包后插件
        RESOURCE_SKIN to realUrl("classes_common_skin_res"), // 对应 maven-wgllss-sample-skin-resource-apk打包后插件
        RUNTIME to realUrl("classes_wgllss_dynamic_plugin_runtime"),// 对应 Maven-Wgllss-Dynamic-Plugin-RunTime-Apk打包后插件
        MANAGER to realUrl("classes_manager_dex"), // 对应 Maven-Wgllss-Dynamic-Plugin-Manager打包后插件
        FIRST to realUrl("classes_loading_dex"), // 对应 maven-wgllss-sample-ui-loading打包后插件
        CLMD to realUrl("class_loader_impl_dex"), // 对应 Maven-Wgllss-Dynamic-Plugin-Loader-Impl打包后插件
        CDLFD to realUrl("classes_downloadface_impl_dex") // 对应 Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl打包后插件
    )
```

&emsp;&emsp;&emsp;&emsp;VersionImpl和LoaderVersionImpl中  getMapDLU的map key不能自定义，如下：


```
 /**
     * 下面要添加删除 map内内容 map的key 不能自定义
     * 即:COMMON,WEB_ASSETS,COMMON_BUSINESS,RUNTIME,MANAGER,RESOURCE_SKIN,HOME不能动
     */
    override fun getMapDLU() = linkedMapOf(
        COMMON to Pair("classes_common_lib_dex", 1000), //Maven-Wgllss-Dynamic-Plugin-Common-Library 插件工程 和 版本号
        WEB_ASSETS to Pair("classes_business_web_res", 1000),  //maven-wgllss-sample-assets-source-apk 插件工程 和版本号
        COMMON_BUSINESS to Pair("classes_business_lib_dex", 1001), //maven-wgllss-sample-business-library 插件工程 和 版本号
        RUNTIME to Pair("classes_wgllss_dynamic_plugin_runtime", 1000), //Maven-Wgllss-Dynamic-Plugin-RunTime-Apk 插件工程 和 版本号
        MANAGER to Pair("classes_manager_dex", 1000), // Maven-Wgllss-Dynamic-Plugin-Manager 插件工程 和 版本号
        RESOURCE_SKIN to Pair("classes_common_skin_res", 1000), // maven-wgllss-sample-skin-resource-apk 插件工程 和 版本号
        HOME to Pair("classes_home_dex", 1000)//maven-wgllss-sample-ui-home 插件工程 和 版本号
    )
```

### 三、如何动态修改插件化框架SDK？
插件化代理分发实现,目前只添加了最基本的：如下

```
public interface WXHostActivityDelegate {

    void attachContext(FragmentActivity context, Resources resources);

    void onCreate(Bundle savedInstanceState);

    void onRestart();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void lazyInitValue();

    void onSaveInstanceState(Bundle outState);

    void onConfigurationChanged(Configuration newConfig);
}

```
比如：开发过程中想用 onSaveInstanceState，onBackPressed等，那么直接修改 Maven-Wgllss-Dynamic-Plugin-SDK工程下，Maven-Wgllss-Dynamic-Plugin-Library的WXHostActivityDelegate ，在里面添加 onSaveInstanceState，onBackPressed等，然后再去 Maven-Wgllss-Dynamic-Plugin-RunTime-Apk下HostPluginActivity内修改代理改方法，其他的或者service 依次类推






