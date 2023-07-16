package com.example.pkartadmin.model

data class AddProductModel(
    val productName: String? = "",
    val productDescription: String? = "",
    val productCoverImg: String? = "",
    val productCategory: String? = "",
    val productId: String? = "",
    val productMRP: String? = "",
    val productSP: String? = "",
    val productImages: ArrayList<String>
)