package com.enesay.financialliteracy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleDialog(
    title: String,
    message: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    negativeButtonText: String,
    positiveButtonText: String,
    colorBtn: Color = Color.Red
) {
    if (showDialog) {
        AlertDialog (
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
            },
            buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { onConfirm() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorBtn,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).padding(end = 8.dp)
                        ) {
                            Text(positiveButtonText)
                        }
                        OutlinedButton(
                            onClick = { onDismiss() },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).padding(start = 8.dp)
                        ) {
                            Text(negativeButtonText)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun SimpleOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(22.dp),
    borderColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = 19.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    width: Dp = 250.dp,
    height: Dp = 45.dp
) {
    OutlinedButton (
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        border = BorderStroke(1.2.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = textColor,
            disabledContentColor = textColor.copy(alpha = 0.4f)
        ),
        modifier = modifier
            .padding(8.dp)
            .width(width)
            .height(height)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = textColor
        )
    }
}
