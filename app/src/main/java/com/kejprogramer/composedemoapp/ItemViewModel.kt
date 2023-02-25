package com.kejprogramer.composedemoapp


import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemViewModel : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://63f49e393f99f5855db38740.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    private val _items: MutableLiveData<List<ItemData>> by lazy {
        MutableLiveData<List<ItemData>>()
    }
    val items: LiveData<List<ItemData>> get() = _items

    fun loadItems() {
        //LaunchedEffect 를 쓰는 것을 권장?
//        LaunchedEffect(Unit) {
//            val result = runCatching { api.getComposeList() }
//            result.onSuccess { _items.value = it }
//            result.onFailure { }
//        }
        viewModelScope.launch {
            val result = runCatching { api.getComposeList() }
            result.onSuccess { _items.value = it }
            result.onFailure { }
        }
    }
}