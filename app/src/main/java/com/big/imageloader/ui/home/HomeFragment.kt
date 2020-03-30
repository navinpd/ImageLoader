package com.big.imageloader.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.big.imageloader.MyApplication
import com.big.imageloader.R
import com.big.imageloader.data.remote.response.search_response.Value
import com.big.imageloader.di.DaggerFragmentComponent
import com.big.imageloader.di.module.HomeFragmentModule
import com.big.imageloader.ui.adapter.SelectableViewAdapter
import com.squareup.picasso.Picasso
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var homeViewModel: HomeViewModel

    private var timer: CountDownTimer? = null

    private var listOfSearchImages = mutableListOf<Value>()

    private lateinit var searchViewAdapter: SelectableViewAdapter
    private lateinit var searchView: SearchView

    private lateinit var progressBar: ProgressBar

    private var pageNumber: Int = 1
    private var previousQuery: String = ""

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

        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(var1: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(searchTerm: String?): Boolean {
                timer?.let { timer?.cancel() }

                //Delay of 500mS to wait for next text
                timer = object : CountDownTimer(500, 500) {
                    override fun onTick(millisUntilFinished: Long) {
                    }

                    override fun onFinish() {
                        if(previousQuery != searchTerm) {
                            previousQuery = searchTerm!!
                            listOfSearchImages.clear()

                            homeViewModel.getSearchResult(searchTerm!!, pageNumber, PAGE_SIZE)
                            timer?.cancel()
                        }
                    }
                }

                timer?.start()
                return true
            }
        })

        homeViewModel.getSearchResults.observe(this, Observer {
            //TODO Get search result and send to adapter
            if(it != null) {
                listOfSearchImages.addAll(it.value)
                searchViewAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "NULL VALUE", Toast.LENGTH_LONG).show()
            }
        })

        return root
    }

    private fun setUpView(view: View) {
        searchView = view.findViewById(R.id.search_sv)
        progressBar = view.findViewById(R.id.progress_circle)

        val recyclerView: RecyclerView = view.findViewById(R.id.search_view_rv)
        searchViewAdapter = SelectableViewAdapter(listOfSearchImages, picasso)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = searchViewAdapter
        searchViewAdapter.setOnItemClickListener(searchItemClickListener)
    }


    private val searchItemClickListener =
        View.OnClickListener { view ->
            val selectedPosition = view.tag as Int

            Toast.makeText(
                this.context,
                getString(R.string.you_clicked, selectedPosition),
                Toast.LENGTH_SHORT
            ).show()

            //TODO make Dialog to show that Position's full image
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