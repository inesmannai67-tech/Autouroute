package com.example.autouroute.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.R
import com.example.autouroute.data.ApiClient
import com.example.autouroute.data.Strings
import com.example.autouroute.ui.components.AppHeader
import com.example.autouroute.ui.components.PrimaryButton
import com.example.autouroute.ui.theme.LightGreenInput
import com.example.autouroute.ui.theme.PrimaryAlt
import com.example.autouroute.ui.theme.TextDark
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onValider: () -> Unit,
    onAuthRequest: (String) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    var isLoginMode by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

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
        // Shared header across screens
        AppHeader(showBell = false)
        
        Column(modifier = Modifier.padding(24.dp)) {
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (isLoginMode) Strings.LOGIN_TITLE else Strings.REGISTER_TITLE,
                    fontSize = 28.sp,
                    color = PrimaryAlt,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                // Registration/Login illustration
                Image(
                    painter = painterResource(id = R.drawable.ic_login_illustration),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

            if (!isLoginMode) {
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
            }
            LabeledInput(Strings.PHONE, phone, { phone = it })
            
            Spacer(Modifier.height(8.dp))
            
            Text(
                text = if (isLoginMode) Strings.REGISTER_SUBTITLE else Strings.LOGIN_SUBTITLE,
                color = PrimaryAlt,
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isLoginMode = !isLoginMode }
                    .padding(vertical = 8.dp)
            )

            Spacer(Modifier.height(16.dp))
            
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryAlt)
                }
            } else {
                PrimaryButton(
                    text = Strings.VALIDER,
                    enabled = !isLoading,
                    onClick = {
                        // Prevent multiple register/login requests (OTP can be overwritten).
                        if (isLoading) return@PrimaryButton

                        if (phone.isEmpty()) {
                            Toast.makeText(context, "Numéro de téléphone obligatoire", Toast.LENGTH_SHORT).show()
                            return@PrimaryButton
                        }
                        
                        isLoading = true
                        coroutineScope.launch {
                            val action = if (isLoginMode) "login" else "register"
                            val (success, message) = ApiClient.authRequest(
                                action = action,
                                prenom = prenom,
                                nom = nom,
                                genre = genre,
                                age = age,
                                phone = phone
                            )
                            isLoading = false
                            
                            if (success) {
                                Toast.makeText(
                                    context,
                                    "Le code d'authentification a été envoyé au $phone (OTP de test: $message)",
                                    Toast.LENGTH_LONG
                                ).show()
                                onAuthRequest(phone)
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                )
            }
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
