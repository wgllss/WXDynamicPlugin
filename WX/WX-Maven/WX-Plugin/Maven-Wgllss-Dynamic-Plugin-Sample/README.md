# WXDynamicPlugin的 WX-Plugin 工程介绍
#### 注意：需要配置ANT 编译环境 
* **打开工程下 local.properties**
#####  以下为我本地电脑配置，需要添加一行下面一句，并改成自己电脑配置:

```
 workingDirPath=D\:\\android_software\\android_sdk\\android_sdk\\build-tools\\32.0.0\\
```
## 项目工程目录截图

<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/project_img.png" width="331" height="631"/>

## 项目工程目录介绍

| 文件夹                     | 介绍                  | 接入方式                 | 
|-------------------------|---------------------|----------------------|
| WX-Code                 | 示例工程全源码级接入方式        | 接入方式一：依赖host模块工程     |   
| WX-Dynamic-Host-SDK-Lib | 宿主工程所依赖的lib工程源码     |                      |       
| WX-Maven                | 示例工程maven依赖仓库方式接入工程 | 接入方式二：maven引入host包 |    

* **特别说明**：WX-Code 和 WX-Maven 下实际代码内容一样，目录结构一样，唯一区别接入全动态host代码方式不一样，另外WX-Maven 下所有文件夹和项目工程名多了 maven前缀，用于在同一工程下区分成 2个不同的项目

#### 推荐用方式二： Maven接入方式

#### 以 WX-Maven下目录结构介绍为例：

#### WX-Host:   下面全部为host宿主项目代码

&emsp;&emsp;sample:  host宿主真实工程app

##### &emsp;&emsp;sample-lib:  下面为宿主工程依赖的4个工程模块

&emsp;&emsp;&emsp;&emsp;maven-wgllss-business-re-library:涉及到app的公共业务带res资源的模块lib       
&emsp;&emsp;&emsp;&emsp;maven-wgllss-common-re-library:涉及到公共代码带res资源的模块lib        
&emsp;&emsp;&emsp;&emsp;maven-wgllss-dynamic-host-library:宿主里manifest注册所必备的四大组件等lib          
&emsp;&emsp;&emsp;&emsp;maven-wgllss-dynamic-host-skin-resource-lib:宿主所必须的资源样式主题等lib

#### WX-Plugin:  该文件夹下面的所有工程都是插件的形式，不存在宿主里面

&emsp;&emsp;Maven-Wgllss-Dynamic-Plugin-Common-Library:  插件中公共代码    
&emsp;&emsp;Maven-Wgllss-Dynamic-Plugin-Generate:  插件中打包所用的apt工程   
&emsp;&emsp;Maven-Wgllss-Dynamic-Plugin-Manager:  插件中管理插件，管理动态代码的3个工程   
&emsp;&emsp;Maven-Wgllss-Dynamic-Plugin-Sample:  插件中真正业务代码插件工程   
&emsp;&emsp;Maven-Wgllss-Dynamic-Plugin-SDK:  插件框架四大组件SDK代码，以插件形式存在  
&emsp;&emsp;Maven-Wgllss-Dynamic-Plugin_Skin:  插件中换皮肤资源

### WX-Resource: 为项目已经打包好的插件，so,皮肤包文件等 存放的文件夹

#### 上面介绍 直接 run运行 ,打开app 进入宿主，直接下载的插件 为我已经放在准好的服务器上面了

&emsp;&emsp;可以通过源码工程自行打包，上传到自己的服务器上部署，方法如下:  
&emsp;&emsp;找到 WX/WX-Maven/WX-Host/sample/ com.wgllss.dynamic.host.FaceImpl

```
  //    override fun getHostL() = "http://192.168.3.21:8080/assets/WXDynamicPlugin/"
//    override fun getHostL() = "http://192.168.1.9:8080/assets/WXDynamicPlugin/"
    //todo 自己本地搭一个服务器，或者 自己服务器 或者 像我一样在gitee上面在自己的项目下建一个文件当作服务器 供下载,
    // 切记不要往往我的 gitee 项目上面推
    override fun getHostL() = "https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/"

    /** 0:WXDynamicPlugin 动态化插件框架 理论上已经做到了可以完全不动宿主,但是如果一定要动宿主 可以提供以下思路:
     *  1:可以根据 宿主版本号得到 宿主版本支持的 的插件,
     *  2:当宿主必须 需要升级时,升级后原版本的插件不可用了，插件配置在新宿主版本文件夹下面，原宿主版本文件夹可可以先动态配置 在启动页 升级下载新的宿主
     *  @example  宿主版本 10000 版本支持的插件 放在服务端 WXDynamicPlugin/10000/ 文件夹下  20000版本的插件放在 WXDynamicPlugin/20000/下面
     */
    override fun getBaseL(): String {
        if (TextUtils.isEmpty(baseXL)) {
            baseXL = StringBuilder().append(getHostL()).append(DeviceIdUtil.getDeviceId()).append("/").append(BuildConfig.VERSION_CODE).append("/").toString()
        }
        return baseXL
    }
```

&emsp;&emsp;修改 getHostL() 地址为自己服务器地址, 修改 getBaseL() 中主要路劲,确保修改后地址可以访问通  
&emsp;&emsp;然后将打包好的14个文件 放入getHostL()+getBaseL() 服务器文件夹下面  
&emsp;&emsp;该 14个文件打包如下:  
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/createfile.jpeg" width="628" height="277"/>

&emsp;&emsp;点击 assembleCreateAllFileRelease 等待 14个文件生成 ，一次不行，再次点击执行命令  
&emsp;&emsp;14个文件生成在以下目录:可以拷贝到自己的服务器上面供下载:即上面修改的 getHostL()+getBaseL() 服务器文件夹下面  ,同时把我准备的WX-Resource/so 文件夹和 WX-Resource/skins 文件夹拷贝过去，这是供sample 工程演示所用的，另外皮肤资源包多个apk文件也可以自行通过源码工程打包  
<img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/14file.jpg" width="610" height="260"/>

## 插件设计详解 (以WX-Maven为例)


* **app 公共代码库插件模块** (Maven-Wgllss-Dynamic-Plugin-Common-Library):设计成插件下载，该模块内代码理论上可以很多app 来依赖，公司内A,B,C,D等app 都可以依赖，其他公司的app也可用的

#### Maven-Wgllss-Dynamic-Plugin-Sample 真正插件app 下需要实现代码模块
* **app 公共业务代码库插件模块** (maven-wgllss-sample-business-library):设计成插件下载，该模块内代码为一个app内公用的业务逻辑，比如服务类业务代码，很多地方调用，某些接口，很多地方调用等，公司内整体一套网络传输数据加密 token的设计
* **app Assets资源插件模块** (maven-wgllss-sample-assets-source-apk):设计成插件下载，该模块内代码为app内用到的assets下存放资源
* **app 皮肤插件模块** (maven-wgllss-sample-skin-resource-apk):设计成插件下载，该模块内代码全是皮肤资源，如图片，颜色，也可含有多语言String 下资源
* **app 启动页插件模块** (maven-wgllss-sample-ui-loading):设计成插件下载，首次打开app展示的启动页面，在该页面停留，下载其他插件
* **app 首页插件模块** (maven-wgllss-sample-ui-home):设计成插件下载，app主页面，为了提高启动速度，启动页面只有展示一屏的代码，像首页HomeActivity的第2,3,4...等tab下fragment则不在该模块中
* **app 首页之外fragment插件模块** (maven-wgllss-sample-ui-other-lib):设计成插件下载，除开首页HomActivity maven-wgllss-sample-ui-home中存在的之外的 该Homactivity 持有的fragment相关代码
* **app 首页之外fragment布局资源插件模块** (maven-wgllss-sample-ui-other):设计成插件下载，maven-wgllss-sample-ui-other-lib对应的相关布局资源
* **app 首页之外的Activity插件模块** (maven-wgllss-sample-ui-other2-lib2):设计成插件下载，除开首页HomActivity 之外其他activity 模块代码
* **app 首页之外的Activity插件模块布局资源** (maven-wgllss-sample-ui-other2):设计成插件下载，maven-wgllss-sample-ui-other2-lib2 模块对应的布局资源
* **app 各个插件版本模块** (maven-wgllss-sample-loader-version):设计成插件下载，各个插件版本配置相关，后面特殊介绍
#### Maven-Wgllss-Dynamic-Plugin-SDK  真正插件框架SDK代码 
* **插件框架SDK中代理接口** (Maven-Wgllss-Dynamic-Plugin-Library) : 插件框架中代理接口
* **插件框架SDK中代理接口实现** (Maven-Wgllss-Dynamic-Plugin-RunTime-Apk):设计成插件下载，插件框架SDK中代理接口 依赖 Maven-Wgllss-Dynamic-Plugin-Library，最终会参与到插件SDK 打包

#### Maven-Wgllss-Dynamic-Plugin-Manager 插件中管理插件，管理动态代码的3个工程   
* **管理插件中 activity跳转,service 启动绑定 ，皮肤，资源等** (Maven-Wgllss-Dynamic-Plugin-Manager) 设计成插件下载 必须有
* **动态实现更换下载插件地址，文件，以及debug 等** (Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl) 宿主默认有一份， 可以不用
* **动态实现根据版本下载插件，加载插件** (Maven-Wgllss-Dynamic-Plugin-Loader-Impl) 宿主默认有一份，可以不用

#### Maven-Wgllss-Dynamic-Plugin-Generate 一个命令执行打包所有插件 apt工程
* **注解处理器annotations 工程** (maven-wgllss-sample-create-version-config-annotations) 
* **注解处理器 工程** (maven-wgllss-sample-create-version-config-compiler) 
* **一键打包14个文件配置命令工程** (maven-wgllss-sample-create-all-app) 
* **一键打包版本配置的2个文件配置命令工程** (maven-wgllss-sample-create-version-config-app) 

## 宿主中FaceImpl 介绍及注释
```
class FaceImpl : IDynamicDownLoadFace {

    private var baseXL: String = ""

    //    override fun getHostL() = "http://192.168.3.21:8080/assets/WXDynamicPlugin/"
//    override fun getHostL() = "http://192.168.1.9:8080/assets/WXDynamicPlugin/"
    //todo 自己本地搭一个服务器，或者 自己服务器 或者 像我一样在gitee上面在自己的项目下建一个文件当作服务器 供下载,
    // 切记不要往往我的 gitee 项目上面推
    override fun getHostL() = "https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/"

    /** 0:WXDynamicPlugin 动态化插件框架 理论上已经做到了可以完全不动宿主,但是如果一定要动宿主 可以提供以下思路:
     *  1:可以根据 宿主版本号得到 宿主版本支持的 的插件,
     *  2:当宿主必须 需要升级时,升级后原版本的插件不可用了，插件配置在新宿主版本文件夹下面，原宿主版本文件夹可可以先动态配置 在启动页 升级下载新的宿主
     *  @example  宿主版本 10000 版本支持的插件 放在服务端 WXDynamicPlugin/10000/ 文件夹下  20000版本的插件放在 WXDynamicPlugin/20000/下面
     */
    override fun getBaseL(): String {
        if (TextUtils.isEmpty(baseXL)) {
            baseXL = StringBuilder().append(getHostL()).append(DeviceIdUtil.getDeviceId()).append("/").append(BuildConfig.VERSION_CODE).append("/").toString()
        }
        return baseXL
    }

    override fun isDebug() = false

    override fun getOtherDLU() = realUrl("vc")

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

    /** 1:首次启动用 宿主里面的 VersionImpl,当有版本更新时用 WX-Plugin/Maven-Wgllss-Dynamic-Plugin-Sample/maven-wgllss-sample-loader-version
     *  2:首次时两个地方文件配置成一样
     *  3: 以后每次修改插件，升级插件 ，需要修改 WX-Plugin/Maven-Wgllss-Dynamic-Plugin-Sample/maven-wgllss-sample-loader-version 下面配置
     **/
    override fun getLoadVersionClassName() = "com.wgllss.loader.version.LoaderVersionImpl"

    private fun realUrl(name: String) = StringBuilder().append(getBaseL()).append(name).toString()

}
```
## 宿主中VersionImpl 介绍及注释
```
class VersionImpl : ILoaderVersion {

    override fun getV() = 1000 //总版本号 只要下面每个地方改一下 此处版本号要往上加+1，下面可同一时间改多个，上面加一下版本号

    override fun isMustShowLoading() = false//下次下载插件 是否显示主下载loading 页面

    override fun getClfd() = Triple(
        //配置loading 页插件实现 及版本号 对应 maven-wgllss-sample-loader-version 工程
        "com.wgllss.dynamic.impl.ILoadHomeImpl",
        "loading",
        1000
    )

    //配置 动态实现根据版本下载插件 及版本号 对应 Maven-Wgllss-Dynamic-Plugin-Loader-Impl 工程
    override fun getClmd() = Triple("", "", 0)

    //动态实现更换下载插件地址，文件，已经debug  Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl
    override fun getCdlfd() = Triple("", "", 0)

    override fun getMapDLU() = linkedMapOf(
        DynamicPluginConstant.COMMON to Pair("classes_common_lib_dex", 1000), //Maven-Wgllss-Dynamic-Plugin-Common-Library 插件工程 和 版本号
        DynamicPluginConstant.WEB_ASSETS to Pair("classes_business_web_res", 1000), //maven-wgllss-sample-assets-source-apk 插件工程 和版本号
        DynamicPluginConstant.COMMON_BUSINESS to Pair("classes_business_lib_dex", 1000),//maven-wgllss-sample-business-library 插件工程 和 版本号
        DynamicPluginConstant.RUNTIME to Pair("classes_wgllss_dynamic_plugin_runtime", 1000), //Maven-Wgllss-Dynamic-Plugin-RunTime-Apk 插件工程 和 版本号
        DynamicPluginConstant.MANAGER to Pair("classes_manager_dex", 1000), // Maven-Wgllss-Dynamic-Plugin-Manager 插件工程 和 版本号
        DynamicPluginConstant.RESOURCE_SKIN to Pair("classes_common_skin_res", 1000), // maven-wgllss-sample-skin-resource-apk 插件工程 和 版本号
        DynamicPluginConstant.HOME to Pair("classes_home_dex", 1000) //maven-wgllss-sample-ui-home 插件工程 和 版本号
    )

    override fun getOthers() = mutableMapOf(
        "classes_other_dex" to 1000,  //maven-wgllss-sample-ui-other-lib 插件工程 和 版本号
        "classes_other_res" to 1000,  //maven-wgllss-sample-ui-other 插件工程 和 版本号
        "classes_other2_dex" to 1000, //maven-wgllss-sample-ui-other2-lib2 插件工程 和 版本号
        "classes_other2_res" to 1000  //maven-wgllss-sample-ui-other2 插件工程 和 版本号
    )
}
```

### 各个插件模块对应的打包命令 如下：  

| 工程名                                           | 打包插件task                 |
|-----------------------------------------------|--------------------------|
| Maven-Wgllss-Dynamic-Plugin-Common-Library    | assembleDxCommandAndCopy |
| Maven-Wgllss-Dynamic-Plugin-Manager           | assembleDxCommandAndCopy |
| Maven-Wgllss-Dynamic-Plugin-Loader-Impl       | assembleDxCommandAndCopy |
| Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl | assembleDxCommandAndCopy |
| maven-wgllss-sample-assets-source-apk         | assembleCopy             |
| maven-wgllss-sample-business-library          | assembleDxCommandAndCopy |
| maven-wgllss-sample-loader-version            | assembleDxCommandAndCopy |
| maven-wgllss-sample-skin-resource-apk         | assembleCopy             |
| maven-wgllss-sample-ui-home                   | assembleDxCommandAndCopy |
| maven-wgllss-sample-ui-loading                | assembleDxCommandAndCopy |
| maven-wgllss-sample-ui-other                  | assembleCopy             |
| maven-wgllss-sample-ui-other2                 | assembleCopy             |
| maven-wgllss-sample-ui-other2-lib2            | assembleDxCommandAndCopy |
| maven-wgllss-sample-ui-other-lib              | assembleDxCommandAndCopy |
| Maven-Wgllss-Dynamic-Plugin-RunTime-Apk       | assembleCopy             |
| 其他工程为配置                                 | 系统自带assembleRelease   |

* **上面配置15个task,所打包后的插件都在 WXDynamicPlugin\build\，外加一个vc文件 总共16个 ,Maven-Wgllss-Dynamic-Plugin-Loader-Impl 和 Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl 可以不用，总共14个文件**
* **一次性打包task assembleCreateAllFileRelease 供首次使用生成14个文件**
* **以后每次修改单独插件模块，可以单独用各自task单独打包，然后还需要修改配置版本文件VersionImpl类后，再执行assembleCreateVersion2FileRelease Task打包，会同时生成classes_version_dex和vc文件，然后把修改后的文件全部放到下载服务器上面，下次启动就可以无感知更新了**

## 特别注意 
* **首次打包 maven-wgllss-sample-ui-loading工程 生成的 classes_loading_dex 文件 需要重名命改为 loading_1000 放到宿主工程 assets 下面**
* **首次打包 宿主默认包含3部分,其一:loading_1000 其二:VersionImpl类，其三:FaceImpl类**
* **之后每次都可以不动宿主,直接修改 loading ,VersionImpl类，FaceImpl类 和其他插件工程，插件框架SDK 等完全全动态插件化，无需动宿主**


