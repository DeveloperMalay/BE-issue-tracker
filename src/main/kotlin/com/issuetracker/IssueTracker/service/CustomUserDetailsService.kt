package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.datasource.database.UserDataSourceImpl
import com.issuetracker.IssueTracker.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser=com.issuetracker.IssueTracker.model.User

@Service
class CustomUserDetailsService(
    private val userRepository: UserDataSourceImpl
):UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        println("username -> $username")
        val res =   userRepository.findByEmail(username)
            ?.mapToUserDetails()?:throw UsernameNotFoundException("Not Found")
        println("response $res")
        return res;
    }


    private fun  ApplicationUser.mapToUserDetails() : UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .authorities(emptyList())
            .build()

}