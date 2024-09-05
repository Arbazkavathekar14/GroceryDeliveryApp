package com.internshala.instantmart.ui
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
 import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
 import com.internshala.instantmart.data.DataSource
import com.internshala.instantmart.R


@Composable
fun StartScreen(instantMartViewModel:InstantMartViewModel,
                onCategoryClicked:(Int)->Unit
){
    val context= LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        item (
            span = { GridItemSpan(2)}
        ){
            Column {
                Image(painter = painterResource(id = R.drawable.category), contentDescription ="Offer",
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
                        text = "Shop by Category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp))
                }
            }
            
        }
        
        items(DataSource.loadCategories()){
            CategoryCard(
                context = context,
                stringResourceId = it.stringResourceId,
                imageResourceId = it.imageResourceId,
                instantMartViewModel = instantMartViewModel,
                onCategoryClicked = onCategoryClicked
                )
        }
    }
}
@Composable
fun CategoryCard(
    context:Context,
    stringResourceId:Int,
    imageResourceId:Int,
    instantMartViewModel:InstantMartViewModel,
    onCategoryClicked: (Int) -> Unit
)
{
    val currentCategory = stringResource(id = stringResourceId)
    Card (modifier = Modifier
        .width(150.dp)
        .height(200.dp)
        .clickable {
            instantMartViewModel.updateClickState(currentCategory)
            Toast
                .makeText(context, "clickable", Toast.LENGTH_SHORT)
                .show()
            onCategoryClicked(stringResourceId)
        },
        colors = CardDefaults.cardColors(
            Color(248,221,248,255)
        )

    ) {
        Column(modifier = Modifier
            .padding(5.dp)
            .fillMaxSize() ,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text (text =  currentCategory ,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Image(painter = painterResource(id = imageResourceId),
                contentDescription ="Fruits and Vegetables",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }

}