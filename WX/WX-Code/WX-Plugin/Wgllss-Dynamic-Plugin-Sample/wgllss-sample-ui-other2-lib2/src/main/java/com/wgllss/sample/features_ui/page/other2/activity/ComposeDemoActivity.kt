package com.wgllss.sample.features_ui.page.other2.activity

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.wgllss.sample.features_ui.page.base.BasePluginComposeActivity
import com.wgllss.sample.features_ui.page.other2.viewmodel.ComposeDemoViewModel
import com.wx.compose1.ui.ui.theme.WXComposeXXXTheme
import kotlinx.coroutines.launch

class ComposeDemoActivity : BasePluginComposeActivity() {

    private lateinit var viewModel: ComposeDemoViewModel

    override fun attachContext(context: ComponentActivity, resources: Resources) {
        super.attachContext(context, resources)
        viewModel = context.viewModels<ComposeDemoViewModel>().value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.lifecycleScope.launch {
            activity.setContent {
                WXComposeXXXTheme {
                    baseUIXXXX({ paddingvalues ->
                        layoutExamplexxx(paddingvalues, viewModel)
                    }, onClick = {
                        Toast.makeText(activity, "我是插件里面的", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
        viewModel.loadBitmap(resources)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun baseUIXXXX(content: @Composable (PaddingValues) -> Unit, onClick: () -> Unit) {
    val context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .height(81.dp), colors = mediumTopAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "compose 插件化写法", fontSize = 18.sp, color = Color.Red, style = TextStyle.Default)
            }
        })
    }, bottomBar = {
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            Text(
                text = "compose 底部栏", fontSize = 18.sp, color = Color.Red, style = TextStyle.Default, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = onClick) {
            Icon(
                Icons.Default.Add, contentDescription = "Add", tint = Color.Red
            )
        }
    }) { innerPadding ->
        content(innerPadding)
    }
}


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun layoutExamplexxx(innerPadding: PaddingValues, viewModel: ComposeDemoViewModel) {
    var offset by remember { mutableStateOf(0) }
    val bitmap = viewModel.bitmap.observeAsState()
//    val imageBitmap = remember { bitmap.value?.asImageBitmap() }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(Color.Yellow)
            .verticalScroll(rememberScrollState())
            .padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .offset(offset.dp, offset.dp)
                .border(2.dp, SolidColor(Color.Green), RoundedCornerShape(20.dp))
                .background(Color.Cyan)
                .padding(5.dp),
            text = "compose 内容1:我是插件里面的".trimIndent(),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(116.dp)
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = "compose 内容2:我是插件里面的 A", modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
//                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    .background(Color.Red)
                    .align(Alignment.TopStart), textAlign = TextAlign.Center, style = TextStyle(fontSize = 18.sp), color = Color.White
            )
            Text(
                modifier = Modifier
                    .padding(8.dp, 58.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
                    .height(50.dp)
//                        .size(1000.dp, 50.dp)
                    .background(Color.Green), textAlign = TextAlign.Center, style = TextStyle(fontSize = 18.sp), color = Color.Yellow, text = "compose 内容3:文字控件 B"

            )
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp)
                .background(Color.Magenta)
                .padding(5.dp)
        ) {
            val (buttonID, textID) = createRefs()
            Button(onClick = {
                Toast.makeText(context, "我是插件里面的？", Toast.LENGTH_LONG).show()
            }, modifier = Modifier.constrainAs(buttonID) {
                top.linkTo(parent.top, margin = 16.dp)
            }) {
                Text("compose 内容4 button 控件")
            }
            // Assign reference "text" to the Text composable
            // and constrain it to the bottom of the Button composable
            Text("compose 内容5 text 我是插件里面的 控件",
                Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .background(Color.Red)
                    .constrainAs(textID) {
                        top.linkTo(buttonID.bottom, margin = 16.dp)
                    })
        }

        FlowColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
                .background(Color.White)
                .padding(5.dp)
        ) {

            //图片 需要从插件包 apk 里面拿到 resource ，从resource 里面取出drawable，然后转化为 bitmap 再 转化为 imageBitmap 然后显示

//            val imageBitmap: ImageBitmap =
            bitmap.value?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = "小姐姐", modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(5.dp)
                    .weight(1.0f)
                    .clickable {
                        Toast
                            .makeText(context, "Image 点击事件", Toast.LENGTH_SHORT)
                            .show()
                    })
            }


            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.LightGray), text = "compose 内容列表:我是插件里面的", textAlign = TextAlign.Center, color = Color.Black
            )


        }
    }


}