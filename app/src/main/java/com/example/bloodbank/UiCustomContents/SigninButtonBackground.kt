package com.example.bloodbank.UiCustomContents

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun signinButton(){
    Canvas(
        modifier = Modifier
            .width(125.dp)
            .height(55.dp),
        onDraw = {
            var canvasWidth=size.width
            var canvasHeight=size.height


            drawRoundRect(
                color= Color(0xFFe9e4e4),
                cornerRadius = CornerRadius(14.dp.toPx()),
                size = Size(canvasWidth,canvasHeight)
            )

            drawRoundRect(
                color= Color(0xFFe6e0e0),
                cornerRadius = CornerRadius(14.dp.toPx()),
                size = Size(canvasWidth,canvasHeight),
                style = Stroke(width = 3.dp.toPx())
            )
        }
    )
}

@Composable
@Preview
fun displaySignInButton(){
    signinButton()
}