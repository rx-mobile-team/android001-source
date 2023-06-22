package com.rxmobileteam.lecture9sample.data.remote.response


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class PhotoListResponseItem(
    @Json(name = "id")
    val id: String, // r90ZgffimHk
    @Json(name = "slug")
    val slug: String, // r90ZgffimHk
    @Json(name = "created_at")
    val createdAt: String, // 2022-06-02T08:20:27Z
    @Json(name = "updated_at")
    val updatedAt: String, // 2023-06-15T11:29:28Z
    @Json(name = "promoted_at")
    val promotedAt: String?, // 2023-06-15T15:40:01Z
    @Json(name = "width")
    val width: Int, // 15520
    @Json(name = "height")
    val height: Int, // 7760
    @Json(name = "color")
    val color: String, // #262626
    @Json(name = "blur_hash")
    val blurHash: String, // LD8qW.00%MIA?bD$%MRjIAfRxutR
    @Json(name = "description")
    val description: String?, // Hyundai AVANTE in the dark studio
    @Json(name = "alt_description")
    val altDescription: String, // the front end of a silver car in a dark room
    @Json(name = "urls")
    val urls: Urls,
    @Json(name = "links")
    val links: Links,
    @Json(name = "likes")
    val likes: Int, // 74
    @Json(name = "liked_by_user")
    val likedByUser: Boolean, // false
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any>,
    @Json(name = "sponsorship")
    val sponsorship: Sponsorship?,
    @Json(name = "topic_submissions")
    val topicSubmissions: TopicSubmissions?,
    @Json(name = "user")
    val user: User
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Urls(
        @Json(name = "raw")
        val raw: String, // https://images.unsplash.com/photo-1654157925394-4b7809721149?ixid=M3wyNDA3MjZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4Njg1NTQyOHw&ixlib=rb-4.0.3
        @Json(name = "full")
        val full: String, // https://images.unsplash.com/photo-1654157925394-4b7809721149?crop=entropy&cs=srgb&fm=jpg&ixid=M3wyNDA3MjZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4Njg1NTQyOHw&ixlib=rb-4.0.3&q=85
        @Json(name = "regular")
        val regular: String, // https://images.unsplash.com/photo-1654157925394-4b7809721149?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wyNDA3MjZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4Njg1NTQyOHw&ixlib=rb-4.0.3&q=80&w=1080
        @Json(name = "small")
        val small: String, // https://images.unsplash.com/photo-1654157925394-4b7809721149?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wyNDA3MjZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4Njg1NTQyOHw&ixlib=rb-4.0.3&q=80&w=400
        @Json(name = "thumb")
        val thumb: String, // https://images.unsplash.com/photo-1654157925394-4b7809721149?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wyNDA3MjZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4Njg1NTQyOHw&ixlib=rb-4.0.3&q=80&w=200
        @Json(name = "small_s3")
        val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1654157925394-4b7809721149
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: String, // https://api.unsplash.com/photos/r90ZgffimHk
        @Json(name = "html")
        val html: String, // https://unsplash.com/photos/r90ZgffimHk
        @Json(name = "download")
        val download: String, // https://unsplash.com/photos/r90ZgffimHk/download?ixid=M3wyNDA3MjZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4Njg1NTQyOHw
        @Json(name = "download_location")
        val downloadLocation: String // https://api.unsplash.com/photos/r90ZgffimHk/download?ixid=M3wyNDA3MjZ8MXwxfGFsbHwxfHx8fHx8Mnx8MTY4Njg1NTQyOHw
    )

    @Keep
    @JsonClass(generateAdapter = true)
    data class Sponsorship(
        @Json(name = "impression_urls")
        val impressionUrls: List<Any>,
        @Json(name = "tagline")
        val tagline: String, // Connect with Hyundai Motor Group
        @Json(name = "tagline_url")
        val taglineUrl: String, // https://www.hyundaimotorgroup.com
        @Json(name = "sponsor")
        val sponsor: Sponsor
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Sponsor(
            @Json(name = "id")
            val id: String, // hfrh7ZJApJQ
            @Json(name = "updated_at")
            val updatedAt: String, // 2023-06-15T08:19:54Z
            @Json(name = "username")
            val username: String, // hyundaimotorgroup
            @Json(name = "name")
            val name: String, // Hyundai Motor Group
            @Json(name = "first_name")
            val firstName: String, // Hyundai Motor Group
            @Json(name = "last_name")
            val lastName: Any?, // null
            @Json(name = "twitter_username")
            val twitterUsername: Any?, // null
            @Json(name = "portfolio_url")
            val portfolioUrl: String, // https://www.hyundaimotorgroup.com
            @Json(name = "bio")
            val bio: Any?, // null
            @Json(name = "location")
            val location: Any?, // null
            @Json(name = "links")
            val links: Links,
            @Json(name = "profile_image")
            val profileImage: ProfileImage,
            @Json(name = "instagram_username")
            val instagramUsername: String?, // hyundaimotorgroup.official
            @Json(name = "total_collections")
            val totalCollections: Int, // 61
            @Json(name = "total_likes")
            val totalLikes: Int, // 0
            @Json(name = "total_photos")
            val totalPhotos: Int, // 202
            @Json(name = "accepted_tos")
            val acceptedTos: Boolean, // true
            @Json(name = "for_hire")
            val forHire: Boolean, // false
            @Json(name = "social")
            val social: Social
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Links(
                @Json(name = "self")
                val self: String, // https://api.unsplash.com/users/hyundaimotorgroup
                @Json(name = "html")
                val html: String, // https://unsplash.com/ko/@hyundaimotorgroup
                @Json(name = "photos")
                val photos: String, // https://api.unsplash.com/users/hyundaimotorgroup/photos
                @Json(name = "likes")
                val likes: String, // https://api.unsplash.com/users/hyundaimotorgroup/likes
                @Json(name = "portfolio")
                val portfolio: String, // https://api.unsplash.com/users/hyundaimotorgroup/portfolio
                @Json(name = "following")
                val following: String, // https://api.unsplash.com/users/hyundaimotorgroup/following
                @Json(name = "followers")
                val followers: String // https://api.unsplash.com/users/hyundaimotorgroup/followers
            )

            @Keep
            @JsonClass(generateAdapter = true)
            data class ProfileImage(
                @Json(name = "small")
                val small: String, // https://images.unsplash.com/profile-1667725587447-c153854a19dcimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
                @Json(name = "medium")
                val medium: String, // https://images.unsplash.com/profile-1667725587447-c153854a19dcimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
                @Json(name = "large")
                val large: String // https://images.unsplash.com/profile-1667725587447-c153854a19dcimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
            )

            @Keep
            @JsonClass(generateAdapter = true)
            data class Social(
                @Json(name = "instagram_username")
                val instagramUsername: String? , // hyundaimotorgroup.official
                @Json(name = "portfolio_url")
                val portfolioUrl: String, // https://www.hyundaimotorgroup.com
                @Json(name = "twitter_username")
                val twitterUsername: Any?, // null
                @Json(name = "paypal_email")
                val paypalEmail: Any? // null
            )
        }
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class TopicSubmissions(
        @Json(name = "wallpapers")
        val wallpapers: Wallpapers?,
        @Json(name = "3d-renders")
        val dRenders: DRenders?
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Wallpapers(
            @Json(name = "status")
            val status: String // rejected
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class DRenders(
            @Json(name = "status")
            val status: String // rejected
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "id")
        val id: String, // hfrh7ZJApJQ
        @Json(name = "updated_at")
        val updatedAt: String, // 2023-06-15T08:19:54Z
        @Json(name = "username")
        val username: String, // hyundaimotorgroup
        @Json(name = "name")
        val name: String, // Hyundai Motor Group
        @Json(name = "first_name")
        val firstName: String, // Hyundai Motor Group
        @Json(name = "last_name")
        val lastName: String?, // Macanaya
        @Json(name = "twitter_username")
        val twitterUsername: Any?, // null
        @Json(name = "portfolio_url")
        val portfolioUrl: String?, // https://www.hyundaimotorgroup.com
        @Json(name = "bio")
        val bio: String?, // I'm 18 years old and I do 3D renders Send me an email for commission work :)   You can also check out my work on Instagram @graphics_cash
        @Json(name = "location")
        val location: String?, // Los Angeles
        @Json(name = "links")
        val links: Links,
        @Json(name = "profile_image")
        val profileImage: ProfileImage,
        @Json(name = "instagram_username")
        val instagramUsername: String?, // hyundaimotorgroup.official
        @Json(name = "total_collections")
        val totalCollections: Int, // 61
        @Json(name = "total_likes")
        val totalLikes: Int, // 0
        @Json(name = "total_photos")
        val totalPhotos: Int, // 202
        @Json(name = "accepted_tos")
        val acceptedTos: Boolean, // true
        @Json(name = "for_hire")
        val forHire: Boolean, // false
        @Json(name = "social")
        val social: Social
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Links(
            @Json(name = "self")
            val self: String, // https://api.unsplash.com/users/hyundaimotorgroup
            @Json(name = "html")
            val html: String, // https://unsplash.com/ko/@hyundaimotorgroup
            @Json(name = "photos")
            val photos: String, // https://api.unsplash.com/users/hyundaimotorgroup/photos
            @Json(name = "likes")
            val likes: String, // https://api.unsplash.com/users/hyundaimotorgroup/likes
            @Json(name = "portfolio")
            val portfolio: String, // https://api.unsplash.com/users/hyundaimotorgroup/portfolio
            @Json(name = "following")
            val following: String, // https://api.unsplash.com/users/hyundaimotorgroup/following
            @Json(name = "followers")
            val followers: String // https://api.unsplash.com/users/hyundaimotorgroup/followers
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class ProfileImage(
            @Json(name = "small")
            val small: String, // https://images.unsplash.com/profile-1667725587447-c153854a19dcimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
            @Json(name = "medium")
            val medium: String, // https://images.unsplash.com/profile-1667725587447-c153854a19dcimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
            @Json(name = "large")
            val large: String // https://images.unsplash.com/profile-1667725587447-c153854a19dcimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String?, // hyundaimotorgroup.official
            @Json(name = "portfolio_url")
            val portfolioUrl: String?, // https://www.hyundaimotorgroup.com
            @Json(name = "twitter_username")
            val twitterUsername: Any?, // null
            @Json(name = "paypal_email")
            val paypalEmail: Any? // null
        )
    }
}
