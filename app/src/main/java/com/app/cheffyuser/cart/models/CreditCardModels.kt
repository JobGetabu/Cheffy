package com.app.cheffyuser.cart.models

import com.google.gson.annotations.SerializedName


data class CreditCardRequest(
    val cvc: Int,
    val exp_month: Int,
    val exp_year: Int,
    val number: String
)

data class CreditCardResponse(
    @SerializedName("billing_details")
    val billingDetails: BillingDetails? = null,
    @SerializedName("card")
    val card: Card? = null,
    @SerializedName("created")
    val created: Int? = null,
    @SerializedName("customer")
    val customer: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("livemode")
    val livemode: Boolean? = null,
    @SerializedName("metadata")
    val metadata: Metadata? = null,
    @SerializedName("object")
    val objectX: String? = null,
    @SerializedName("type")
    val type: String? = null
)

data class BillingDetails(
    @SerializedName("address")
    val address: Address? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null
)

data class Address(
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("country")
    val country: Any? = null,
    @SerializedName("line1")
    val line1: String? = null,
    @SerializedName("line2")
    val line2: String? = null,
    @SerializedName("postal_code")
    val postalCode: String? = null,
    @SerializedName("state")
    val state: String? = null
)

data class Card(
    @SerializedName("brand")
    val brand: String? = null,
    @SerializedName("checks")
    val checks: Checks? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("exp_month")
    val expMonth: Int? = null,
    @SerializedName("exp_year")
    val expYear: Int? = null,
    @SerializedName("fingerprint")
    val fingerprint: String? = null,
    @SerializedName("funding")
    val funding: String? = null,
    @SerializedName("generated_from")
    val generatedFrom: Any? = null,
    @SerializedName("last4")
    val last4: String? = null,
    @SerializedName("three_d_secure_usage")
    val threeDSecureUsage: ThreeDSecureUsage? = null,
    @SerializedName("wallet")
    val wallet: Any? = null
)

data class Checks(
    @SerializedName("address_line1_check")
    val addressLine1Check: String? = null,
    @SerializedName("address_postal_code_check")
    val addressPostalCodeCheck: String? = null,
    @SerializedName("cvc_check")
    val cvcCheck: String? = null
)

data class ThreeDSecureUsage(
    @SerializedName("supported")
    val supported: Boolean? = null
)

class Metadata()