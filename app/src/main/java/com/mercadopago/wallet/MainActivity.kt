package com.mercadopago.wallet

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class MainActivity : AppCompatActivity() {

    private lateinit var splitInstallManager: SplitInstallManager
    private var mySessionId = 0

    companion object {
        private const val MODULE_NAME = "meliphone"
        private const val FRAGMENT_HOME = "fragment_home"
        const val FRAGMENT_WEBVIEW = "fragment_webview"
    }

    private val listener = SplitInstallStateUpdatedListener { state ->
        if (state.sessionId() == mySessionId) {
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {}

                SplitInstallSessionStatus.INSTALLING -> {}

                SplitInstallSessionStatus.INSTALLED -> {
                    Toast.makeText(this, "Módulo instalado", Toast.LENGTH_SHORT).show()
                    this.recreate()
                }

                SplitInstallSessionStatus.FAILED -> {
                    Toast.makeText(this, "Error al instalar el módulo", Toast.LENGTH_SHORT).show()
                }

                SplitInstallSessionStatus.CANCELED -> {
                    Toast.makeText(this, "Instalación cancelada", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splitInstallManager = SplitInstallManagerFactory.create(this.applicationContext)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment.newInstance(), FRAGMENT_HOME)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        splitInstallManager.registerListener(listener)
    }

    override fun onStop() {
        splitInstallManager.unregisterListener(listener)
        super.onStop()
    }

    fun downloadModule() {
        if (splitInstallManager.installedModules.contains(MODULE_NAME)) {
            Toast.makeText(this, "El módulo ya está instalado: $MODULE_NAME", Toast.LENGTH_SHORT)
                .show()
        } else {
            val request = SplitInstallRequest.newBuilder().addModule(MODULE_NAME).build()

            splitInstallManager.startInstall(request).addOnSuccessListener { sessionId ->
                mySessionId = sessionId
                Toast.makeText(this, "Instalación iniciada", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Error al iniciar la instalación", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openWebView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, WebViewFragment.newInstance(), FRAGMENT_WEBVIEW)
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_WEBVIEW)

        if (fragment != null && fragment.isVisible) {
            SplitCompat.installActivity(applicationContext)
            SplitCompat.install(applicationContext)
            supportFragmentManager.popBackStack()
            supportFragmentManager.commit {
                replace(R.id.fragment_container, HomeFragment.newInstance(), FRAGMENT_HOME)
            }
        } else {
            super.onBackPressed()
        }
    }
}