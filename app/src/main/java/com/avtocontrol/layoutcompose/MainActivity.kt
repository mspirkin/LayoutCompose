package com.avtocontrol.layoutcompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.avtocontrol.layoutcompose.ui.theme.LayoutComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutComposeTheme {
                ConstraintLayoutContent()
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodeLab")
                },
                actions = {
                    IconButton(onClick = { /*doSomething*/ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Hi there")
        Text(text = "Thanks for going the layouts codelab")
    }

}
//Вертикальный список из 100 элементов
@Composable
fun SimpleList() {
    val scrollState = rememberScrollState()
    Column(Modifier.verticalScroll(scrollState)) {
        repeat(100) {
            Text("Item #$it")
        }
    }
}

@ExperimentalCoilApi
@Composable
fun LazyList() {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(index = it)
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = rememberImagePainter(
            data = "https://developer.android.com/images/brand/Android_Robot.png"
        ), contentDescription = "Android Logo",
        modifier = Modifier.size(50.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
    
}

@ExperimentalCoilApi
@Composable
fun ScrollingList() {
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column() {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to the top")
            }
            Button(onClick = {
                coroutineScope.launch { 
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text(text = "Scroll to the end")
            }
        }
        LazyColumn(state = scrollState) {
            items(listSize) {
                ImageListItem(it)
            }
        }

    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val button = createRef()
        val text = createRef()

        Button(onClick = {/*Do something*/},
        modifier = Modifier.constrainAs(button) {
            top.linkTo(parent.top, margin = 16.dp)
        }) {
            Text(text = "Button")
        }
        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })

    }

}


@Preview
@Composable
fun LayoutsCodeLabPreview() {
    LayoutComposeTheme {
        ConstraintLayoutContent()
    }
}





