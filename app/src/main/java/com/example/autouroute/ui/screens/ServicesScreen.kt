package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.theme.BorderGreen
import com.example.autouroute.ui.theme.LightGreenCard
import com.example.autouroute.ui.theme.SubtitleGray
import com.example.autouroute.ui.theme.TextDark

data class ServiceItem(val title: String, val type: String)

@Composable
fun ServicesScreen(
    onServiceClick: (String) -> Unit
) {
    val services = listOf(
        ServiceItem(Strings.ENTRETIEN_ROUTES, "entretien"),
        ServiceItem(Strings.PROBLEME_PEAGE, "peage"),
        ServiceItem(Strings.ECLAIRAGE_DEFECTUEUX, "eclairage"),
        ServiceItem(Strings.PROBLEME_SECURITE, "securite"),
        ServiceItem(Strings.PROBLEME_SERVICES_USAGERS, "usagers")
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                Strings.SERVICES_TITLE,
                fontSize = 30.sp,
                color = BorderGreen,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                Strings.SERVICES_SUBTITLE,
                fontSize = 15.sp,
                color = SubtitleGray,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )
            services.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .background(LightGreenCard, RoundedCornerShape(12.dp))
                        .padding(20.dp, 18.dp)
                        .clickable { onServiceClick(item.type) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        item.title,
                        fontSize = 18.sp,
                        color = TextDark,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        Icons.Filled.Route,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
