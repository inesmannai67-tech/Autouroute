package com.example.autouroute.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.theme.DarkBackground
import com.example.autouroute.ui.theme.HeaderGray
import com.example.autouroute.ui.theme.LightGreenCard
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun AppHeader(
    showBell: Boolean = true,
    onBellClick: () -> Unit = {},
    dark: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bg = if (dark) DarkBackground else HeaderGray
    val arabicColor = if (dark) Color(0xFFE0E0E0) else TextDark
    Row(
        modifier = modifier
            .background(bg)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(56.dp, 48.dp)
                    .background(LightGreenCard, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Route,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                Strings.ARABIC_TITLE,
                fontSize = 15.sp,
                color = arabicColor,
                modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
            )
            Text(
                Strings.LATIN_TITLE,
                fontSize = 12.sp,
                color = Primary
            )
        }
        if (showBell) {
            IconButton(onClick = onBellClick) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
