package mr.cat.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.setting.component.model.ThemeOption

@Composable
fun ThemeSelector(
    themes: List<ThemeOption>,
    selectedId: String,
    onSelected: (ThemeOption) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(themes) { theme ->
            ThemeItem(
                theme = theme,
                isSelected = theme.id == selectedId,
                onClick = { onSelected(theme) }
            )
        }
    }
}

@Composable
private fun ThemeItem(
    theme: ThemeOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) theme.fontColor else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(theme.backgroundColor)
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = theme.label,
            color = theme.fontColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        // Radio button manual
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 1.5.dp,
                    color = theme.fontColor,
                    shape = CircleShape
                )
                .padding(3.dp)
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(theme.fontColor)
                        .padding(6.dp)
                )
            } else {
                Box(modifier = Modifier.padding(6.dp))
            }
        }
    }
}