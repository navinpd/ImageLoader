package com.big.imageloader.ui.dashboard

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
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val searchView: SearchView = root.findViewById(R.id.search_sv)
        val webView: WebView = root.findViewById(R.id.web_view)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://contextualwebsearch.com/")

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

    private fun getDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent(
                (context!!
                    .applicationContext as MyApplication).applicationComponent
            )
            .dashboardFragmentModule(DashboardFragmentModule(this))
            .build()
            .inject(this)
    }

}