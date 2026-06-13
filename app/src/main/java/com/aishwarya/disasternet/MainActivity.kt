package com.aishwarya.disasternet

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.aishwarya.disasternet.screens.ChatScreen
import com.aishwarya.disasternet.screens.UsernameScreen
import com.aishwarya.disasternet.ui.theme.DisasterNetTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val prefs = getSharedPreferences(
                "user_prefs",
                Context.MODE_PRIVATE
            )

            var username by remember {
                mutableStateOf(
                    prefs.getString(
                        "username",
                        null
                    )
                )
            }

            DisasterNetTheme {

                if (username == null) {

                    UsernameScreen { enteredName ->

                        prefs.edit()
                            .putString(
                                "username",
                                enteredName
                            )
                            .apply()

                        username = enteredName
                    }

                } else {

                    ChatScreen(
                        username = username!!
                    )
                }
            }
        }
    }
}