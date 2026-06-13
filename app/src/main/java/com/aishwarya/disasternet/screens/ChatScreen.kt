package com.aishwarya.disasternet.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aishwarya.disasternet.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    username: String,
    vm: ChatViewModel = viewModel()
) {

    val messages by vm.messages.collectAsStateWithLifecycle()

    var text by remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF0D47A1)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "🚨 DisasterNet",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    Text(
                        text = "Welcome, $username",
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Button(
                    onClick = {
                        vm.sendMessage(
                            "🚨 SOS ALERT - $username needs urgent help"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text(
                        text = "SOS",
                        color = Color.White
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            if (messages.isEmpty()) {

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No messages yet")
                }

            } else {

                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f)
                ) {

                    items(messages) { msg ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.End
                        ) {

                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            ) {

                                Text(
                                    text = msg,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 10.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                    },
                    modifier = Modifier.weight(1f),
                    label = {
                        Text("Message")
                    }
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Button(
                    onClick = {

                        if (text.isNotBlank()) {

                            vm.sendMessage(
                                "$username : $text"
                            )

                            text = ""
                        }
                    }
                ) {
                    Text("Send")
                }
            }
        }
    }
}