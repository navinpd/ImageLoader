package com.big.imageloader.ui.home

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.big.imageloader.data.remote.NetworkService
import com.big.imageloader.data.remote.response.search_response.ImageResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        val TAG: String = "HomeViewModel"
    }

    val getSearchResults = MutableLiveData<ImageResponse>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    fun getSearchResult(query: String, pageNumber: Int, pageSize: Int) {

        compositeDisposable.add(
            networkService.doSearchCity(
                queryText = query,
                pageNumber = pageNumber,
                pageSize = pageSize
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        getSearchResults.postValue(it)
                        Log.d(TAG, it.toString())
                    },
                    {
                        getSearchResults.postValue(null)
                        Log.d(TAG, it.toString())
                    }
                )
        )
    }

}