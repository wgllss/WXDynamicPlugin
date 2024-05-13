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

####  [插件内部详细介绍](https://gitee.com/wgllss888/WXDynamicPlugin/blob/master/WX/WX-Maven/WX-Plugin/Maven-Wgllss-Dynamic-Plugin-Sample)    






