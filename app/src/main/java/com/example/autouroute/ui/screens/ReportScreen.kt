package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.DarkBackground
import com.example.autouroute.ui.theme.InputGray
import com.example.autouroute.ui.theme.LightGreenCard
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark
import com.example.autouroute.ui.theme.TextGray

val reportTypes = mapOf(
    "entretien" to Pair("Entretient des Routes", listOf(
        "Marquages dégradées",
        "Pannaux de signalisation endommagées ou manquantes",
        "Trous ou ondulations sur la route"
    )),
    "peage" to Pair("Probléme de Péage", listOf(
        "Dysfonctionnment du systéme",
        "Ereur de facturation"
    )),
    "eclairage" to Pair("Eclirage défectueux", listOf(
        "Lampadaires non allumés",
        "Lampadaires endommagés",
        "Eclirage interlittent",
        "Eclirage insuifisantes"
    )),
    "securite" to Pair("Problème de Sécurité", listOf(
        "Obstacles et objets sur la route",
        "Barriére de sécurité endommagées",
        "Glisséres de sécurité défectuuses",
        "Travaux routier dangereux"
    )),
    "usagers" to Pair("Probléme de Service aux usagers", listOf(
        "Toilette publiques non opérationnelle",
        "Station de service non opérationnelle"
    ))
)

@Composable
fun ReportScreen(
    type: String,
    onValider: () -> Unit
) {
    val config = reportTypes[type] ?: reportTypes["entretien"]!!
    val (title, options) = config
    val isDark = type == "securite"
    var selected by remember { mutableStateOf<Int?>(null) }

    val bg = if (isDark) DarkBackground else Color.White
    val titleColor = Primary
    val instructionsColor = if (isDark) Color(0xFFCCCCCC) else TextGray
    val cardBg = LightGreenCard
    val photoBg = if (isDark) TextGray else InputGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                title,
                fontSize = 24.sp,
                color = titleColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                Strings.REPORT_INSTRUCTIONS,
                fontSize = 15.sp,
                color = instructionsColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(photoBg, RoundedCornerShape(16.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text(Strings.CHOISIR_PHOTO, fontSize = 16.sp, color = TextDark)
            }
            Spacer(Modifier.height(24.dp))
            options.forEachIndexed { index, label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(cardBg, RoundedCornerShape(12.dp))
                        .padding(16.dp, 14.dp)
                        .clickable { selected = index },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (selected == index) Icons.Default.RadioButtonChecked else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (selected == index) Primary else TextDark,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.size(12.dp))
                    Text(label, fontSize = 16.sp, color = TextDark, modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton(text = Strings.VALIDER, onClick = onValider)
        }
    }
}
