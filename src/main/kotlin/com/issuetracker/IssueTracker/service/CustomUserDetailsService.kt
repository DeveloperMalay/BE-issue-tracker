package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

typealias ApplicationUser=com.issuetracker.IssueTracker.model.User

class CustomUserDetailsService(
    private val userRepository: UserRepository
):UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.mapToUserDetails()?:throw UsernameNotFoundException("Not Found")

    private fun  ApplicationUser.mapToUserDetails() : UserDetails =
 User.builder().username(this.email).password(this.password).build()


}