# WXDynamicPlugin

## 介绍

#### 最新接入研究配置参考文章介绍  [Compose的全动态插件化框架支持了，已更新到AGP 8.6,Kotlin2.0.20,支持Compose](https://juejin.cn/post/7435587382345482303)

#### **WXDynamicPlugin是由本人自住研发的Android插件框架**

### 零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构

## 示例Sample

![扫码下载](https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Qrcode.png)

## 示例Sample截图

|                                                                                                                                                 |                                                                                                                                                      |                                                                                                                                                 |
|-------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_home.png" width="220" height="448"/>    | <img src="https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/wx-pic/Screenshot_sc.png" width="220" height="448"/>                    | <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_sample.jpg" width="220" height="448"/>  |
| <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_setting.png" width="220" height="448"/> | <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_vedio.png" width="220" height="448"/>        | <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_audio.png" width="220" height="448"/>   |
| <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_a2.png" width="220" height="448"/>      | <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_notification.png" width="220" height="448"/> | <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_webview.png" width="220" height="448"/> |
| <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_cp.png" width="220" height="448"/>      | <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_service.png" width="220" height="448"/>      | <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_so.png" width="220" height="448"/>      |

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_home.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_sc.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_sample.jpg" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_setting.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_vedio.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_audio.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_a2.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_notification.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_webview.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_cp.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_service.png" width="220" height="448"/>)

[//]: # (<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/Screenshot_so.png" width="220" height="448"/>)

## 与市面上其他插件框架相比，WXDynamicPlugin主要具有以下优势和特点

### 优势:

#### 1.支持宿主就一个空壳子，并且启动速度不受任何影响,做到真正原生不用发版

#### 2.插件打包支持“分布式”，多模块单独插件，单独下载，单独加载，单独访问

#### 3.支持接入到宿主的那仅有的一小部分代码 都可以动态更新(插件下载逻辑，版本管理，下载服务器地址，环境全动态化)

#### 4.支持Debug调试插件，不存在插件调试难度

### 特点:

* **零反射无Hack实现插件技术**：从理论上就已经确定无需对任何系统做兼容开发，更无任何隐藏API调用，和Google限制非公开SDK接口访问的策略完全不冲突。
* **全动态插件框架**：一次性实现完美的插件框架很难，但WXDynamicPlugin将这些全部动态化来实现，使插件化框架代码也成为了插件，同时，宿主下载插件的逻辑，版本控制也可以插件化起来，使得插件的迭代，及插件化框架的修改，以及可能涉及到宿主下载插件逻辑，版本控制逻辑，加载插件逻辑，这些全部动态化起来。目前市面上插件化框架，都没有实现插件下载到本地逻辑的动态化起来
* **插件极限瘦身优化**：编译出插件体积最小，所有插件模块总体积加载起来不到500k,单个模块70k左右，同时可以让各个功能模块单独插件化起来，市面上插件化框架插件体积编译出来基本都3M以上
* **宿主增量极小**：接入宿主的代码全Kotlin实现，真正插件化框架实现宿主接入代码仅4K多，加上下载逻辑，插件版本控制加载接入宿主代码仅60k左右,加上下载版本判断逻辑总共方法数仅80个方法数
* **极限启动优化性能**：做到宿主空壳子，第一次启动就下载到本地到加载，到显示到第一个页面，所需要的总耗时最小，基本是秒开，这得益于插件模块编译出来体积最小化，4G网络基本500ms就下载完了，如果插件编译出来基本3M以上，那么从服务端下载到本地至少10s以上，第一次再加载一个3M的插件又去了2~
  3s，第一次进入到主UI界面，差不多20s去了。而WXDynamicPlugin真正做到接入插件化后都比各大厂主流顶级App,没有通过宿主接插件化启动时间还快

## 支持特性

* 四大组件 Activity ,Service , ContentProvider ,Broadcaster
* 跨进程使用插件Service
* fragment
* assets
* 插件访问宿主类
* 插件之间可以互不依赖，也可以存在有依赖关系
* 通知栏
* So加载
* 分段加载插件（多Apk分别加载或多Apk以此依赖加载）
* 一个app 分多个模块单独加载
* 一个Activity中加载多个Apk中的View
* 支持插件调试debug
* 支持Compose
* 等等……

## 插件化框架对比

| 插件化框架         | Shadow      | WXDynamicPlugin |
|---------------|-------------|-----------------|
| 插件打包体积        | 3M以上        | 500k左右 ✅        |
| 极致化下载管理版本控制   | 需自己实现       | 1步到位 ✅          |
| 插件加载逻辑        | 宿主->管理器->插件 | 宿主->插件 ✅        |
| 首次插件下载到展示首页耗时 | 3~5s以上？     | 1s内 ✅           |
| 插件已经到本地后加载速度  | 1500ms以上?   | 500ms内 ✅        |
| 全动态化          | 支持          | 支持 ✅            |
| 插件化框架动态化      | 支持          | 支持 ✅            |
| 下载逻辑代码动态化     | 不支持         | 支持 ✅            |
| 版本控制代码动态化     | 不支持         | 支持 ✅            |
| 插件调试debug     | 不支持         | 支持 ✅            |

## 编译与开发环境

#### 环境准备

* **AS设置JDK 选17，电脑java版本需要安装 1.8.xxx, 打开工程下 local.properties**
* **最新版本AS(Android Studio Ladybug 2024.2.1 Patch 3)以上提示JDK需要 21时：需要修改如下2个工程：**
* **maven-wgllss-sample-create-version-config-compiler 和 maven-wgllss-sample-create-version-config-annotations工程都需要java版本需要改成如下21**
```
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
```

##### 以下为我本地电脑配置，需要添加一行下面一句，并改成自己电脑配置:

```
 workingDirPath=D\:\\android_software\\android_sdk\\android_sdk\\build-tools\\32.0.0\\
```

##### 然后在IDE中选择 app 或 sample 模块直接运行，如下:

  <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/run_app.jpeg" width="181" height="56"/>
  <img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/run_sample.jpeg" width="578" height="38"/>

## 项目工程目录截图

<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/project_img.png" width="331" height="631"/>

## 项目工程目录介绍

| 文件夹                     | 介绍                  | 接入方式               | 
|-------------------------|---------------------|--------------------|
| WX-Code                 | 示例工程全源码级接入方式        | 接入方式一：依赖host模块工程   |   
| WX-Dynamic-Host-SDK-Lib | 宿主工程所依赖的lib工程源码     |                    |       
| WX-Maven                | 示例工程maven依赖仓库方式接入工程 | 接入方式二：maven引入host包 |    

* **特别说明**：WX-Code 和 WX-Maven 下实际代码内容一样，目录结构一样，唯一区别接入全动态host代码方式不一样，另外WX-Maven 下所有文件夹和项目工程名多了 maven前缀，用于在同一工程下区分成 2个不同的项目

#### 推荐用方式二： Maven接入方式

#### 为了方便编译和看，已经注释掉 settings.gradle 中第35行到 137行，需要看源码及源码方式接入（WX-Code下面），请自行放开注释

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

#### maven接入方式 请不要设置代理抓包！！！

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
<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/createfile.jpeg" width="628" height="277"/>

&emsp;&emsp;点击 assembleCreateAllFileRelease 等待 14个文件生成 ，一次不行，再次点击执行命令，如果还不生成，点击当前项目下maven-wgllss-sample-create-version-config-app的另一个assembleCreateVersion2FileRelease，生成2个文件之后再执行assembleCreateAllFileRelease  
&emsp;&emsp;14个文件生成在以下目录:可以拷贝到自己的服务器上面供下载:即上面修改的 getHostL()+getBaseL() 服务器文件夹下面 ,同时把我准备的WX-Resource/so 文件夹和 WX-Resource/skins 文件夹拷贝过去，这是供sample 工程演示所用的，另外皮肤资源包多个apk文件也可以自行通过源码工程打包  
<img src="https://raw.githubusercontent.com/wgllss/WXDynamicPlugin/master/WX-Resource/wx-pic/14file.jpg" width="610" height="260"/>

## 全动态插件化框架WXDynamicPlugin介绍文章：

#### [(一) 插件化框架开发背景：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7347994218235363382)

#### [(二）插件化框架主要介绍：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7367676494976532490)

#### [(三）插件化框架内部详细介绍: 零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7368397264026370083)

#### [(四）插件化框架接入详细指南：零反射，零HooK,全动态化，插件化框架，全网唯一结合启动优化的插件化架构](https://juejin.cn/post/7372393698230550565)

#### [(五) 大型项目架构：全动态插件化+模块化+Kotlin+协程+Flow+Retrofit+JetPack+MVVM+极限瘦身+极限启动优化+架构示例+全网唯一](https://juejin.cn/post/7381787510071934985)

#### [(六) 大型项目架构：解析全动态插件化框架WXDynamicPlugin是如何做到全动态化的？](https://juejin.cn/post/7388891131037777929)

#### [(七) 还在不断升级发版吗？从0到1带你看懂WXDynamicPlugin全动态插件化框架？](https://juejin.cn/post/7412124636239904819)

#### [(八) Compose插件化：一个Demo带你入门Compose，同时带你入门插件化开发](https://juejin.cn/post/7425434773026537483)

#### [(九) 花式高阶：插件化之Dex文件的高阶用法，极少人知道的秘密 ](https://juejin.cn/spost/7428216743166771212)

#### [(十) 5种常见Android的SDK开发的方式，你知道几种？ ](https://juejin.cn/post/7431088937278947391)

#### [(十一) 5种WebView混合开发动态更新方式，直击痛点，有你想要的？ ](https://juejin.cn/post/7433288965942165558)

#### [(十二) Compose的全动态插件化框架支持了，已更新到AGP 8.6,Kotlin2.0.20,支持Compose](https://juejin.cn/post/7435587382345482303)


## 作者开源 Compose可视化图表库

#### [(一)Compose曲线图表库WXChart，你只需要提供数据配置就行了](https://juejin.cn/post/7438835112790605865 "https://juejin.cn/post/7438835112790605865")\

#### [(二)Compose折线图，贝赛尔曲线图，柱状图，圆饼图，圆环图。带动画和点击效果](https://juejin.cn/post/7442228138501259283 "https://juejin.cn/post/7442228138501259283")\

#### [(三)全网最火视频，Compose代码写出来，动态可视化趋势视频，帅到爆](https://juejin.cn/post/7449238845214244875 "https://juejin.cn/post/7449238845214244875")\

#### [(四)全网最火可视化趋势视频实现深度解析，同时新增条形图表](https://juejin.cn/post/7449910229573943350)

## 本人其他开源文章：

#### [那些大厂架构师是怎样封装网络请求的？](https://juejin.cn/post/7435904232597372940)

#### [Kotlin+协程+Flow+Retrofit+OkHttp这么好用，不运行安装到手机可以调试接口吗?可以自己搭建一套网络请求工具](https://juejin.cn/post/7406675078810910761)

#### [花式封装：Kotlin+协程+Flow+Retrofit+OkHttp +Repository，倾囊相授,彻底减少模版代码进阶之路](https://juejin.cn/post/7417847546323042345)

#### [注解处理器在架构，框架中实战应用：MVVM中数据源提供Repository类的自动生成](https://juejin.cn/post/7392258195089162290)

#### [Android串口，USB，打印机，扫码枪，支付盒子，键盘，鼠标，U盘等开发使用一网打尽](https://juejin.cn/post/7439231301869305910)

#### [多台Android设备局域网下的数据备份如何实现？](https://juejin.cn/post/7444378661934055464)

#### [轻松搞定Android蓝牙打印机，双屏异显及副屏分辨率适配解决办法](https://juejin.cn/post/7446820939943428107)

#### [一个Kotlin版Demo带你入门JNI,NDK编程](https://juejin.cn/post/7452181029996380171)

#### 感谢阅读，欢迎给给个星，你们的支持是我开源的动力

## 欢迎光临：

#### **[我的掘金地址](https://juejin.cn/user/356661835082573)**

#### 关于我

**VX号：wgllss**  ,如果想更多交流请加我VX






