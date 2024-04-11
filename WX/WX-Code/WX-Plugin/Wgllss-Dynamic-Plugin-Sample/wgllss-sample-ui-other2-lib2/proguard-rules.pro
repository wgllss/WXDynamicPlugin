# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/* #这个过滤器是谷歌推荐的算法，一般不做更改
-optimizationpasses 7 # 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-dontpreverify # 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontusemixedcaseclassnames #混合时不使用大小写混合，混合后的类名为小写
-verbose #包含有类名->混淆后类名的映射关系 日志记录
-keepattributes SourceFile,LineNumberTable # 保留代码行号

# Preserve some attributes that may be required for reflection.
-keepattributes AnnotationDefault,*Annotation*,# 避免混淆注解
                EnclosingMethod, # 避免混淆匿名类
                InnerClasses, # 避免混淆内部类
                RuntimeVisibleAnnotations,
                RuntimeVisibleParameterAnnotations,
                RuntimeVisibleTypeAnnotations,
                Signature # 避免混淆泛型

# Fragment
-keep class * extends androidx.fragment.app.Fragment{}
-keep class * extends androidx.fragment.app.FragmentActivity{}

# keepclassmembers :防止类成员被移除或被混淆
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * { native <methods>;}

# The support libraries contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontnote android.support.**
-dontnote androidx.**
-dontwarn android.support.**
-dontwarn androidx.**
# 保留R下面的资源
-keep class **.R$* {*;}
## Android architecture components: Lifecycle
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @androidx.lifecycle.OnLifecycleEvent *;
}

# ViewBinding
-keep public class * extends androidx.viewbinding.ViewBinding {*;}

# These classes are duplicated between android.jar and org.apache.http.legacy.jar.
-dontnote org.apache.http.**
-dontnote android.net.http.**

# 保持自定义控件类不被混淆  # keepclasseswithmembers:防止拥有改成员的类和类成员被移除或被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留枚举类不被混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

# 保留Parcelable序列化类不被混淆
#-keep class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *;}
## 保留Serializable序列化的类不被混淆
#-keepnames class * implements java.io.Serializable
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    !private <fields>;
#    !private <methods>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
## 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
#-keepclassmembers class * {
#    void *(**On*Event);
#    void *(**On*Listener);
#}

# Databinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class kotlinx.coroutines.** { *; }
-keep class com.wgllss.sample.features_ui.page.** { *; }
# kotlin 相关
-dontwarn kotlin.**
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }
################ 第三方库中的混淆规则start ##############################
# Glide混淆
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
#  *** rewind();
#}

#友盟混淆
-keep class com.umeng.** {*;}
-keep class org.repackage.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#Jsoup 混淆
#-dontwarn org.jsoup.**
#-keep class org.jsoup.**{*;}
#-keeppackagenames org.jsoup.nodes

# Gson
#-keep class com.google.gson.** {*;}
#-keep class com.google.gson.stream.** {*;}
#-keep class com.google.** {
#    <fields>;
#    <methods>;
#}


#
############### 对于一些基本指令的添加start ##################
## 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
#-optimizationpasses 5
## 混合时不使用大小写混合，混合后的类名为小写
#-dontusemixedcaseclassnames
## 指定不去忽略非公共库的类
#-dontskipnonpubliclibraryclasses
## 这句话能够使我们的项目混淆后产生映射文件
## 包含有类名->混淆后类名的映射关系
#-verbose
## 指定不去忽略非公共库的类成员
#-dontskipnonpubliclibraryclassmembers
## 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
#-dontpreverify
## 忽略警告
#-ignorewarnings
## 保留Annotation不混淆
#-keepattributes *Annotation*,InnerClasses
## 避免混淆泛型
#-keepattributes Signature
## 抛出异常时保留代码行号
#-keepattributes SourceFile,LineNumberTable
## 指定混淆是采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不做更改
#-optimizations !code/simplification/cast,!field/*,!class/merging/*
################ 对于一些基本指令的添加end #########################
#
#
################ Android开发中一些需要保留的公共部分start ##################
## 保留我们使用的四大组件，自定义的Application等等这些类不被混淆,因为这些子类都有可能被外部调用
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
## 保留support下的所有类及其内部类
#-keep class android.support.** {*;}
## 保留继承的
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.support.v7.**
#-keep public class * extends android.support.annotation.**
## Androidx的混淆
#-keep class com.google.android.material.** {*;}
#-keep class androidx.** {*;}
#-keep public class * extends androidx.**
#-keep interface androidx.** {*;}
#-dontwarn com.google.android.material.**
#-dontnote com.google.android.material.**
#-dontwarn androidx.**
## 保留R下面的资源
#-keep class **.R$* {*;}
## 保留本地native方法不被混淆
#-keepclasseswithmembernames class * { native <methods>;}
## 保留在Activity中的方法参数是view的方法，
## 这样以来我们在layout中写的onClick就不会被影响
#-keepclassmembers class * extends android.app.Activity{ public void *(android.view.View);}
## 保留枚举类不被混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
## 保留我们自定义控件（继承自View）不被混淆
#-keep public class * extends android.view.View{
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
## 保留Parcelable序列化类不被混淆
#-keep class * implements android.os.Parcelable { public static final android.os.Parcelable$Creator *;}
## 保留Serializable序列化的类不被混淆
#-keepnames class * implements java.io.Serializable
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    !private <fields>;
#    !private <methods>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
## 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
#-keepclassmembers class * {
#    void *(**On*Event);
#    void *(**On*Listener);
#}
#
## 所有实体类不能混淆 model文件夹下的所有实体类不能混淆
#
##-keep class com.btpj.lib_base.data.bean.** {*;}
##-keep class com.btpj.wanandroid.data.** {*;}
#
################ Android开发中一些需要保留的公共部分end ##################
#
#
################ 第三方库中的混淆规则start ##############################
## Glide混淆
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep class * extends com.bumptech.glide.module.AppGlideModule {
# <init>(...);
#}
#-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
#  *** rewind();
#}
#
##友盟混淆
#-keep class com.umeng.** {*;}
#-keep class org.repackage.** {*;}
#-keepclassmembers class * {
#   public <init> (org.json.JSONObject);
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
##Jsoup 混淆
#-dontwarn org.jsoup.**
#-keep class org.jsoup.**{*;}
#-keeppackagenames org.jsoup.nodes

# Gson
#-keep class com.google.gson.** {*;}
#-keep class com.google.gson.stream.** {*;}
#-keep class com.google.** {
#    <fields>;
#    <methods>;
#}