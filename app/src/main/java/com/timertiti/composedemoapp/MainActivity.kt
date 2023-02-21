package com.timertiti.composedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.timertiti.composedemoapp.ui.theme.ComposeDemoAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://63f49e393f99f5855db38740.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(Api::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var posts by remember { mutableStateOf(emptyList<ItemData>()) }
            LaunchedEffect(Unit) {
                val result = kotlin.runCatching { api.getComposeList()}
                result.onSuccess { posts = it }
                result.onFailure {  }
            }
            ComposeDemoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    CatalogEx(posts)
                }
            }
        }
    }
}
@Composable
fun Item(itemData: ItemData) {
    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberImagePainter(data = "https://w.namu.la/s/0f0f182dcbb699f9b1d7d2e026b8654dbc881bfacf21427fe20dca1c0557bd38ffeb0c108c070906773b0e3f16eaf85ca44ab8481804761662c5eaa6b459465311c500c760eb5e8644c850d31c83ccd04305661e65b35b2522be38ec78b3307ef59a9ea9fcc8bcebf0184132cb372458"),
                contentDescription = itemData.title,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = itemData.title,
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = itemData.description
            )
        }
    }
}

@Composable
fun CatalogEx(itemList: List<ItemData>) {
    LazyColumn {
        items(itemList) {item ->
            Item(item)
        }
    }
}

@Composable
fun Greeting(){
    val painter = rememberImagePainter(data = "https://w.namu.la/s/0f0f182dcbb699f9b1d7d2e026b8654dbc881bfacf21427fe20dca1c0557bd38ffeb0c108c070906773b0e3f16eaf85ca44ab8481804761662c5eaa6b459465311c500c760eb5e8644c850d31c83ccd04305661e65b35b2522be38ec78b3307ef59a9ea9fcc8bcebf0184132cb372458")
    Image(painter = painter, contentDescription = "")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoAppTheme {
        Greeting()
    }
}