package com.ecw.foodrankpr.store

// 리뷰 등록페이지 모델
class ItemData {
    var uid: String? = null             // 리뷰 등록페이지 ID
    var name: String? = null            // 리뷰등록한 사람 이름
    var storeName: String? = null       // 가게 이름
    var storeCategory: String? = null   // 가게 카테고리(중식,일식)
    var storeTime: String? = null       // 가게 영업시간
    var mapaddress: String? = null      // 가게 주소
    var ratingbar: String? = null       // 가게 평점
    var reviewText: String? = null      // 가게 리뷰
    var likePoint: Int? = null          // 가게 좋아요 개수
}