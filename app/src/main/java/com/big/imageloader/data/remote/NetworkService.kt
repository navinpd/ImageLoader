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
    fun doSearchCity(
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
    //        },
    //        {
    //            "url": "https://sm.ign.com/ign_za/news/a/apex-legen/apex-legends-may-be-teasing-octane-with-a-new-in-game-item_dg9v.jpg",
    //            "height": 720,
    //            "width": 1280,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=7407437794380902805",
    //            "thumbnailHeight": 180,
    //            "thumbnailWidth": 320,
    //            "base64Encoding": null,
    //            "name": "",
    //            "title": "Apex Legends May Be Teasing Octane with a New In-Game Item",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "http://facts-about-recycling.com/wp-content/uploads/2018/09/japanese-home-name-best-of-new-item-for-this-month-japanese-nobis-s-house-of-japanese-home-name.jpg",
    //            "height": 542,
    //            "width": 1024,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=6067983959023102509",
    //            "thumbnailHeight": 135,
    //            "thumbnailWidth": 255,
    //            "base64Encoding": null,
    //            "name": "Japanese Home Name Best Of New Item for This Month Japanese Nobis S House",
    //            "title": "Japanese Home Name Best Of New Item for This Month Japanese Nobis S House | facts-about-recycling.com",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "http://www.bollywoodvogue.com/wp-content/uploads/2013/05/eHg0Nzd1MTI_o_madhuri-dixits-item-song-with-ranbir-in-yeh-jawaani-hai-.jpg",
    //            "height": 240,
    //            "width": 320,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=422145023525996413",
    //            "thumbnailHeight": 180,
    //            "thumbnailWidth": 240,
    //            "base64Encoding": null,
    //            "name": "eHg0Nzd1MTI=_o_madhuri-dixits-item-song-with-ranbir-in-yeh-jawaani-hai-",
    //            "title": "Mohini is back the new item number \"Ghagra\" - Bollywood Vogue",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "https://www.dhresource.com/600x600/f2/albu/g4/M00/0E/A9/rBVaEVd8yYyAF0dDAAIzjGPqdW8155.jpg",
    //            "height": 600,
    //            "width": 600,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=3780413572733329207",
    //            "thumbnailHeight": 168,
    //            "thumbnailWidth": 168,
    //            "base64Encoding": null,
    //            "name": "Free shipping new item luxury crystal lamp Dia800*H360mm modern lustre crystal chandelier lighting hotel lobby light",
    //            "title": "New Item Luxury Crystal Lamp Dia800*H360mm Modern Lustre Crystal Chandelier Lighting Hotel Lobby Light Small Chandeliers Bedroom Chandeliers From Lighting_factory, $648.24| DHgate.Com",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "https://wow.zamimg.com/uploads/news/15745-crucible-of-storms-updates-in-8-1-5-ptr-build-new-music-item-effects-adjusted.png",
    //            "height": 675,
    //            "width": 1077,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=6499977522347058964",
    //            "thumbnailHeight": 252,
    //            "thumbnailWidth": 402,
    //            "base64Encoding": null,
    //            "name": "",
    //            "title": "Crucible of Storms Updates in 8.1.5 PTR Build - New Music, Item Effects Adjusted - Notcias do Wowhead",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "http://www.viralnews99.com/wp-content/uploads/2014/10/Mehwish-Hayat-hot-pictures.jpg",
    //            "height": 660,
    //            "width": 1012,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=8789197798247610638",
    //            "thumbnailHeight": 184,
    //            "thumbnailWidth": 283,
    //            "base64Encoding": null,
    //            "name": "Mehwish Hayat hot pictures",
    //            "title": "Mehwish Hayat New Hot & Sexy Pictures from Item song and others",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "https://www.allinlearning.com/wp-content/uploads/2017/04/item-bank-tab.png",
    //            "height": 622,
    //            "width": 974,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=2381177884771850629",
    //            "thumbnailHeight": 155,
    //            "thumbnailWidth": 242,
    //            "base64Encoding": null,
    //            "name": "",
    //            "title": "NEW FEATURES & UPGRADES: New Item Banks Tab and Other Improvements! - All In Learning",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        },
    //        {
    //            "url": "https://static.foxnews.com/foxnews.com/content/uploads/2019/08/nypd-2-iStock.jpg",
    //            "height": 576,
    //            "width": 1024,
    //            "thumbnail": "https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=5817825292757477560",
    //            "thumbnailHeight": 144,
    //            "thumbnailWidth": 256,
    //            "base64Encoding": null,
    //            "name": "",
    //            "title": "New York City subway stop reopens after suspicious item scare | Fox News",
    //            "imageWebSearchUrl": "https://contextualwebsearch.com/search/New%20Item/images"
    //        }
    //    ]
    //}


}