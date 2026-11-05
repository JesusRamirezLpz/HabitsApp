package com.example.habitsapp.authentication.data

import android.util.Patterns
import com.example.habitsapp.authentication.domain.matcher.EmailMatcher

class EmailMatcherImpl: EmailMatcher {
    override fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}