package com.issuetracker.IssueTracker.controller.product

import com.issuetracker.IssueTracker.model.ModifiedProduct
import com.issuetracker.IssueTracker.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlinx.coroutines.runBlocking

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/modified")
    fun getModifiedProducts(): List<ModifiedProduct> = runBlocking {
        productService.fetchAndModifyProducts()
    }
}