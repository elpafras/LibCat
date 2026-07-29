package mr.cat.libcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.libcat.ui.theme.LibCatTheme
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.SettingBottomSheet

class HebrewGreekActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibCatTheme {
                HebrewGreekScreen(onBack = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HebrewGreekScreen(onBack: () -> Unit) {
    var showSheet by remember { mutableStateOf(value = false) }
    val setting = LocalLibCatSettings.current
    val themeColors = setting.theme.colors
    val fontSize = setting.fontSize.sp

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hebrew & Greek Demo") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.topBar,
                    titleContentColor = themeColors.topBarText,
                    navigationIconContentColor = themeColors.topBarText,
                    actionIconContentColor = themeColors.topBarText
                )
            )
        },
        containerColor = themeColors.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // ... (rest of the column content remains the same)
            // Greek Section
            Column {
                Text(
                    text = "Teks Yunani (Greek) - Yohanes 1:1",
                    style = MaterialTheme.typography.titleLarge,
                    color = themeColors.topBar
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ἐν ἀρχῇ ἦν ὁ λόגος, καὶ ὁ λόגος ἦν πρὸς τὸν θεόν, καὶ θεὸς ἦν ὁ λόגος.",
                    fontSize = (fontSize.value + 4).sp,
                    color = themeColors.text
                )
                Text(
                    text = "Terjemahan: Pada mulanya adalah Firman; Firman itu bersama-sama dengan Allah dan Firman itu adalah Allah.",
                    style = MaterialTheme.typography.labelMedium,
                    color = themeColors.text.copy(alpha = 0.7f)
                )
            }

            HorizontalDivider(color = themeColors.text.copy(alpha = 0.1f))

            // Hebrew Section
            Column {
                Text(
                    text = "Teks Ibrani (Hebrew) - Kejadian 1:1",
                    style = MaterialTheme.typography.titleLarge,
                    color = themeColors.topBar
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                // Hebrew is RTL
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        text = "בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הַאָרֶץ׃",
                        fontSize = (fontSize.value + 6).sp,
                        color = themeColors.text,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Terjemahan: Pada mulanya Allah menciptakan langit dan bumi.",
                    style = MaterialTheme.typography.labelMedium,
                    color = themeColors.text.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = themeColors.topBar.copy(alpha = 0.1f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Catatan:",
                        style = MaterialTheme.typography.titleMedium,
                        color = themeColors.topBar
                    )
                    Text(
                        text = "Teks Ibrani di atas ditampilkan menggunakan LayoutDirection.Rtl untuk memastikan urutan karakter dan tanda baca (niqqud) tampil dengan benar dari kanan ke kiri.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = themeColors.text
                    )
                }
            }
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }
}
