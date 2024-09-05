package com.internshala.instantmart.data

import androidx.annotation.StringRes
import com.internshala.instantmart.R

object DataSource {
    fun loadCategories():List<Categories>{
        return listOf(
            Categories(stringResourceId = R.string.Fresh_Fruits, imageResourceId = R.drawable.fruits),
            Categories(stringResourceId = R.string.Beverages, imageResourceId = R.drawable.softy_drinks),
            Categories(stringResourceId = R.string.Munchies, imageResourceId = R.drawable.munchies),
            Categories(stringResourceId = R.string.Pet_Food, imageResourceId = R.drawable.pet_food),
            Categories(stringResourceId = R.string.Groceries, imageResourceId = R.drawable.groceries),
            Categories(stringResourceId = R.string.Bath_and_Body, imageResourceId = R.drawable.bath_and_body),
            Categories(stringResourceId = R.string.Kitchen_Essentials, imageResourceId = R.drawable.kitchen_essentials),
            Categories(stringResourceId = R.string.Processed_Food, imageResourceId = R.drawable.processed_food),
            Categories(stringResourceId = R.string.Fruits_and_vegetables, imageResourceId = R.drawable.fruits_and_vegetables),
            Categories(stringResourceId = R.string.Vegetables, imageResourceId = R.drawable.vegetables),
            )
    }
    fun loadItems(
        @StringRes categoryNameId:Int
    ):List<Item>{
        return listOf(
            Item(R.string.shimla_apple,R.string.Fresh_Fruits,"1 Kg" ,250,R.drawable.shimla_apple),
            Item(R.string.papaya_semi_ripe,R.string.Fresh_Fruits,"1 Kg" ,150,R.drawable.papaya_semi),
            Item(R.string.pomegranate,R.string.Fresh_Fruits,"500 g" ,150,R.drawable.pomegranate),
            Item(R.string.pineapple,R.string.Fresh_Fruits,"1 Kg" ,130,R.drawable.pineapple),
            Item(R.string.pepsi_can,R.string.Beverages,"1 " ,40,R.drawable.pepsi_can)
        ).filter {
            it.itemCategoryId == categoryNameId

        }
    }


}