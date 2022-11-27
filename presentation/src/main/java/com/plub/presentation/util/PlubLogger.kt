package com.plub.presentation.util

import timber.log.Timber

object PlubLogger {
    fun logD(tag:String, msg:String) {
        Timber.tag(tag).d(msg)
    }
}