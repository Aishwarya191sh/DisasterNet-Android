package com.aishwarya.disasternet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aishwarya.disasternet.model.MessageRequest
import com.aishwarya.disasternet.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages =
        MutableStateFlow<List<String>>(emptyList())

    val messages: StateFlow<List<String>>
        get() = _messages

    init {
        startPolling()
    }

    private fun startPolling() {

        viewModelScope.launch {

            while (true) {

                try {
                    _messages.value =
                        RetrofitClient.api.getMessages()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                delay(2000)
            }
        }
    }

    fun sendMessage(message: String) {

        viewModelScope.launch {

            try {
                RetrofitClient.api.sendMessage(
                    MessageRequest(message)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}