package com.mercadopago.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnOpenWebView: Button = view.findViewById(R.id.btn_open_webview)
        val btnDownloadDFM: Button = view.findViewById(R.id.btn_download_dfm)

        btnOpenWebView.setOnClickListener {
            (activity as? MainActivity)?.openWebView()
        }

        btnDownloadDFM.setOnClickListener {
            (activity as? MainActivity)?.downloadModule()
        }
    }
}