package com.internshala.instantmart.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.internshala.instantmart.R
import com.internshala.instantmart.data.InternetItem

@Composable
fun ItemScreen(
    instantMartViewModel:InstantMartViewModel,
    items:List<InternetItem>)
{
    val instantMartUiState by instantMartViewModel.uiState.collectAsState()
    val selectedCategory= stringResource(id =instantMartUiState.selectedCategory )
    val internetDatabase=items.filter {
        it.itemCategory.lowercase() == selectedCategory.lowercase()
    }


    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {

        item (
            span = { GridItemSpan(2) }
        ){
            Column(
             )
            {
                Image(painter = painterResource(id = R.drawable.items), contentDescription ="Offer",
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Card (
                    colors = CardDefaults.cardColors(
                        Color(108,194,111,255)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                ){
                    Text(
                        text =  "${stringResource(id = instantMartUiState.selectedCategory)} (${internetDatabase.size})",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp))
                }
            }

        }
        items(
            internetDatabase
        ){
            ItemCard(
                stringResourceId = it.itemName,
                imageResourceId = it.imageUrl,
                itemQuantity = it.itemQuantity,
                itemPrice =it.itemPrice ,
                instantMartViewModel=instantMartViewModel
                )
        }

    }
}

@Composable
fun InternetItemScreen( instantMartViewModel:InstantMartViewModel,
                        itemUiState:InstantMartViewModel.ItemUiState
) {
    when(itemUiState){
        is InstantMartViewModel.ItemUiState.Success->{
            ItemScreen(instantMartViewModel = instantMartViewModel, items = itemUiState.successItems)
        }
        is InstantMartViewModel.ItemUiState.Loading->{
            LoadingScreen()
        }
        else ->{
            ErrorScreen(instantMartViewModel=instantMartViewModel)
        }
    }
}
@Composable
fun ItemCard(
    stringResourceId: String,
    imageResourceId: String,
    itemQuantity:String,
    itemPrice:Int,
    instantMartViewModel: InstantMartViewModel
){
    val context = LocalContext.current
    Column(modifier = Modifier.width(150.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                Color(248,221,248,255)
            )
        ) {
            Box {
                AsyncImage(
                    model = imageResourceId,
                    contentDescription = stringResourceId,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End
                ){
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(244,67,54,255)
                        ),
                    ) {
                        Text(
                            text = "25% Off",
                            color = Color.White,
                            fontSize = 8.sp,
                            modifier = Modifier.padding(
                                horizontal = 5.dp,
                                vertical = 2.dp
                            )
                            )
                    }
                }
            }

        }
        Text(
            text = stringResourceId,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            maxLines = 1,
            textAlign = TextAlign.Left
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Text(
                    text = "Rs. $itemPrice",
                    fontSize = 6.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    color = Color(109,109,109,255),
                    textDecoration =  TextDecoration.LineThrough
                )
                Text(
                    text = "Rs. ${itemPrice*75/100}",
                    fontSize = 10.sp ,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    color = Color(255,116,105,255)
                )
            }
            Text(
                text = itemQuantity,
                fontSize = 14.sp,
                maxLines = 1,
                color = Color(114,114,114,255)
            )
        }
        Card (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .clickable {
                    instantMartViewModel.addToDatabase(
                        InternetItem(
                            itemName = stringResourceId,
                            itemQuantity= itemQuantity,
                            itemPrice = itemPrice,
                            imageUrl = imageResourceId,
                            itemCategory = ""
                        )
                    )
                    Toast
                        .makeText(context, "Added to Cart ", Toast.LENGTH_SHORT)
                        .show()
                },
            colors = CardDefaults.cardColors(
                containerColor = Color(108,194,111,255  )
            )
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Add to Cart",
                    fontSize = 11.sp,
                    color = Color.White
                    )
            }

        }
        
    }
}

@Composable
fun LoadingScreen(){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
     Image(painter = painterResource(id = R.drawable.loadingbar), contentDescription = "Loading")
    }
}

@Composable
fun ErrorScreen(instantMartViewModel:InstantMartViewModel){
    Column(  verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.error), contentDescription = "Error")
        Text(
            text = "Oops! No Internet connection.\n Please try again",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center
            )
        Button(onClick = {
            instantMartViewModel.getMartItems()
        }
        ) {
            Text(text = "Retry")
        }
    }

}











