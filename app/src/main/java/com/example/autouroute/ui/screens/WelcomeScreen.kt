package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.LightGreen
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun WelcomeScreen(onDecouvrir: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader(showBell = false)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                Strings.WELCOME_TITLE,
                fontSize = 28.sp,
                color = Primary,
                modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
            )
            Box(
                modifier = Modifier
                    .height(140.dp)
                    .widthIn(max = 280.dp)
                    .fillMaxWidth()
                    .background(LightGreen, RoundedCornerShape(16.dp))
            )
            Spacer(Modifier.height(24.dp))
            Text(
                Strings.WELCOME_DESC,
                fontSize = 16.sp,
                color = TextDark,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 32.dp)
            )
            PrimaryButton(text = Strings.DECOUVRIR, onClick = onDecouvrir)
        }
    }
}
