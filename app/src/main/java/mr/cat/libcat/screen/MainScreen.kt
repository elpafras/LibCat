package mr.cat.libcat.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.component.model.toTextUnit
import mr.cat.setting.rememberSettingState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var showSheet by remember { mutableStateOf(false) }
    val setting = rememberSettingState()

    var showWebViewTest by remember { mutableStateOf(false) }

    if (showWebViewTest) {
        WebViewTestScreen()
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(setting.theme.backgroundColor)
    ) {
        TopAppBar(
            title = { Text("AllLibrary", color = setting.theme.fontColor) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = setting.theme.topBarColor
            )
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Lorem ipsum dolor sit amet...",
                fontSize = setting.fontSize.toTextUnit(),
                fontFamily = setting.fontStyle.toFontFamily(),
                color = setting.theme.fontColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { showSheet = true }) {
                Text("Open BottomSheet")
            }

            Button(onClick = {
                showWebViewTest = true
            }) {
                Text("Test WebView Font")
            }
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }
}