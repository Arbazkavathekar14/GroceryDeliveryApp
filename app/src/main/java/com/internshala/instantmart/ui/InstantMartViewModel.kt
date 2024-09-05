package com.internshala.instantmart.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internshala.instantmart.data.InternetItem
import com.internshala.instantmart.network.MartApi
 import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.google.firebase.Firebase
 import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class InstantMartViewModel  :ViewModel() {
    private val _uiState= MutableStateFlow(InstantMartUiState())
    val uiState:StateFlow<InstantMartUiState> =_uiState.asStateFlow()

    private val _isVisible = MutableStateFlow(true)
    val isVisible = _isVisible


    private val _cartItems= MutableStateFlow<List<InternetItem>>(emptyList())
    val cart_items : StateFlow<List<InternetItem>> get()  = _cartItems.asStateFlow()

    private val database = Firebase.database
    private val myRef = database.getReference("users/${auth.currentUser?.uid}/cart")


    private var screenJob:Job
    private lateinit var internetJob:Job

    var itemUiState :ItemUiState by mutableStateOf(ItemUiState.Loading)
        private set

        sealed interface ItemUiState{
            data class Success (val successItems:List<InternetItem>): ItemUiState
            data object Loading:ItemUiState
            data object Error:ItemUiState
        }

    

    fun addToCart(item:InternetItem){
        _cartItems.value += item


    }
    private fun fillCartItemsFromDatabase(){
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _cartItems.value= emptyList()
                for(childSnapshot in dataSnapshot.children){
                    val item =childSnapshot.getValue(InternetItem::class.java)
                    item?.let {
                        val newItem=it
                        addToCart(newItem)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun addToDatabase(item:InternetItem){
        myRef.push().setValue(item)
    }
    fun removeFromCart(oldItem:InternetItem){
         myRef.addListenerForSingleValueEvent(object :ValueEventListener {

             override fun onDataChange(dataSnapshot: DataSnapshot) {
                  for(childSnapshot in dataSnapshot.children){
                      var itemRemoved =false
                     val item =childSnapshot.getValue(InternetItem::class.java)
                     item?.let {
                         if(oldItem.itemName == it.itemName && oldItem.itemPrice==it.itemPrice){
                             childSnapshot.ref.removeValue()
                             itemRemoved = true
                         }
                     }
                      if(itemRemoved) break
                 }
             }

             override fun onCancelled(error: DatabaseError) {

             }
         }
         )

    }


    fun updateClickState(updatedText:String){
        _uiState.update {
            it.copy(
                clickStatus = updatedText)
        }
    }

    fun updateSelectedCategory(updatedCategory: Int){
        _uiState.update {
            it.copy(
                selectedCategory = updatedCategory
            )
        }
    }
    private fun toggleVisibility(){
        _isVisible.value=false
    }

    fun getMartItems(){
            internetJob =viewModelScope.launch {
                try{
                    val listResult=MartApi.retrofitService.getItems()
                    itemUiState= ItemUiState.Success(listResult)

                }
                catch (exception:Exception){
                    itemUiState= ItemUiState.Error
                    toggleVisibility()
                    screenJob.cancel()
                }
            }
    }


    init {
        screenJob =viewModelScope.launch {
            delay(3000)
            toggleVisibility()
        }
        getMartItems()
        fillCartItemsFromDatabase()
    }
}

