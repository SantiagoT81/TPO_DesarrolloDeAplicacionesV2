package ar.edu.uade.c12024.tpo.UI

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class LoginViewModel: ViewModel() {
    private val coroutineContext: CoroutineContext = newSingleThreadContext("countries")
    private val scope = CoroutineScope(coroutineContext)
    fun loginUser(email: String, password: String) {

    }
    fun registerUser(email: String, password: String) {

    }

    fun loginUserWithGoogle() {

    }
}