package mr.cat.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.setting.component.model.FontSizeConfig
import mr.cat.setting.component.model.FontSizeOption

@Composable
fun FontSizeSelector(
    selected: FontSizeOption,
    onSelected: (FontSizeOption) -> Unit
) {
    val items = listOf(
        FontSizeConfig(FontSizeOption.SMALL, "Kecil", 12.sp),
        FontSizeConfig(FontSizeOption.STANDARD, "Standar", 14.sp),
        FontSizeConfig(FontSizeOption.LARGE, "Besar", 18.sp)
    )

    Row(modifier = Modifier.fillMaxWidth()) {

        items.forEachIndexed { index, item ->

            val shape = when (index) {
                0 -> RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp
                )

                items.lastIndex -> RoundedCornerShape(
                    topEnd = 12.dp,
                    bottomEnd = 12.dp
                )

                else -> RoundedCornerShape(0.dp)
            }

            FontSizeItem(
                text = item.label,
                fontSize = item.fontSize,
                isSelected = selected == item.option,
                shape = shape,
                modifier = Modifier.weight(1f)
            ) {
                onSelected(item.option)
            }
        }
    }
}

@Composable
private fun FontSizeItem(
    text: String,
    fontSize: androidx.compose.ui.unit.TextUnit,
    isSelected: Boolean,
    shape: RoundedCornerShape,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .height(48.dp)
            .clip(shape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            color = textColor
        )
    }
}
