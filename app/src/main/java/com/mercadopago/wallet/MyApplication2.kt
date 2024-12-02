package com.mercadopago.wallet

import android.app.Activity
import android.os.Bundle
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication

class MyApplication2 : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleLog()
    }

    private fun registerActivityLifecycleLog() {
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                SplitCompat.installActivity(applicationContext)
                SplitCompat.install(applicationContext)
            }

            override fun onActivityStarted(activity: Activity) {
                SplitCompat.installActivity(applicationContext)
                SplitCompat.install(applicationContext)
            }

            override fun onActivityResumed(activity: Activity) {
                SplitCompat.installActivity(applicationContext)
                SplitCompat.install(applicationContext)
            }

            override fun onActivityPaused(activity: Activity) {
                SplitCompat.installActivity(applicationContext)
                SplitCompat.install(applicationContext)
            }

            override fun onActivityStopped(activity: Activity) {
                SplitCompat.installActivity(applicationContext)
                SplitCompat.install(applicationContext)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                SplitCompat.installActivity(applicationContext)
                SplitCompat.install(applicationContext)
            }

            override fun onActivityDestroyed(activity: Activity) {
                SplitCompat.installActivity(applicationContext)
                SplitCompat.install(applicationContext)
            }
        })
    }
}