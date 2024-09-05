package com.internshala.instantmart.ui

 import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.padding
 import androidx.compose.runtime.Composable
import com.internshala.instantmart.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.res.painterResource
 import androidx.compose.ui.unit.dp


@Composable
fun OfferScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(23, 105, 201, 255))
    ) {
        Image(painter = painterResource(id = R.drawable.offer),
            contentDescription = "Offer",
            modifier = Modifier.padding(20.dp))

    }

}




