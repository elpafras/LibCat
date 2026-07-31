package mr.cat.libcat.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.libcat.ui.theme.LocalLibCatSettings
import mr.cat.setting.SettingBottomSheet
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.utility.ThemeRegistry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsShowcaseScreen(
    onBack: () -> Unit,
) {
    val setting = LocalLibCatSettings.current
    val themeId = setting.theme.id
    val themeColors = remember(themeId) { ThemeRegistry.resolveThemeColors(themeId) }
    val fontFamily = setting.fontStyle.toFontFamily()
    val fontSize = setting.fontSize.sp

    var showSheet by remember { mutableStateOf(value = false) }
    var showDemoSheet by remember { mutableStateOf(value = false) }
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = themeColors.onPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = themeColors.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeColors.primary,
                    titleContentColor = themeColors.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = themeColors.primary,
                contentColor = themeColors.onPrimary
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home", fontFamily = fontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = themeColors.primary,
                        selectedTextColor = themeColors.onPrimary,
                        indicatorColor = themeColors.onPrimary,
                        unselectedIconColor = themeColors.onPrimary.copy(alpha = 0.6f),
                        unselectedTextColor = themeColors.onPrimary.copy(alpha = 0.6f)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile", fontFamily = fontFamily) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = themeColors.onPrimary.copy(alpha = 0.6f),
                        unselectedTextColor = themeColors.onPrimary.copy(alpha = 0.6f)
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = themeColors.primary,
                contentColor = themeColors.onPrimary,
                shape = FloatingActionButtonDefaults.shape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = if (ThemeRegistry.calculateContrastRatio(themeColors.primary, themeColors.background) < 1.5) {
                        Modifier.size(56.dp).border(1.dp, themeColors.onBackground.copy(alpha = 0.1f), FloatingActionButtonDefaults.shape)
                    } else Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
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
                        focusedBorderColor = themeColors.onBackground,
                        unfocusedBorderColor = themeColors.onBackground.copy(alpha = 0.5f),
                        focusedLabelColor = themeColors.onBackground,
                        cursorColor = themeColors.onBackground
                    )
                )
            }

            item {
                Text(
                    "Section Header",
                    fontFamily = fontFamily,
                    fontSize = (fontSize.value + 4).sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.onBackground
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = themeColors.onBackground.copy(alpha = 0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Standard Card",
                            fontFamily = fontFamily,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.onBackground
                        )
                        Text(
                            "This card uses theme text color with 10% alpha as background.",
                            fontFamily = fontFamily,
                            fontSize = fontSize,
                            color = themeColors.onBackground
                        )
                    }
                }
            }

            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = themeColors.background,
                        contentColor = themeColors.onBackground
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
                            containerColor = themeColors.primary,
                            contentColor = themeColors.onPrimary
                        ),
                        border = if (ThemeRegistry.calculateContrastRatio(themeColors.primary, themeColors.background) < 1.5) {
                            androidx.compose.foundation.BorderStroke(1.dp, themeColors.onBackground.copy(alpha = 0.1f))
                        } else null
                    ) {
                        Text("Filled", fontFamily = fontFamily)
                    }
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = themeColors.onBackground
                        ),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = androidx.compose.ui.graphics.SolidColor(themeColors.onBackground))
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
                        color = themeColors.onBackground,
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                    )
                }
            }

            item {
                Text(
                    "Text Highlighting",
                    fontFamily = fontFamily,
                    fontSize = (fontSize.value + 4).sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.onBackground
                )
            }

            item {
                val highlightColor = MaterialTheme.colorScheme.secondaryContainer
                val annotatedString = buildAnnotatedString {
                    append("You can use the ")
                    withStyle(SpanStyle(background = highlightColor)) {
                        append("themed highlight color")
                    }
                    append(" to draw attention to specific parts of your text. This color automatically adapts to ensure readability.")
                }

                Text(
                    text = annotatedString,
                    fontFamily = fontFamily,
                    fontSize = fontSize,
                    color = themeColors.onBackground
                )
            }

            item {
                Text(
                    "Sheets & Dialogs",
                    fontFamily = fontFamily,
                    fontSize = (fontSize.value + 4).sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.onBackground
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = themeColors.primary,
                            contentColor = themeColors.onPrimary
                        )
                    ) {
                        Text("Show Dialog", fontFamily = fontFamily)
                    }
                    OutlinedButton(
                        onClick = { showDemoSheet = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = themeColors.onBackground
                        ),
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = androidx.compose.ui.graphics.SolidColor(themeColors.onBackground))
                    ) {
                        Text("Show Sheet", fontFamily = fontFamily)
                    }
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
                        tint = themeColors.onBackground.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "No Data Available",
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                        color = themeColors.onBackground.copy(alpha = 0.6f)
                    )
                }
            }

            items(3) { index ->
                ListItem(
                    headlineContent = { Text("Item List $index", fontFamily = fontFamily, color = themeColors.onBackground) },
                    supportingContent = { Text("Subtitle for item $index", fontFamily = fontFamily, color = themeColors.onBackground.copy(alpha = 0.7f)) },
                    leadingContent = { Icon(Icons.Default.Favorite, contentDescription = null, tint = themeColors.onBackground) },
                    colors = ListItemDefaults.colors(containerColor = androidx.compose.ui.graphics.Color.Transparent)
                )
                HorizontalDivider(color = themeColors.onBackground.copy(alpha = 0.1f))
            }
        }

        SettingBottomSheet(
            show = showSheet,
            onDismiss = { showSheet = false }
        )

        if (showDemoSheet) {
            ModalBottomSheet(
                onDismissRequest = { showDemoSheet = false },
                containerColor = themeColors.background,
                contentColor = themeColors.onBackground,
                dragHandle = {
                    BottomSheetDefaults.DragHandle(color = themeColors.onBackground.copy(alpha = 0.3f))
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp, start = 24.dp, end = 24.dp, top = 8.dp)
                ) {
                    Text(
                        "Demo Bottom Sheet",
                        fontFamily = fontFamily,
                        fontSize = (fontSize.value + 4).sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "This bottom sheet is part of the showcase. It uses the background and text colors defined in your active theme, and respects your chosen font family and size.",
                        fontFamily = fontFamily,
                        fontSize = fontSize,
                        color = themeColors.onBackground.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { showDemoSheet = false },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = themeColors.primary,
                            contentColor = themeColors.onPrimary
                        )
                    ) {
                        Text("Close Sheet", fontFamily = fontFamily)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK", color = themeColors.onBackground, fontFamily = fontFamily)
                }
            },
            title = { Text("Theme Dialog", fontFamily = fontFamily, color = themeColors.onBackground) },
            text = { Text("This dialog also follows your theme settings.", fontFamily = fontFamily, color = themeColors.onBackground) },
            containerColor = themeColors.background
        )
    }
}
