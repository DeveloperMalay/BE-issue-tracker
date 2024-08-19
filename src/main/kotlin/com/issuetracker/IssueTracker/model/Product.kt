package com.issuetracker.IssueTracker.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)

data class ModifiedProduct(
    val id: Int,
    val title: String,
    val price: String, // Modified to include currency symbol
    val summary: String // New field summarizing the product
)
