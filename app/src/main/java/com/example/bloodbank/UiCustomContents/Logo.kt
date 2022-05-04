package com.example.bloodbank.UiCustomContents

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.example.bloodbank.R

@Composable
fun drawLogo(mipMap:Int){
    ResourcesCompat.getDrawable(
        LocalContext.current.resources,
        mipMap, LocalContext.current.theme
    )?.let { drawable ->
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        CircularBackground(
            shape = CircleShape,
            size = 120.dp,
            backgroundColor = Color.Transparent
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Adaptive Icon",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun displayLogo(){
    drawLogo(R.mipmap.blood_bank_icon_round)
}
