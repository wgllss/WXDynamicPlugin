package com.wgllss.compiler

import com.google.auto.service.AutoService
import com.wgllss.annotations.CreateVersionConfig
import com.wgllss.dynamic.host.lib.protobuf.PluginMode
import com.wgllss.loader.version.LoaderVersionImpl
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic
import javax.tools.FileObject
import javax.tools.StandardLocation

@AutoService(Processor::class)
class AptCreateConfigProcessor : AbstractProcessor() {
    private val OUTPUT_FILE_NAME = "vc"
    private var mFiler: Filer? = null
    private var mElementUtils: Elements? = null

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        mFiler = processingEnv?.filer
        mElementUtils = processingEnv?.elementUtils
    }

    //指定处理的版本
    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    //给到需要处理的注解
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types: LinkedHashSet<String> = LinkedHashSet()
        getSupportedAnnotations().forEach { clazz: Class<out Annotation> ->
            types.add(clazz.canonicalName)
        }
        return types
    }

    private fun getSupportedAnnotations(): Set<Class<out Annotation>> {
        val annotations: LinkedHashSet<Class<out Annotation>> = LinkedHashSet()
        // 需要解析的自定义注解
        annotations.add(CreateVersionConfig::class.java)
        return annotations
    }

    /**
    KotlinPoet 官方helloWorld示例：
    val greeterClass = ClassName("", "Greeter")
    val file = FileSpec.builder("", "HelloWorld")
    .addType(TypeSpec.classBuilder("Greeter")
    .primaryConstructor(FunSpec.constructorBuilder()
    .addParameter("name", String::class).build())
    .addProperty(PropertySpec.builder("name", String::class)
    .initializer("name").build())
    .addFunction(FunSpec.builder("greet")
    .addStatement("println(%P)", "Hello, \$name").build())
    .build())
    .addFunction(FunSpec.builder("main")
    .addParameter("args", String::class, VARARG)
    .addStatement("%T(args[0]).greet()", greeterClass).build())
    .build()
    file.writeTo(System.out)
    ——————————————————————————————————
    class Greeter(val name: String) {
    fun greet() {println("""Hello, $name""")}}
    fun main(vararg args: String) {Greeter(args[0]).greet()}
     */
    override fun process(annotations: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        var fos: FileOutputStream? = null
        try {
            //filer.createResource()意思是创建源文件
            //我们可以指定为class文件输出的地方，
            //StandardLocation.CLASS_OUTPUT：java文件生成class文件的位置，/app/build/intermediates/javac/debug/classes/目录下
            //StandardLocation.SOURCE_OUTPUT：java文件的位置，一般在/ppjoke/app/build/generated/source/apt/目录下
            //StandardLocation.CLASS_PATH 和 StandardLocation.SOURCE_PATH用的不多，指的了这个参数，就要指定生成文件的pkg包名了
            val resource: FileObject = mFiler!!.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME)
            val resourcePath = resource.toUri().path
            //由于我们想要把json文件生成在app/src/main/assets/目录下,所以这里可以对字符串做一个截取，
            //以此便能准确获取项目在每个电脑上的 /app/src/main/assets/的路径
            val appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4)
            val jsonPath = appPath + "build/outputs/apk/"
            val file = File(jsonPath)
            if (!file.exists()) {
                file.mkdirs()
            }

            //此处就是稳健的写入了
            val outPutFile = File(file, OUTPUT_FILE_NAME)
            if (outPutFile.exists()) {
                outPutFile.delete()
            }
            outPutFile.createNewFile()
            val bytes = createConfig().toByteArray();

            fos = FileOutputStream(outPutFile)
            fos.write(bytes)
            fos.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return true
    }

    private fun log(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, message)
    }

    private fun createConfig(): PluginMode._PluginMode {
        val loader = LoaderVersionImpl()
        val pluginMode = PluginMode._PluginMode.newBuilder()
            .setV(loader.getV())
            .setClfd(PluginMode._PluginMode._Plugin.newBuilder().setV(loader.getClfd().third).setDlu(loader.getClfd().second).setIsApkRes(false).build())
            .setClmd(PluginMode._PluginMode._Plugin.newBuilder().setV(loader.getClmd().third).setDlu(loader.getClmd().second).setIsApkRes(false).build())
            .setCdlfd(PluginMode._PluginMode._Plugin.newBuilder().setV(loader.getCdlfd().third).setDlu(loader.getCdlfd().second).setIsApkRes(false).build())
        val map = linkedMapOf<String, PluginMode._PluginMode._Plugin>()
        loader.getMapDLU().forEach { (key, value) ->
            map[key] = PluginMode._PluginMode._Plugin.newBuilder().setV(value.second).setDlu(value.first).setIsApkRes(false).build()
        }
        pluginMode.putAllMapDl(map)
        loader.getOthers().forEach { (key, value) ->
            var isApkRes = key.contains("_res")
            pluginMode.addOthers(PluginMode._PluginMode._Plugin.newBuilder().setV(value).setDlu(key).setIsApkRes(isApkRes).build())
        }
        return pluginMode.build()
    }
}