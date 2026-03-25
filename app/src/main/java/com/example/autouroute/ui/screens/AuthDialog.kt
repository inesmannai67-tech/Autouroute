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
import android.widget.Toast
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.autouroute.data.ApiClient
import com.example.autouroute.data.Strings
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import kotlinx.coroutines.launch
import com.example.autouroute.ui.theme.OtpGray
import com.example.autouroute.ui.theme.Primary
import com.example.autouroute.ui.theme.TextDark

@Composable
fun AuthDialog(
    phone: String,
    onDismiss: () -> Unit,
    onValidate: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
    var isLoading by mutableStateOf(false)
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
                    Text(Strings.ANNULER, fontSize = 16.sp)
                }
                Button(
                    onClick = {
                        if (code.length < 4) {
                            Toast.makeText(context, "Code invalide", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        
                        isLoading = true
                        coroutineScope.launch {
                            val (success, message) = ApiClient.authRequest(
                                action = "verify",
                                phone = phone,
                                code = code
                            )
                            isLoading = false
                            
                            if (success) {
                                onValidate()
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    if (isLoading) {
                        androidx.compose.material3.CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(Strings.VALIDER, fontSize = 16.sp, color = Color.White)
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                text = "Renvoyer le code",
                color = Primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        coroutineScope.launch {
                            val (success, message) = ApiClient.authRequest(
                                action = "login", // Reuse login to resend OTP
                                phone = phone
                            )
                            if (success) {
                                Toast.makeText(context, "Nouveau code envoyé (Test: $message)", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                textAlign = TextAlign.Center
            )
        }
    }
}
