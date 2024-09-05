package com.internshala.instantmart.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.internshala.instantmart.data.InternetItem
import com.google.firebase.auth.FirebaseAuth


enum class InstantMartAppScreen(val tittle:String){
    Start("  InstantMart"),
    Items("Choose Items"),
    Cart("Your Cart")
}

var canNavigateBack =false
val auth= FirebaseAuth.getInstance()



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstantMartApp(instantMartViewModel:InstantMartViewModel= viewModel(),
                   navController: NavHostController= rememberNavController()
) {
    val isVisible by instantMartViewModel.isVisible.collectAsState()


    val backStackEntry by navController.currentBackStackEntryAsState()
    
    val currentScreen= InstantMartAppScreen.valueOf(
        backStackEntry?.destination?.route?:InstantMartAppScreen.Start.name
    )
    canNavigateBack=navController.previousBackStackEntry!=null

    val cartItems by instantMartViewModel.cart_items.collectAsState()
    if(isVisible){
        OfferScreen()
    }
    else{
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Text(
                                text = currentScreen.tittle,
                                fontSize = 26.sp,
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            if (currentScreen==InstantMartAppScreen.Cart){
                                Text(
                                    text = " (${cartItems.size})",
                                    fontSize = 26.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        if(canNavigateBack){
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button")
                            }
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    navController = navController,
                    currentScreen=currentScreen,
                    cartItems= cartItems)
            }
        )
        { scaffoldPadding->
            NavHost(navController = navController, startDestination = InstantMartAppScreen.Start.name,
                Modifier.padding(scaffoldPadding)
            ) {
                composable(route=InstantMartAppScreen.Start.name){
                    StartScreen(instantMartViewModel = instantMartViewModel,
                        onCategoryClicked = {
                            instantMartViewModel.updateSelectedCategory(it)
                            navController.navigate(InstantMartAppScreen.Items.name)
                        }
                    )
                }
                composable(route=InstantMartAppScreen.Items.name){
                    InternetItemScreen(instantMartViewModel=instantMartViewModel,
                        itemUiState = instantMartViewModel.itemUiState
                         )
                }
                composable(route=InstantMartAppScreen.Cart.name){
                    CartScreen(instantMartViewModel=instantMartViewModel, onHomeButtonClicked = {
                        navController.navigate(InstantMartAppScreen.Start.name){
                            popUpTo(0)
                        }
                    })
                }
            }
        }
    }

}

@Composable
fun BottomAppBar(navController: NavHostController,
                 currentScreen:InstantMartAppScreen,
                 cartItems: List<InternetItem>
){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 70.dp,
                vertical = 10.dp
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally  ,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                navController.navigate(InstantMartAppScreen.Start.name){
                    popUpTo(0)
                }
            }
        ) {
             Icon(
                 imageVector = Icons.Outlined.Home,
                 contentDescription ="Home"
                 )
            Text(text = "Home",
                fontSize = 10.sp,)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally  ,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                if (currentScreen!= InstantMartAppScreen.Cart){
                    navController.navigate(InstantMartAppScreen.Cart.name){
                    }
                }
            }
        ) {
            Box {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Cart"
                )
                if (cartItems.isNotEmpty()){
                    Card(
                        modifier = Modifier.align(alignment = Alignment.TopEnd),
                        colors = CardDefaults.cardColors(containerColor = Color.Red),

                        ) {
                        Text(
                            text = cartItems.size.toString(),
                            fontSize = 10.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 1.dp)
                        )
                    }
                }
            }
            Text(text = "Cart",
                fontSize = 10.sp,)
        }

    }
}










