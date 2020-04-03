package com.big.imageloader.data.remote

import com.big.imageloader.data.remote.response.search_response.ImageResponse
import com.navin.downloadfiletest.data.remote.Endpoints
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkService {


//https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/Search/ImageSearchAPI?autoCorrect=false&pageNumber=1&pageSize=10&q=New%20Item&safeSearch=false
    @GET(Endpoints.ENDPOINT_SEARCH_API)
    fun searchImages(
    @Header(Endpoints.KEY_API_KEY) apiKey: String = Networking.API_VAL,
    @Header(Endpoints.KEY_API_HOST_KEY) hostKey: String = Networking.API_HOST_VAL,

    @Query(Endpoints.KEY_Auto_Correct) autoCorrect: Boolean = false,
    @Query(Endpoints.KEY_Page_Number) pageNumber: Int,
    @Query(Endpoints.KEY_Page_Size) pageSize: Int,
    @Query(Endpoints.KEY_QUERY) queryText: String,
    @Query(Endpoints.KEY_Safe_Search) safeSearch: Boolean = false

    ): Single<ImageResponse>
    // {
    //    "_type": "images",
    //    "totalCount": 408,
    //    "value": [
    //        {
    //            "url": "https://sm.ign.com/ign_ap/news/a/apex-legen/apex-legends-may-be-teasing-octane-with-a-new-in-game-item_vptw.jpg",
    //            "height": 720,
    //            "width": 1280,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=8126696212080375494",
    //            "thumbnailHeight": 135,
    //            "thumbnailWidth": 240,
    //            "base64Encoding": null,
    //            "name": "",
    //            "title": "Apex Legends May Be Teasing Octane with a New In-Game Item - Battle Royale",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "http://www.sofiahousedesigns.com/wp-content/uploads/2019/08/unique-classical-living-room-gallery-of-hot-item-best-selling-furniture-new-design-889.jpg",
    //            "height": 803,
    //            "width": 1300,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=2725506667439201618",
    //            "thumbnailHeight": 200,
    //            "thumbnailWidth": 323,
    //            "base64Encoding": null,
    //            "name": "Unique Classical Living Room Gallery Of Hot Item Best Selling Furniture New Design",
    //            "title": "Unique Classical Living Room Gallery Of Hot Item Best Selling Furniture New Design - Sofia Design",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        }
    //    ]
    //}


}