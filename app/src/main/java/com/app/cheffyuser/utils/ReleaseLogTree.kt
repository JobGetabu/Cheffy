package com.app.cheffyuser.utils

import android.util.Log
import timber.log.Timber

class ReleaseLogTree : Timber.Tree() {

    override fun log(
        priority: Int, tag: String?, message: String,
        throwable: Throwable?
    ) {
        if (priority == Log.DEBUG || priority == Log.VERBOSE || priority == Log.INFO) {
            return
        }

        if (priority == Log.ERROR) {
            if (throwable == null) {
                Timber.e(message)
            } else {
                Timber.e(throwable, message)
            }
        }
    }
}
