package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.LightGreen
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun LanguageScreen(
    onValider: () -> Unit
) {
    var selected by remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader(showBell = false)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                Strings.LANGUAGE_TITLE,
                fontSize = 24.sp,
                color = Primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                Strings.LANGUAGE_SUBTITLE,
                fontSize = 15.sp,
                color = TextDark,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightGreen)
                    .padding(16.dp, 20.dp)
                    .clickable { selected = "arabe" },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(TextDark))
                Spacer(Modifier.width(14.dp))
                Text(Strings.ARABE, fontSize = 18.sp, color = TextDark)
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightGreen)
                    .padding(16.dp, 20.dp)
                    .clickable { selected = "francais" },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(TextDark))
                Spacer(Modifier.width(14.dp))
                Text(Strings.FRANCAIS, fontSize = 18.sp, color = TextDark)
            }
            Spacer(Modifier.height(24.dp))
            PrimaryButton(text = Strings.VALIDER, onClick = onValider)
            Spacer(Modifier.height(24.dp))
            Text(Strings.LANGUAGE_NOTE, fontSize = 12.sp, color = TextDark)
        }
    }
}
