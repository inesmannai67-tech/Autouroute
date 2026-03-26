package com.example.autouroute.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autouroute.ui.theme.Primary

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        shape = RoundedCornerShape(12.dp),
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
