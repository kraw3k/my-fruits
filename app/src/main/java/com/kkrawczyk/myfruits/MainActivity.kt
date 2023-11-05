package com.kkrawczyk.myfruits

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kkrawczyk.myfruits.ui.theme.MyFruitsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFruitsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FruitList()
                }
            }
        }
    }
}

// API: https://www.fruityvice.com/

@Composable
fun FruitList(){
    FruitListItem()
}

@Composable
fun FruitListItem(){
    Box(modifier = Modifier.padding(all=16.dp)){
        Column {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)) {
                Column {
                    Row {
                        Text(
                            text = "Strawberry",
                            fontSize = 22.sp,
                        )
                    }
                    Row {
                        Text(
                            text = "genus: Fragaria",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Row {
                        Text(
                            text = "family: Rosaceae",
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
                Column {
                    Image(painter = painterResource(id = R.drawable.strawberry), contentDescription = "Strawberry", modifier = Modifier.width(60.dp).height(60.dp))
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
                        text = "calories: 29",
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "|",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "protein: 0.8",
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "|",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "sugar: 5.4",
                        fontSize = 12.sp,
                    )
                }

                Row {

                    OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()) {
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
        FruitList()
    }
}