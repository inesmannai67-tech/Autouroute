package com.example.autouroute.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.theme.OtpGray
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun AuthDialog(
    onDismiss: () -> Unit,
    onValidate: () -> Unit
) {
    var code by mutableStateOf("")
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {
            Text(
                Strings.AUTH_MODAL_TITLE,
                fontSize = 22.sp,
                color = Primary,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            Text(
                Strings.AUTH_MODAL_MSG,
                fontSize = 15.sp,
                color = TextDark,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                value = code,
                onValueChange = { code = it.filter { c -> c.isDigit() }.take(4) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("0000") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = OtpGray,
                    unfocusedContainerColor = OtpGray
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
                ) {
                    Text(Strings.ANNULER, fontSize = 18.sp)
                }
                Button(
                    onClick = onValidate,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text(Strings.VALIDER, fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}
