package com.app.cheffyuser.cart.models

import com.google.gson.annotations.SerializedName




data class OrderRequest(
    val deliveryType: String?,
    val shipping_id: Int,
    val card: CreditCardRequest? = null
)

data class OrderResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("payment_return")
    val paymentReturn: PaymentReturn? = null
)

data class PaymentReturn(
    @SerializedName("amount")
    val amount: Int? = null,
    @SerializedName("card_brand")
    val cardBrand: String? = null,
    @SerializedName("card_country")
    val cardCountry: String? = null,
    @SerializedName("card_exp_month")
    val cardExpMonth: Int? = null,
    @SerializedName("card_exp_year")
    val cardExpYear: Int? = null,
    @SerializedName("card_fingerprint")
    val cardFingerprint: String? = null,
    @SerializedName("card_last")
    val cardLast: String? = null,
    @SerializedName("client_secret")
    val clientSecret: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("customer")
    val customer: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("network_status")
    val networkStatus: String? = null,
    @SerializedName("orderId")
    val orderId: Int? = null,
    @SerializedName("paid")
    val paid: Boolean? = null,
    @SerializedName("payment_method")
    val paymentMethod: String? = null,
    @SerializedName("receipt_url")
    val receiptUrl: String? = null,
    @SerializedName("risk_level")
    val riskLevel: String? = null,
    @SerializedName("risk_score")
    val riskScore: Int? = null,
    @SerializedName("seller_message")
    val sellerMessage: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)