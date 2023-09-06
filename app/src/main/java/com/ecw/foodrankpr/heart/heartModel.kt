package com.ecw.foodrankpr.heart

// 좋아요 모델
class heartModel {
    var boardId: String?= null          // 리뷰 게시물 ID
    var heartPoint: String?= null       // 좋아요 ON/OFF 확인용
    var uId: String?= null              // 좋아요 페이지 ID
    var userId : String?= null          // 좋아요 누른 유저 ID
    var storeName: String? = null       // 좋아요 가게
    var mapaddress:String? =null        // 좋아요 가게 주소
    var storeTime:String? =null         // 좋아요 가게 영업시간
}
