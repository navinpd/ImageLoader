package com.big.imageloader.data.remote.response.search_response

data class ImageResponse (

	val _type : String,
	val totalCount : Int,
	val value : List<Value>
)