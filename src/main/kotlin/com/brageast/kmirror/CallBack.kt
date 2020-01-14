package com.brageast.kmirror

object CallBack {
    val throwableCallBack = lambda@ fun Throwable.() {
        this.printStackTrace()
    }
}