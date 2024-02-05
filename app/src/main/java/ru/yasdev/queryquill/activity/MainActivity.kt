package ru.yasdev.queryquill.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.yasdev.queryquill.components.MyTopAppBar
import ru.yasdev.queryquill.ui.theme.QueryQuillTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QueryQuillTheme {
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        MyTopAppBar(scrollBehavior = scrollBehavior)
                    }
                ){
                        MainScreen(paddingValues = it)
                }
            }
        }
    }
}


@Composable
fun MainScreen(paddingValues: PaddingValues){

        Surface(Modifier.padding(top = paddingValues.calculateTopPadding()).fillMaxSize()){
            LazyColumn{
                items(60) { index ->
                    Text(text = "qqq $index")
                }
            }
        }




}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QueryQuillTheme {
    }
}