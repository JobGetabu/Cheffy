package com.app.cheffyuser.home.model

import com.google.gson.annotations.SerializedName


val reviewType_chef = "chef"
val reviewType_driver = "driver"
val reviewType_plate = "plate"

data class ReviewRequest(
    val orderItemId: Int,
    val rating: Double,
    val comment: String?
)


data class ReviewResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val reviewData: ReviewData? = null
)


data class ReviewData(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("orderItemId")
    val orderItemId: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("orderId")
    val orderId: String? = null,
    @SerializedName("review_type")
    val reviewType: String? = null,
    @SerializedName("plateId")
    val plateId: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null
)


data class AggregateRatingResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("aggregate_rating")
    val aggregateRating: String? = null,
    @SerializedName("data")
    val aggregateRatingData: AggregateRatingData? = null
)


data class AggregateRatingData(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("review_type")
    val reviewType: String? = null,
    @SerializedName("chefID")
    val chefID: Any? = null,
    @SerializedName("driverID")
    val driverID: Int? = null,
    @SerializedName("plateId")
    val plateId: Any? = null,
    @SerializedName("userCount")
    val userCount: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)