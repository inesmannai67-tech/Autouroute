package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.example.autouroute.ui.theme.HeaderGrayDark
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.StatusGreen
import com.example.autouroute.ui.theme.StatusOrange
import com.example.autouroute.ui.theme.StatusRed
import com.example.autouroute.ui.theme.TextGray
import com.example.autouroute.ui.theme.TextDark

@Composable
fun ObservationsScreen() {
    val items = listOf(
        Triple("Marquage dégradée", "en attente de traitement.", StatusRed),
        Triple("Eclirage défectueux", "en cours de traitement.", StatusOrange),
        Triple("Probléme de péage", "exécutée.", StatusGreen)
    )
    val dates = listOf("3 août 2023, 15:25", "1 août 2023, 10:02", "29 juillet 2023, 15:25")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader()
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                Strings.OBSERVATIONS_TITLE,
                fontSize = 26.sp,
                color = Primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
            items.forEachIndexed { index, (desc, status, statusColor) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .background(HeaderGrayDark, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .width(6.dp)
                            .height(72.dp)
                            .padding(end = 12.dp)
                            .background(statusColor, RoundedCornerShape(3.dp))
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Votre observation \"$desc\" est ",
                            fontSize = 15.sp,
                            color = TextDark
                        )
                        Text(
                            status,
                            fontSize = 15.sp,
                            color = statusColor
                        )
                        Text(
                            dates.getOrNull(index) ?: "",
                            fontSize = 13.sp,
                            color = TextGray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
