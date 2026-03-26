package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.theme.LightGreen
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun SuccessScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                Strings.SUCCESS_TITLE,
                fontSize = 28.sp,
                color = Primary,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
            Text(
                Strings.SUCCESS_SUBTITLE,
                fontSize = 18.sp,
                color = Primary,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Spacer(
                modifier = Modifier
                    .size(120.dp)
                    .background(LightGreen, CircleShape)
            )
            Spacer(Modifier.height(24.dp))
            Text(
                Strings.SUCCESS_THANK1,
                fontSize = 16.sp,
                color = Primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                Strings.SUCCESS_THANK2,
                fontSize = 16.sp,
                color = TextDark
            )
        }
    }
}
