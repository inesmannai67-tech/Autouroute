package com.example.autouroute.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

data class FooterTab(val title: String, val icon: ImageVector)

@Composable
fun AppFooter(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        FooterTab("Accueil", Icons.Outlined.Home),
        FooterTab("Observations", Icons.AutoMirrored.Filled.List),
        FooterTab("Administration", Icons.Outlined.Groups),
        FooterTab("Profil", Icons.Outlined.Person)
    )
    NavigationBar(
        modifier = modifier,
        containerColor = androidx.compose.ui.graphics.Color.White,
        tonalElevation = 8.dp
    ) {
        tabs.forEachIndexed { index, tab ->
            val selected = selectedIndex == index
            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                if (selected) Primary.copy(alpha = 0.15f)
                                else androidx.compose.ui.graphics.Color.Transparent,
                                CircleShape
                            )
                            .border(
                                width = if (selected) 0.dp else 1.dp,
                                color = TextDark.copy(alpha = 0.3f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = if (selected) Primary else TextDark
                        )
                    }
                },
                label = { Text(tab.title) },
                selected = selected,
                onClick = { onTabSelected(index) }
            )
        }
    }
}
