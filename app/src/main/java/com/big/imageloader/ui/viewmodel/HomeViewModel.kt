package com.big.imageloader.ui.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.big.imageloader.data.remote.NetworkService
import com.big.imageloader.data.remote.response.search_response.ImageResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        val TAG: String = "HomeViewModel"
    }

    private val mapper = jacksonObjectMapper()
    private val queue = ArrayDeque<String>()
    val getSearchResults = MutableLiveData<ImageResponse>()

    fun getSearchResult(query: String, pageNumber: Int, pageSize: Int) {

        val keyString = "$query;$pageNumber"
        val localString = checkIfDataIsInLocal(keyString)

        if (localString!!.isNotEmpty()) {
            val response = mapper.readValue<ImageResponse>(localString)
            getSearchResults.postValue(response)
            return
        }

        queue.push(keyString)

        compositeDisposable.add(
            networkService.searchImages(
                queryText = query,
                pageNumber = pageNumber,
                pageSize = pageSize
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        getSearchResults.postValue(it)
                        if (queue.isNotEmpty()) {
                            val jsonStr = mapper.writeValueAsString(it)
                            saveDataInLocal(queue.poll()!!, jsonStr)
                        }
                        Log.d(TAG, it.toString())
                    },
                    {
                        queue.poll()
                        getSearchResults.postValue(null)
                        Log.d(TAG, it.toString())
                    }
                )
        )
    }

    fun checkIfDataIsInLocal(query: String): String? =
        if (sharedPreferences.contains(query)) sharedPreferences.getString(query, "") else ""


    fun saveDataInLocal(key: String, data: String) =
        sharedPreferences.edit().putString(key, data).apply()


}