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
-keep class com.google.android.material.** { *; }


-keep class androidx.appcompat.view.ContextThemeWrapper { }
-keep class androidx.fragment.app.FragmentActivity { }
-keep class androidx.lifecycle.** { *; }


-keep class com.wgllss.core.** { *; }
-keep class com.wgllss.sample.** { *; }
-keep class com.wgllss.dynamic.host.lib.**{}
#-keep class com.wgllss.host.library.** { *; }

#-keep class com.wgllss.dynamic.lib.** { *; }
#-keep class com.wgllss.host.library.** { *; }
#-keep class com.wgllss.plugin.library.** { *; }
#-keep class com.wgllss.host.library.impl.** { *; }

-keep class androidx.savedstate.** { *; }
-keep class androidx.arch.core.internal.**{*;}

-keep class com.wgllss.**{*;}
-keep class android.**{*;}
-keep class android.app.**{*;}
-keep class androidx.**{*;}
-keep class androidx.activity.**{*;}
-keep class kotlinx.**{*;}
-keep class coil.**{*;}
-keep class com.tencent.mmkv.**{*;}
-keep class com.google.android.**{*;}
-keep class com.google.android.exoplayer2**{*;}
-keep class com.google.gson.**{*;}
-keep class retrofit2.**{*;}
-keep class okhttp3.**{*;}
-keep class okio.**{*;}
-keep class com.bumptech.glide.**{*;}
-keep class com.mcxtzhang.**{*;}
-keep class com.hjq.**{*;}
-keep class com.umeng.**{*;}
-keep class org.jsoup.**{*;}
-keep class res.**{*;}
-keep class com.wgllss.sample.skin.**{*;}
-keep class androidx.compose.**{*;}
-keep class androidx.constraintlayout.compose.**{*;}


-keep class * extends androidx.startup.Initializer{}

# Fragment
-keep class * extends androidx.fragment.app.Fragment{}
-keep class * extends android.app.Application{}
-keep class * extends androidx.lifecycle.LiveData{}
-keep class * extends androidx.startup.InitializationProvider{}

# keepclassmembers :防止类成员被移除或被混淆
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# 保留本地native方法不被混淆
-keepclasseswithmembernames class * { native <methods>;}

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
-keepclassmembers class * extends androidx.lifecycle.LifecycleOwner {
    <init>(...);
}
#-keepclassmembers class * extends androidx.savedstate.SavedStateRegistryOwner {
#    <init>(...);
#}
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @androidx.lifecycle.OnLifecycleEvent *;
}

# ViewBinding
#-keep class androidx.databinding.DataBindingUtil{}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


## Databinding
#-dontwarn android.databinding.**
#-keep class android.databinding.** { *; }
#
#-keep class android.content.Intent { *; }
#-keep class android.os.Bundle { *; }

# kotlin 相关
-dontwarn kotlin.**
-keep class kotlin.** { *; }
-keep interface kotlin.** { *; }
