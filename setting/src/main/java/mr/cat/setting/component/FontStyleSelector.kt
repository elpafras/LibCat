package mr.cat.setting.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mr.cat.setting.R
import mr.cat.setting.component.model.FontCategory
import mr.cat.setting.component.model.FontStyleOption
import mr.cat.setting.component.model.toFontFamily
import mr.cat.setting.component.model.toLabel

@Composable
fun FontStyleSelector(
    selected: FontStyleOption,
    onSelected: (FontStyleOption) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "arrow_rotation"
    )

    // group font by category
    val grouped = FontStyleOption.entries.groupBy { it.category }

    Column(modifier = modifier.fillMaxWidth()) {

        // trigger box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selected.toLabel(),
                    fontFamily = selected.toFontFamily(),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow_drop_down_24px),
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation)
                )
            }
        }

        // dropdown dengan sub-group
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            FontCategory.entries.forEach { category ->
                val fontsInCategory = grouped[category] ?: return@forEach

                // header kategori
                Text(
                    text = category.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                // item per font
                fontsInCategory.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.toLabel(),
                                fontFamily = option.toFontFamily()
                            )
                        },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }

                // divider antar kategori kecuali yang terakhir
                if (category != FontCategory.entries.last()) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}