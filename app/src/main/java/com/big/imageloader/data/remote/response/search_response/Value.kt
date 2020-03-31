package com.big.imageloader.data.remote.response.search_response

import androidx.annotation.Nullable

data class Value (

	val url : String,
	val height : Int,
	val width : Int,
	val thumbnail : String,
	val thumbnailHeight : Int,
	val thumbnailWidth : Int,
	val base64Encoding : String?,
	val name : String,
	val title : String,
	val imageWebSearchUrl : String

)
