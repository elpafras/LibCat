package mr.cat.libcat.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.component.model.toFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsShowcaseScreen(
    onBack: () -> Unit,
) {
    val setting = LocalLibCatSettings.current
    val themeColors = setting.theme.colors
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.sp

    var showSheet by remember { mutableStateOf(value = false) }
    var showDialog by remember { mutableStateOf(value = false) }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Settings Showcase", 
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = themeColors.topBarText)
                    }
                },
                actions = {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = themeColors.topBarText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.topBar,
                    titleContentColor = themeColors.topBarText
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = themeColors.topBar,
                contentColor = themeColors.topBarText
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home", fontFamily = fontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = themeColors.topBar,
                        selectedTextColor = themeColors.topBarText,
                        indicatorColor = themeColors.topBarText,
                        unselectedIconColor = themeColors.topBarText.copy(alpha = 0.6f),
                        unselectedTextColor = themeColors.topBarText.copy(alpha = 0.6f)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile", fontFamily = fontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = themeColors.topBarText.copy(alpha = 0.6f),
                        unselectedTextColor = themeColors.topBarText.copy(alpha = 0.6f)
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = themeColors.topBar,
                contentColor = themeColors.topBarText
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        containerColor = themeColors.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search dummy...", fontFamily = fontFamily) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = themeColors.text,
                        unfocusedBorderColor = themeColors.text.copy(alpha = 0.5f),
                        focusedLabelColor = themeColors.text,
                        cursorColor = themeColors.text
                    )
                )
            }

            item {
                Text(
                    "Section Header",
                    fontFamily = fontFamily,
                    fontSize = (fontSize.value + 4).sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.text
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = themeColors.text.copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Standard Card",
                            fontFamily = fontFamily,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.text
                        )
                        Text(
                            "This card uses theme text color with 10% alpha as background.",
                            fontFamily = fontFamily,
                            fontSize = fontSize,
                            color = themeColors.text
                        )
                    }
                }
            }

            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = themeColors.background,
                        contentColor = themeColors.text
                    ),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Elevated Card",
                            fontFamily = fontFamily,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "This card has elevation and follows theme background.",
                            fontFamily = fontFamily,
                            fontSize = fontSize
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = themeColors.topBar,
                            contentColor = themeColors.topBarText
                        )
                    ) {
                        Text("Filled", fontFamily = fontFamily)
                    }
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = themeColors.text
                        ),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = androidx.compose.ui.graphics.SolidColor(themeColors.text))
                    ) {
                        Text("Outlined", fontFamily = fontFamily)
                    }
                }
            }

            item {
                TextButton(onClick = {}) {
                    Text(
                        "Text Button",
                        fontFamily = fontFamily,
                        color = themeColors.text,
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                    )
                }
            }

            item {
                // Empty State Demo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = themeColors.text.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "No Data Available",
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                        color = themeColors.text.copy(alpha = 0.5f)
                    )
                }
            }

            items(3) { index ->
                ListItem(
                    headlineContent = { Text("Item List $index", fontFamily = fontFamily, color = themeColors.text) },
                    supportingContent = { Text("Subtitle for item $index", fontFamily = fontFamily, color = themeColors.text.copy(alpha = 0.7f)) },
                    leadingContent = { Icon(Icons.Default.Favorite, contentDescription = null, tint = themeColors.text) },
                    colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                )
                HorizontalDivider(color = themeColors.text.copy(alpha = 0.1f))
            }
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK", color = themeColors.text, fontFamily = fontFamily)
                }
            },
            title = { Text("Theme Dialog", fontFamily = fontFamily, color = themeColors.text) },
            text = { Text("This dialog also follows your theme settings.", fontFamily = fontFamily, color = themeColors.text) },
            containerColor = themeColors.background
        )
    }
}
