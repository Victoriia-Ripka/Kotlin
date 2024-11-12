package com.example.pw_5.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.scale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun FancyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
) {
    // State to manage scale animation on press
    var isPressed by remember { mutableStateOf(false) }

    // Animation for scaling effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = if (isPressed) Color(0xFF3700B3) else Color(0xFF6200EE),
                shape = RoundedCornerShape(12.dp)
            ) // Change background color when pressed
            .clickable(
                onClick = {
                    isPressed = true
                    onClick()
                    isPressed = false
                }
            ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) // Use containerColor instead of backgroundColor
    ) {
        Text(
            text = text,
            style = textStyle.copy(color = Color.White),
            modifier = Modifier.padding(4.dp)
        )
    }
}
