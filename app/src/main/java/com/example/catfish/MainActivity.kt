package com.example.catfish

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catfish.module.CatFacts
import com.example.catfish.ui.theme.CatFishTheme
import com.example.catfish.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatFishTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                ) {
                    var fact by remember {
                        mutableStateOf(CatFacts())

                    }

                    val context = LocalContext.current
                    val scope = rememberCoroutineScope()
                    LaunchedEffect(key1 = true) {
                        scope.launch(Dispatchers.IO) {
                            val response = try {
                                RetrofitInstance.api.getRandomFacts()
                            }catch (e: HttpException){
                                Toast.makeText(context, "http error: ${e.message}", Toast.LENGTH_SHORT).show()
                                return@launch
                            }catch (e: IOException){
                                Toast.makeText(context, "http error: ${e.message}", Toast.LENGTH_SHORT).show()
                                return@launch
                            }

                            if(response.isSuccessful && response.body() != null){
                                withContext(Dispatchers.Main){
                                    fact = response.body()!!
                                }
                            }
                        }
                    }
                    MyUi(fact = fact)
                }

            }
        }
    }
}


@Composable
fun MyUi(fact: CatFacts, modifier: Modifier = Modifier) {
    Column (
        modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Cat Facts",
            modifier.padding(bottom = 25.dp),
            fontSize = 26.sp
        )
        Text(
            text = fact.fact,
            fontSize = 26.sp
        )

    }
}