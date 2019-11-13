package com.app.cheffyuser

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.app.cheffyuser.utils.AppExecutors
import com.app.cheffyuser.utils.ReleaseLogTree
import com.app.cheffyuser.utils.TokenManager
import com.droidnet.DroidNet
import timber.log.Timber

class CheffyApp : MultiDexApplication() {

    private val executors: AppExecutors? = null

    val tokenManager: TokenManager
        get() {
            val preferences = getSharedPreferences(applicationContext.packageName, Context.MODE_PRIVATE)
            return TokenManager.getInstance(preferences)
        }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        // Setup singleton instance
        instance = this

        DroidNet.init(this)

        //TODO Crashlytics reporting
        //TODO Map Access token - google/mapbox
        //TODO Init fb sdk
        //TODO Init Firebase notifications

        //plant timber
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                //Add the line number to the tag
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + ": " + element.lineNumber
                }
            })
        } else {
            //Release mode
            Timber.plant(ReleaseLogTree())
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        DroidNet.getInstance().removeAllInternetConnectivityChangeListeners()
    }

    companion object {
        // Singleton instance
        // Getter to access Singleton instance
        var instance: CheffyApp? = null
            private set

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

}