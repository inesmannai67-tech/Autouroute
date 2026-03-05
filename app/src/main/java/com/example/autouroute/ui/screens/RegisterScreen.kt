package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.LightGreenInput
import com.example.autouroute.ui.theme.PrimaryAlt
import com.example.autouroute.ui.theme.TextDark

@Composable
fun RegisterScreen(
    onValider: () -> Unit,
    onAuthRequest: () -> Unit
) {
    var prenom by remember { mutableStateOf("") }
    var nom by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppHeader(showBell = false)
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                Strings.REGISTER_TITLE,
                fontSize = 26.sp,
                color = PrimaryAlt,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            LabeledInput(Strings.PRENOM, prenom, { prenom = it })
            LabeledInput(Strings.NOM, nom, { nom = it })
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    LabeledInput(Strings.GENRE, genre, { genre = it })
                }
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    LabeledInput(Strings.AGE, age, { age = it })
                }
            }
            LabeledInput(Strings.PHONE, phone, { phone = it })
            Spacer(Modifier.height(16.dp))
            PrimaryButton(text = Strings.VALIDER, onClick = onAuthRequest)
        }
    }
}

@Composable
private fun LabeledInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Text(
        label,
        fontSize = 16.sp,
        color = TextDark,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = LightGreenInput,
            unfocusedContainerColor = LightGreenInput,
            focusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
