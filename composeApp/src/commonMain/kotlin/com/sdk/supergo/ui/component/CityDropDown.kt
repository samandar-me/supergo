package com.sdk.supergo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropDown(
    title: String,
    expanded: Boolean,
    selectedCity: String,
    onChanged: () -> Unit,
    onSelected: (String) -> Unit,
    cityList: List<String>
) {
    Column {
        Text(text = title, color = Color.Gray)
        Spacer(Modifier.height(6.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                onChanged()
            },
        ) {
            Row(
                modifier = Modifier.menuAnchor().fillMaxWidth().height(55.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedCity,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = onChanged
            ) {
                cityList.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                fontWeight = if (item == selectedCity) FontWeight.Bold else null,
                                color = if (item == selectedCity) Color.Black else Color.Gray,
                                fontSize = 20.sp
                            )
                        },
                        onClick = {
                            onSelected(item)
                        }
                    )
                }
            }
        }
    }
}