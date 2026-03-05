package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark
import com.example.autouroute.ui.theme.TextGray

@Composable
fun ProfileScreen(
    onConditionsClick: () -> Unit,
    onLogout: () -> Unit
) {
    var arabicOn by remember { mutableStateOf(true) }
    var frenchOn by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader()
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                Strings.PROFILE_TITLE,
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
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Outlined.Edit, contentDescription = null, tint = TextGray)
                }
                Text("Prénom : ", fontSize = 15.sp, color = TextDark)
                Text("Nom : ", fontSize = 15.sp, color = TextDark)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text("Age : ", fontSize = 15.sp, color = TextDark)
                    Text("Genre : ", fontSize = 15.sp, color = TextDark)
                }
                Text(Strings.PROFILE_PHONE_LABEL, fontSize = 15.sp, color = TextDark)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(androidx.compose.ui.graphics.Color.White, RoundedCornerShape(16.dp))
                    .border(2.dp, Primary, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(Strings.PROFILE_CHANGE_LANG, fontSize = 16.sp, color = TextDark, modifier = Modifier.padding(bottom = 12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier.size(28.dp).background(TextGray, CircleShape)
                    )
                    androidx.compose.foundation.layout.Spacer(Modifier.size(12.dp))
                    Text("Arabe", fontSize = 16.sp, color = TextDark, modifier = Modifier.weight(1f))
                    Switch(checked = arabicOn, onCheckedChange = { arabicOn = it })
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier.size(28.dp).background(TextGray, CircleShape)
                    )
                    androidx.compose.foundation.layout.Spacer(Modifier.size(12.dp))
                    Text("Francais", fontSize = 16.sp, color = TextDark, modifier = Modifier.weight(1f))
                    Switch(checked = frenchOn, onCheckedChange = { frenchOn = it })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(androidx.compose.ui.graphics.Color.White, RoundedCornerShape(16.dp))
                    .border(2.dp, Primary, RoundedCornerShape(16.dp))
                    .padding(20.dp)
                    .clickable { onConditionsClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(Strings.PROFILE_CONDITIONS, fontSize = 16.sp, color = TextDark, modifier = Modifier.weight(1f))
                Icon(Icons.Outlined.MoreVert, contentDescription = null, tint = TextGray)
            }
            PrimaryButton(text = Strings.PROFILE_LOGOUT, onClick = onLogout)
        }
    }
}
