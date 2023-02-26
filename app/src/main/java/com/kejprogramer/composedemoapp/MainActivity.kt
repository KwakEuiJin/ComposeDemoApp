package com.kejprogramer.composedemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kejprogramer.composedemoapp.ui.theme.ComposeDemoAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BottomNavigationScreens()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomNavigationScreens() {
    val pagerState = rememberPagerState()
    HorizontalPager( // 가로로 스크롤 가능한 pager
        count = 5, // 페이지 수
        state = pagerState,
        content = {}// PagerState
    )
    val currentTabIndex = remember { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navItems = listOf("Home", "Profile")
                navItems.forEachIndexed { index, label ->
                    BottomNavigationItem(
                        icon = {
                            when (index) {
                                0 -> Icon(Icons.Filled.Home, contentDescription = null)
                                1 -> Icon(Icons.Filled.Person, contentDescription = null)
                                else -> Icon(Icons.Filled.Home, contentDescription = null)
                            }
                        },
                        label = { Text(label) },
                        selected = currentTabIndex.value == index,
                        onClick = {
                            currentTabIndex.value = index
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        val pagerState = rememberPagerState()
        HorizontalPager( // 가로로 스크롤 가능한 pager
            count = 2, // 페이지 수
            state = pagerState
        ) { page ->
            when (page) {
                0 -> Screen1()
                1 -> Screen2()

            }
        }
    }
}

class PageDataSource(private val screens: List<@Composable (Modifier) -> Unit>) :
    androidx.paging.PagingSource<Int, Int>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Int> {
        val currentPage = params.key ?: 0
        val nextIndex = currentPage + 1
        val prevIndex = if (currentPage > 0) currentPage - 1 else null
        return LoadResult.Page(
            data = listOf(currentPage),
            prevKey = prevIndex,
            nextKey = nextIndex
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Int>): Int? {
        return state.anchorPosition
    }
}

@Composable
fun Screen1(modifier: Modifier = Modifier) {
    Text(
        text = "Screen 1",
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun Screen2(modifier: Modifier = Modifier) {
    Text(
        text = "Screen 2",
        modifier = modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeDemoAppTheme {
        BottomNavigationScreens()
    }
}