package com.example.counterapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.counterapplication.ui.theme.CounterApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Counter()
                }
            }
        }
    }
}

@Composable
fun Counter() {
    var text by remember {
        mutableStateOf("")
    }
    var state by rememberSaveable {
        mutableStateOf(0)
    }
    val mainViewModel: MainViewModel = viewModel()
    mainViewModel.getSeconds().observe(LocalLifecycleOwner.current) {
        state = it
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.wrapContentSize(Alignment.TopCenter),
            text = "$state",
            textAlign = TextAlign.Center,
            fontSize = 100.sp
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Timer count") },
            value = text,
            onValueChange = {
                text = it
            })
        Button(modifier = Modifier.padding(4.dp), onClick = {
            if (mainViewModel.getCountDownTimer() != null) {
                mainViewModel.getCountDownTimer()?.cancel()
            }
            if (!text.isNullOrEmpty()) {
                mainViewModel.startTime.value = text.toLong()
                mainViewModel.startTimer()
            }
        }) {
            Text("Start")
        }
        Button(modifier = Modifier.padding(4.dp), onClick = {
            mainViewModel.stopTimer()
        }) {
            Text("Stop")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CounterApplicationTheme {
        Counter()
    }
}