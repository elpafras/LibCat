package mr.cat.libcat.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mr.cat.chat.engine.ChatEngine
import mr.cat.chat.model.ChatError
import mr.cat.chat.model.ChatRole
import mr.cat.chat.model.ChatStatus
import mr.cat.setting.compose.rememberSettingTextStyle
import mr.cat.setting.viewmodel.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoChatScreen(
    chatEngine: ChatEngine,
    settingViewModel: SettingViewModel,
    onBack: () -> Unit
) {
    val uiState by chatEngine.uiState.collectAsState()
    val textStyle = rememberSettingTextStyle(settingViewModel)
    var inputText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Chat Demo", 
                        fontFamily = textStyle.fontFamily,
                        fontSize = textStyle.fontSize
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 3.dp) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { 
                            Text(
                                "Ketik pesan...", 
                                fontFamily = textStyle.fontFamily,
                                fontSize = textStyle.fontSize
                            ) 
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = textStyle.fontFamily,
                            fontSize = textStyle.fontSize
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                val message = inputText
                                inputText = ""
                                scope.launch {
                                    chatEngine.sendMessage(message, threadId = "demo-thread")
                                }
                            }
                        },
                        enabled = uiState.status != ChatStatus.SENDING
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            uiState.error?.let { error ->
                ErrorMessage(error)
            }

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.messages) { message ->
                    ChatBubble(message, textStyle)
                }

                if (uiState.status == ChatStatus.SENDING) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Mengetik (${uiState.loadingSeconds}s)...",
                                fontFamily = textStyle.fontFamily,
                                fontSize = textStyle.fontSize
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: mr.cat.chat.model.ChatMessage,
    textStyle: mr.cat.setting.compose.SettingTextStyle
) {
    val isUser = message.role == ChatRole.USER
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val bgColor = if (isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Box(
            modifier = Modifier
                .background(bgColor, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontFamily = textStyle.fontFamily,
                fontSize = textStyle.fontSize
            )
        }
    }
}

@Composable
fun ErrorMessage(error: ChatError) {
    val message = when (error) {
        is ChatError.LimitReached -> "Limit harian tercapai!"
        is ChatError.NetworkError -> "Kesalahan jaringan: ${error.message}"
        is ChatError.GenericError -> "Terjadi kesalahan: ${error.message}"
    }
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
