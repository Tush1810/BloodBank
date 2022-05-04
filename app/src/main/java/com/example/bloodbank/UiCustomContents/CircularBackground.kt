package com.example.bloodbank.UiCustomContents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
@Preview
fun CircleShapeDemo(){
    CircularBackground(shape = CircleShape)
}

@Composable
fun CircularBackground(shape: Shape,
                       size: Dp? = null,
                       backgroundColor :Color = Color.White,
                       onTop: @Composable() () -> Unit = {}
){
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min),
        contentAlignment = Alignment.Center
    ) {
        if(size==null){
            Box(
                modifier = Modifier.fillMaxSize().clip(shape).background(backgroundColor),
            )
        }else{
            Box(
                modifier = Modifier.size(size).clip(shape).background(backgroundColor),
            )
        }
        onTop()
    }
}
