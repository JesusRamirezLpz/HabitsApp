package com.example.habitsapp.authentication.domain.matcher

interface EmailMatcher {
    fun isValidEmail(email: String): Boolean
}