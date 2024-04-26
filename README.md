# WXDynamicPlugin

## 介绍
#### * **WXDynamicPlugin是由本人自住研发的Android插件框架**
### 零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构

## 示例Sample

!![输入图片说明](WX-Resource/wx-pic/Qrcode.png)

## 示例Sample截图

<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_home.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_sc.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_sample.jpg" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_setting.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_vedio.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_audio.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_a2.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_notification.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_webview.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_cp.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_service.png" width="220" height="448"/>
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_so.png" width="220" height="448"/>



## 与市面上其他插件框架相比，WXDynamicPlugin主要具有以下优势和特点

### 优势:

#### 1.支持宿主就一个空壳子，并且启动速度不受任何影响,做到真正原生不用发版

#### 2.插件打包支持“分布式”，多模块单独插件，单独下载，单独加载，单独访问

#### 3.支持插件下载逻辑，版本管理，下载服务器环境全动态化

### 特点:

* **零反射无Hack实现插件技术**：从理论上就已经确定无需对任何系统做兼容开发，更无任何隐藏API调用，和Google限制非公开SDK接口访问的策略完全不冲突。
* **全动态插件框架**：一次性实现完美的插件框架很难，但WXDynamicPlugin将这些全部动态化来实现，使插件化框架代码也成为了插件，同时，宿主下载插件的逻辑，版本控制也可以插件化起来，使得插件的迭代，及插件化框架的修改，以及可能涉及到宿主下载插件逻辑，版本控制逻辑，加载插件逻辑，这些全部动态化起来。目前市面上插件化框架，都没有实现插件下载到本地逻辑的动态化起来
* **插件极限瘦身优化**：编译出插件体积最小，所有插件模块总体积加载起来不到500k,单个模块70k左右，同时可以让各个功能模块单独插件化起来，市面上插件化框架插件体积编译出来基本都3M以上
* **宿主增量极小**：接入宿主的代码全Kotlin实现，真正插件化框架实现宿主接入代码仅4K多，加上下载逻辑，插件版本控制加载接入宿主代码仅60k左右,加上下载版本判断逻辑总共方法数仅80个方法数
* **极限启动优化性能**：做到宿主空壳子，第一次启动就下载到本地到加载，到显示到第一个页面，所需要的总耗时最小，基本是秒开，这得益于插件模块编译出来体积最小化，4G网络基本500ms就下载完了，如果插件编译出来基本3M以上，那么从服务端下载到本地至少10s以上，第一次再加载一个3M的插件又去了2~3s，第一次进入到主UI界面，差不多20s去了。而WXDynamicPlugin真正做到接入插件化后都比各大厂主流顶级App,没有通过宿主接插件化启动时间还快

## 支持特性

* 四大组件 Activity ,Service , ContentProvider ,Broadcaster
* 跨进程使用插件Service
* 插件访问宿主类
* 插件之间可以互不依赖，也可以存在有依赖关系
* 通知栏
* So加载
* 分段加载插件（多Apk分别加载或多Apk以此依赖加载）
* 一个app 分多个模块单独加载
* 一个Activity中加载多个Apk中的View
* 等等……

## 插件化框架对比

| 插件化框架  | Shadow  | WXDynamicPlugin  |
|---|---|---|
| 插件打包体积  | 3M以上  | 500k左右 ✅ |
| 极致化下载管理版本控制  | 需自己实现  |  1步到位 ✅ |
| 插件加载逻辑  | 宿主->管理器->插件  |  宿主->插件 ✅ |
| 首次插件下载到展示首页耗时  | 3~5s以上？  |  1s内 ✅  |
| 插件已经到本地后加载速度  | 1500ms以上?  |  500ms内 ✅ |
| 全动态化  | 支持  |支持 ✅  |
|  插件化框架动态化 |  支持 | 支持 ✅  |
|  下载逻辑代码动态化 | 不支持  | 支持 ✅  |
|  版本控制代码动态化 | 不支持  | 支持 ✅  |

## 编译与开发环境

#### 环境准备

* **打开工程下 local.properties**
#####  以下为我本地电脑配置，需要添加一行下面一句，并改成自己电脑配置:

```
 workingDirPath=D\:\\android_software\\android_sdk\\android_sdk\\build-tools\\32.0.0\\
```
#####  然后在IDE中选择   app   或  sample   模块直接运行，分别体验同一份代码在正常安装情况下和插件情况下的运行情况。如下
  <img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/run_app.jpeg" width="181" height="56"/>
  <img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/run_sample.jpeg" width="578" height="38"/>

## 项目工程目录截图
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/project_img.png" width="331" height="631"/>


## 项目工程目录介绍


| WX-Code                 | 全源码级接入方式工程      |
|-------------------------|-----------------|
| WX-Dynamic-Host-SDK-Lib | 宿主工程所依赖的SDK源码   |
| WX-Maven                | maven依赖仓库方式接入工程 |


