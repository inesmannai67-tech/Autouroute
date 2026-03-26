package com.example.autouroute.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.R
import com.example.autouroute.data.AppLanguage
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.LightGreenCard
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun LanguageScreen(
    onValider: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader(showBell = false)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = Strings.LANGUAGE_TITLE,
                fontSize = 28.sp,
                color = Primary,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = Strings.LANGUAGE_SUBTITLE,
                fontSize = 16.sp,
                color = TextDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Arabic Option
            LanguageOption(
                label = Strings.ARABE,
                iconRes = R.drawable.ic_flag_tunisia,
                isSelected = Strings.getLanguage() == AppLanguage.ARABIC,
                onClick = { Strings.setLanguage(AppLanguage.ARABIC) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // French Option
            LanguageOption(
                label = Strings.FRANCAIS,
                iconRes = R.drawable.ic_flag_france,
                isSelected = Strings.getLanguage() == AppLanguage.FRENCH,
                onClick = { Strings.setLanguage(AppLanguage.FRENCH) }
            )

            Spacer(modifier = Modifier.height(40.dp))
            PrimaryButton(text = Strings.VALIDER, onClick = onValider)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = Strings.LANGUAGE_NOTE,
                fontSize = 14.sp,
                color = TextDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun LanguageOption(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Both options use the same background color per the design unless we want to highlight the selected one
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(LightGreenCard)
            .clickable { onClick() }
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(androidx.compose.ui.graphics.Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
    }
}
