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





