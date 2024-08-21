package com.issuetracker.IssueTracker.config

import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.context.annotation.Configuration

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder().baseUrl("https://fakestoreapi.com").build()
    }

}