package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.model.ModifiedProduct
import com.issuetracker.IssueTracker.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.web.reactive.function.client.WebClient

class ProductService(
    private val webClient: WebClient
) {
    suspend fun fetchAndModifyProducts():List<ModifiedProduct>{

        val products = withContext(Dispatchers.IO){
            webClient.get()
                .uri("/products")
                .retrieve()
                .bodyToMono(Array<Product>::class.java)
                .awaitSingle()
                .toList()
        }
        return  products.map { product ->
            ModifiedProduct(
                id = product.id,
                title = product.title,
                price = "$${product.price}",
                summary = "${product.title} - ${product.category}"
            )
        }
    }
}