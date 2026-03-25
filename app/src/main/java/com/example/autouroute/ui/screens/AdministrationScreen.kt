package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun AdministrationScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader()
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                Strings.ADMIN_TITLE,
                fontSize = 26.sp,
                color = Primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(androidx.compose.ui.graphics.Color.White, RoundedCornerShape(16.dp))
                    .border(2.dp, Primary, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(Strings.ADMIN_ADRESSE, fontSize = 15.sp, color = TextDark)
                Text(Strings.ADMIN_EMAIL, fontSize = 15.sp, color = TextDark)
                Text(Strings.ADMIN_PHONE, fontSize = 15.sp, color = TextDark)
                Text(Strings.ADMIN_FAX, fontSize = 15.sp, color = TextDark)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(androidx.compose.ui.graphics.Color.White, RoundedCornerShape(16.dp))
                    .border(2.dp, Primary, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text(Strings.ADMIN_HORAIRE, fontSize = 16.sp, color = TextDark)
                    Icon(Icons.Outlined.Schedule, contentDescription = null, modifier = Modifier.size(24.dp))
                }
                Row(modifier = Modifier.padding(top = 16.dp)) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(end = 12.dp)
                            .background(Primary, RoundedCornerShape(4.dp))
                            .align(Alignment.CenterVertically)
                    )
                    Text(Strings.ADMIN_LUNDI, fontSize = 15.sp, color = TextDark)
                }
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(end = 12.dp)
                            .background(Primary, RoundedCornerShape(4.dp))
                    )
                    Text(Strings.ADMIN_VENDREDI, fontSize = 15.sp, color = TextDark)
                }
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(end = 12.dp)
                            .background(Primary, RoundedCornerShape(4.dp))
                    )
                    Text(Strings.ADMIN_SAMEDI, fontSize = 15.sp, color = TextDark)
                }
            }
        }
    }
}
