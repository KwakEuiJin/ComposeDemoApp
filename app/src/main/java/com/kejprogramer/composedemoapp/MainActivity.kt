package com.kejprogramer.composedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kejprogramer.composedemoapp.ui.theme.ComposeDemoAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SwipeableScreens()
                }
            }
        }
    }

    @Composable
    fun SwipeableScreens() {
        val tabTitles = listOf("Screen 1", "Screen 2")
        val currentTabIndex = remember { mutableStateOf(0) }

        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = currentTabIndex.value,
                backgroundColor = Color.Gray,
                contentColor = Color.White
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = title) },
                        selected = currentTabIndex.value == index,
                        onClick = { currentTabIndex.value = index }
                    )
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                when (currentTabIndex.value) {
                    0 -> Screen1()
                    1 -> Screen2()
                }
            }
        }
    }

    @Composable
    fun Screen1() {
        Text(
            text = "Screen 1",
            modifier = Modifier.fillMaxSize()
        )
    }

    @Composable
    fun Screen2() {
        Text(
            text = "Screen 2",
            modifier = Modifier.fillMaxSize()
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ComposeDemoAppTheme {
            SwipeableScreens()
        }
    }
}