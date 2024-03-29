package com.kkrawczyk.myfruits

import Fruit
import MainViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkrawczyk.myfruits.repository.DetailsActivity
import com.kkrawczyk.myfruits.ui.theme.MyFruitsTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData()
        setContent {
            MyFruitsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(
                        viewModel,
                        onClick = { id -> navigateToDetails(id) }
                    )
                }
            }
        }
    }

    private fun navigateToDetails(id: Number) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("FRUIT_ID", "$id")
        startActivity(intent)
    }
}

@Composable
fun MainView(viewModel: MainViewModel, onClick: (Number) -> Unit) {
    val uiState by viewModel.immutableFruitsData.observeAsState(MainViewModel.UiState())

    when {
        uiState.isLoading -> {
            MyLoadingView()
        }

        uiState.error != null -> {
            MyErrorView(uiState.error.toString())
        }

        uiState.data != null -> {
            uiState.data?.let { MyListView(fruits = it, onClick = { id -> onClick.invoke(id) }) }
        }
    }
}

@Composable
fun MyErrorView(error: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Oops, something went wrong!",
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = error,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            ),
        )
    }
}

@Composable
fun MyLoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun MyListView(fruits: List<Fruit>, onClick: (Number) -> Unit) {
    FruitList(fruits, onClick = { id -> onClick.invoke(id) })
}

@Composable
fun FruitList(fruits: List<Fruit>, modifier: Modifier = Modifier, onClick: (Number) -> Unit) {
    LazyColumn {
        items(fruits) { fruit ->
            FruitListItem(fruit = fruit, onClick = { id -> onClick.invoke(id) })
        }
    }
}

fun getRandomImageResource(): Int {
    val imageResources = arrayOf(
        R.drawable.blueberry,
        R.drawable.grape,
        R.drawable.kiwi,
        R.drawable.pear,
        R.drawable.watermelon
    )
    return imageResources.random()
}


@Composable
fun FruitListItem(fruit: Fruit, onClick: (Number) -> Unit) {
    val randomImageResource = getRandomImageResource()

    Box(modifier = Modifier.padding(all = 16.dp)) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Column {
                    Row {
                        Text(
                            text = fruit.name,
                            fontSize = 22.sp,
                        )
                    }
                    Row {
                        Text(
                            text = "genus: ${fruit.genus}",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Row {
                        Text(
                            text = "family: ${fruit.family}",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
                Column {
                    Image(
                        painter = painterResource(id = randomImageResource),
                        contentDescription = "Strawberry",
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                    )
                }
            }
            Column {
                Row {
                    Text(
                        text = "Nutrients (per 100g)",
                        fontSize = 14.sp,
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "calories: ${fruit.nutritions.calories}",
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "|",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "protein: ${fruit.nutritions.protein}",
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "|",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "sugar: ${fruit.nutritions.sugar}",
                        fontSize = 12.sp,
                    )
                }

                Row {

                    OutlinedButton(
                        onClick = { onClick.invoke(fruit.id) }, modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "View details",
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FruitListPreview() {
    MyFruitsTheme {
        //FruitList()
    }
}