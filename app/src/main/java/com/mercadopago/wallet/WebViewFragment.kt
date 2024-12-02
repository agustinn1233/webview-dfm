package com.mercadopago.wallet

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitcompat.SplitCompat

class WebViewFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    companion object {
        private const val HOME_URL = "https://www.google.com.ar"
        fun newInstance() = WebViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view.findViewById(R.id.webview_test)
        progressBar = view.findViewById(R.id.webview_progress_bar)

        configureWebView()

        SplitCompat.installActivity(requireActivity().applicationContext)
        SplitCompat.install(requireActivity().applicationContext)

        if (savedInstanceState == null) {
            webView.loadUrl(HOME_URL)
        } else {
            webView.restoreState(savedInstanceState)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

        webSettings.allowFileAccess = false
        webSettings.allowContentAccess = false

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?, request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView?, request: WebResourceRequest?, error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(
                view: WebView?, handler: SslErrorHandler?, error: SslError?
            ) {
                handler?.cancel()
                super.onReceivedSslError(view, handler, error)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                activity?.title = title
                super.onReceivedTitle(view, title)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            webView.destroy()
        }
    }
}