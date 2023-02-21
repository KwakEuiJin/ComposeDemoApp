package com.kejprogramer.composedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kejprogramer.composedemoapp.ui.theme.ComposeDemoAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ItemViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val posts by viewModel.items.observeAsState(emptyList())

            LaunchedEffect(Unit) {
                viewModel.loadItems()
            }
            ComposeDemoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
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
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = itemData.imageId ,
                modifier =  Modifier.fillMaxWidth(),
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
    LazyColumn{
        item {
            AsyncImage(model = "https://w.namu.la/s/0f0f182dcbb699f9b1d7d2e026b8654dbc881bfacf21427fe20dca1c0557bd38ffeb0c108c070906773b0e3f16eaf85ca44ab8481804761662c5eaa6b459465311c500c760eb5e8644c850d31c83ccd04305661e65b35b2522be38ec78b3307ef59a9ea9fcc8bcebf0184132cb372458", contentDescription = "")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoAppTheme {
        Greeting()
    }
}