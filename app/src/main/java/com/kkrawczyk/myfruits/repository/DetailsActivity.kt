package com.kkrawczyk.myfruits.repository

import DetailsViewModel
import Fruit
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkrawczyk.myfruits.MyErrorView
import com.kkrawczyk.myfruits.MyLoadingView
import com.kkrawczyk.myfruits.getRandomImageResource
import com.kkrawczyk.myfruits.ui.theme.MyFruitsTheme

class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("FRUIT_ID")?.toInt() ?: 0
        viewModel.getData(id)


        setContent {
            MyFruitsTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    DetailsView(viewModel)
                }
            }
        }
    }
}

@Composable
fun DetailsView(viewModel: DetailsViewModel) {
    val uiState by viewModel.immutableFruitData.observeAsState(DetailsViewModel.UiState())

    when {
        uiState.isLoading -> {
            MyLoadingView()
        }

        uiState.error != null -> {
            MyErrorView(uiState.error.toString())
        }

        uiState.data != null -> {
            uiState.data?.let { MyFruitView(fruit = it) }
        }
    }
}

@Composable
fun MyFruitView(fruit: Fruit) {
    val randomImageResource = getRandomImageResource()

    Box(modifier = Modifier.padding(all = 16.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = fruit.name,
                    fontSize = 32.sp,
                )
            }

            Row {
                Image(
                    painter = painterResource(id = randomImageResource),
                    contentDescription = "Strawberry",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                        .height(200.dp)
                )
            }

            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Row() {
                    Text(
                        text = "genus: ${fruit.genus}",
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
                Row() {
                    Text(
                        text = "family: ${fruit.family}",
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Row {
                Text(
                    text = "Nutrients (per 100g)",
                    fontSize = 20.sp,
                )
            }

            Text(
                text = "- calories: ${fruit.nutritions.calories} g",
                fontSize = 16.sp,
            )
            Text(
                text = "- carbohydrates: ${fruit.nutritions.carbohydrates} g",
                fontSize = 16.sp,
            )
            Text(
                text = "- fat: ${fruit.nutritions.fat} g",
                fontSize = 16.sp,
            )
            Text(
                text = "- protein: ${fruit.nutritions.protein} g",
                fontSize = 16.sp,
            )
            Text(
                text = "- sugar: ${fruit.nutritions.sugar} g",
                fontSize = 16.sp,
            )
        }
    }

}
