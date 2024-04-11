package com.wgllss.core.ex

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonSyntaxException
import com.wgllss.core.re.R
import com.wgllss.core.units.AppGlobals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun Throwable?.parseErrorString(): String = when (this) {
    is ConnectException, is SocketException -> {
        if (message?.contains("Network is unreachable") == true)
            getString(R.string.Mobilenetuseless_msg)
        if (message?.contains("Failed to connect to") == true)
            getString(R.string.failed_to_connect_to)
        else
            getString(R.string.ConnectException)
    }
    is HttpException -> {
        if (message?.contains("HTTP 50") == true) {
            message!!.substring(0, 8)
        } else {
            getString(R.string.HttpException)
        }
    }
    is InterruptedIOException -> {
        if (message?.contains("timeout") == true)
            getString(R.string.SocketTimeoutException)
        else
            getString(R.string.ConnectException)
    }
    is UnknownHostException -> getString(R.string.UnknownHostException)
    is JsonSyntaxException -> getString(R.string.JsonSyntaxException)
    is SocketTimeoutException, is TimeoutException -> getString(R.string.SocketTimeoutException)
    is IllegalArgumentException -> {
        if (message?.contains("baseUrl must end in ") == true)
            if (AppGlobals.sApplication.isNetWorkActive()) getString(R.string.HostBaseUrlError)
            else getString(R.string.Mobilenetuseless_msg)
        else message ?: getString(R.string.ElseNetException)
    }
    else -> getString(R.string.ElseNetException)
}

fun <T> Flow<T>.flowOnIOAndCatch(errorMsgLiveData: MutableLiveData<String>? = null): Flow<T> =
    flowOn(Dispatchers.IO)
        .catch {
            it.printStackTrace()
            errorMsgLiveData?.value = it.parseErrorString();
        }

suspend fun <T> Flow<T>.flowOnIOAndCatchAAndCollect() {
    flowOnIOAndCatch().collect()//这里，开始结束全放在异步里面处理
}

fun getString(resID: Int) = AppGlobals.sApplication.getString(resID)

//fun <T1, T2, T3, R> Flow<T1>.zip(flow2: Flow<T2>, flow3: Flow<T3>, transform: suspend (T1, T2, T3) -> R): Flow<R> = channelFlow {
//
//    val first: ReceiveChannel<T1> = produce {
//        this@zip.collect {
//            channel.send(it)
//        }
//    }
//
//    val second: ReceiveChannel<T2> = produce {
//        flow2.collect {
//            channel.send(it)
//        }
//    }
//
//    val third: ReceiveChannel<T3> = produce {
//        flow3.collect {
//            channel.send(it)
//        }
//    }
//
//    (second as SendChannel<*>).invokeOnClose {
//        if (!first.isClosedForReceive) first.cancel(CancellationException())
//        if (!third.isClosedForReceive) third.cancel(CancellationException())
//    }
//
//    (third as SendChannel<*>).invokeOnClose {
//        if (!first.isClosedForReceive) first.cancel(CancellationException())
//        if (!second.isClosedForReceive) second.cancel(CancellationException())
//    }
//
//    val otherIterator = second.iterator()
//    val anotherIterator = third.iterator()
//
//    try {
//        first.consumeEach { value ->
//            if (!otherIterator.hasNext() || !anotherIterator.hasNext()) {
//                return@consumeEach
//            }
//            send(transform(value, otherIterator.next(), anotherIterator.next()))
//        }
//    } catch (e: CancellationException) {
//        e.printStackTrace()
//        android.util.Log.e("TAG", "${e.message}")
//        // complete
//    } finally {
//        if (!second.isClosedForReceive) second.cancel(CancellationException())
//        if (!third.isClosedForReceive) third.cancel(CancellationException())
//    }
//}


