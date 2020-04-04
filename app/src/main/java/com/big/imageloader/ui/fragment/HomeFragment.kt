package com.big.imageloader.ui.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.big.imageloader.MyApplication
import com.big.imageloader.R
import com.big.imageloader.data.remote.response.search_response.Value
import com.big.imageloader.di.DaggerFragmentComponent
import com.big.imageloader.di.module.HomeFragmentModule
import com.big.imageloader.ui.adapter.SelectableViewAdapter
import com.big.imageloader.ui.viewmodel.HomeViewModel
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class HomeFragment : Fragment(), GetNextItems {

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var homeViewModel: HomeViewModel

    private var listOfSearchImages = mutableListOf<Value>()

    private lateinit var searchViewAdapter: SelectableViewAdapter
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var dialog: AlertDialog

    private var pageNumber: Int = 1
    private var totalDataCount: Int = 20
    private var currentQuery: String = ""

    companion object {
        val TAG: String = "HomeFragment"
        val PAGE_SIZE: Int = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setUpView(root)

        val textView: TextView = root.findViewById(R.id.text_home)

        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText

        searchPlate.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.toString().isNotEmpty()) {
                    progressBar.visibility = View.VISIBLE
                    textView.visibility = View.VISIBLE

                    pageNumber = 1
                    listOfSearchImages.clear()

                    currentQuery = v.text.toString()
                    homeViewModel.getSearchResult(
                        v.text.toString(), pageNumber,
                        PAGE_SIZE
                    )
                    val imm =
                        context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchPlate.windowToken, 0)
                }
            }
            false
        }

        homeViewModel.getSearchResults.observe(this, Observer {
            progressBar.visibility = View.GONE

            if (it != null && it.totalCount > 0) {
                listOfSearchImages.addAll(it.value)
                searchViewAdapter.notifyDataSetChanged()
                totalDataCount = it.totalCount
                textView.visibility = View.GONE

            } else if (it != null && it.totalCount == 0 && root != null) {

                Snackbar.make(root, "No Image Results Found", Snackbar.LENGTH_LONG).show()
            } else if (root != null) {

                Snackbar.make(root, "Network Error", Snackbar.LENGTH_LONG).show()
            }
        })

        return root
    }

    override fun callForNext() {
        if (totalDataCount > 20 && 20 * pageNumber < totalDataCount) {
            pageNumber++
            progressBar.visibility = View.VISIBLE
            homeViewModel.getSearchResult(
                currentQuery, pageNumber,
                PAGE_SIZE
            )
        }
    }

    private fun setUpView(view: View) {
        searchView = view.findViewById(R.id.search_sv)
        progressBar = view.findViewById(R.id.progress_circle)

        searchViewAdapter = SelectableViewAdapter(listOfSearchImages, glide)
        searchViewAdapter.setOnItemClickListener(searchItemClickListener, this)

        val recyclerView: RecyclerView = view.findViewById(R.id.search_view_rv)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = searchViewAdapter
    }


    private val searchItemClickListener =
        View.OnClickListener { view ->
            val selectedPosition = view.tag as Int

            showAlertDialog(
                listOfSearchImages[selectedPosition].url, selectedPosition + 1
            )
        }

    private fun showAlertDialog(mainURL: String, position: Int) {
        val layout: View = activity!!.layoutInflater.inflate(R.layout.dialog_image, null);
        layout.findViewById<TextView>(R.id.title).text = "Image position ${position}"

        layout.findViewById<Button>(R.id.ok_text)
            .setOnClickListener {
                dialog.dismiss()
            }

        val progressCircle = layout.findViewById<ProgressBar>(R.id.progress_circle)
        val imageHolder = layout.findViewById<ImageView>(R.id.image_holder)

        val dialogBuilder = AlertDialog.Builder(this.context!!)
            .setCancelable(true)
            .setView(layout)

        dialog = dialogBuilder.create()
        dialog.show()

        glide
            .load(mainURL)
            .centerCrop()
            .error(R.drawable.ic_error_outline_black_24dp)
            .placeholder(R.drawable.ic_cloud_download_black_24dp)
            .addListener(object : RequestListener<Drawable?> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {

                    Toast.makeText(context, e!!.message, Toast.LENGTH_LONG).show()
                    progressCircle.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    progressCircle.visibility = View.GONE
                    return false
                }
            })
            .into(imageHolder)
    }

    private fun getDependencies() {
        DaggerFragmentComponent
            .builder()
            .applicationComponent(
                (context!!
                    .applicationContext as MyApplication).applicationComponent
            )
            .homeFragmentModule(HomeFragmentModule(this))
            .build()
            .inject(this)
    }

}

//Interface to invoke query for next set of Images
interface GetNextItems {
    fun callForNext()
}