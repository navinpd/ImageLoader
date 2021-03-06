package com.big.imageloader.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.big.imageloader.MyApplication
import com.big.imageloader.R
import com.big.imageloader.di.DaggerFragmentComponent
import com.big.imageloader.di.module.DashboardFragmentModule
import com.big.imageloader.ui.viewmodel.DashboardViewModel


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this)
                .get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val searchView: SearchView = root.findViewById(
            R.id.search_sv
        )
        val webView: WebView = root.findViewById(
            R.id.web_view
        )
        webView.loadUrl("https://contextualwebsearch.com/")
        setDesktopMode(webView, true)
        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText

        searchPlate.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.toString().isNotEmpty()) {

                    webView.loadUrl("https://contextualwebsearch.com/search/${v.text}/images")
                    val imm =
                        context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchPlate.windowToken, 0)
                }
            }
            false
        }
        return root
    }

    private fun setDesktopMode(webView: WebView, enabled: Boolean) {
        var newUserAgent = webView.settings.userAgentString
        if (enabled) {
            try {
                val ua = webView.settings.userAgentString
                val androidOSString = webView.settings.userAgentString
                    .substring(ua.indexOf("("), ua.indexOf(")") + 1)
                newUserAgent = webView.settings.userAgentString
                    .replace(androidOSString, "(X11; Linux x86_64)")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            newUserAgent = null
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.userAgentString = newUserAgent
        webView.settings.useWideViewPort = enabled
        webView.settings.loadWithOverviewMode = enabled

        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false

        webView.reload()
    }

    private fun getDependencies() {
        DaggerFragmentComponent.builder()
            .applicationComponent(
                (context!!
                    .applicationContext as MyApplication).applicationComponent
            )
            .dashboardFragmentModule(
                DashboardFragmentModule(
                    this
                )
            )
            .build()
            .inject(this)
    }

}