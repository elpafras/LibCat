package mr.cat.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mr.cat.setting.component.model.FontSizeOption
import mr.cat.setting.component.FontSizeSelector
import mr.cat.setting.component.FontStyleSelector
import mr.cat.setting.component.ThemeSelector
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.ThemeOption
import mr.cat.setting.component.model.defaultThemes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontBottomSheet(
    show: Boolean,
    selected: FontSizeOption,
    onSelected: (FontSizeOption) -> Unit,
    selectedStyle: FontStyleOption,
    onStyleSelected: (FontStyleOption) -> Unit,
    selectedThemeId: String,
    onThemeSelected: (ThemeOption) -> Unit,
    themes: List<ThemeOption> = defaultThemes,
    onDismiss: () -> Unit
) {
    if (show) {
        ModalBottomSheet(onDismissRequest = onDismiss) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ukuran Font")
                Spacer(modifier = Modifier.height(8.dp))
                FontSizeSelector(selected = selected, onSelected = onSelected)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Gaya Font")
                Spacer(modifier = Modifier.height(8.dp))
                FontStyleSelector(selected = selectedStyle, onSelected = onStyleSelected)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Tema", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                ThemeSelector(
                    themes = themes,
                    selectedId = selectedThemeId,
                    onSelected = onThemeSelected
                )
            }
        }
    }
}