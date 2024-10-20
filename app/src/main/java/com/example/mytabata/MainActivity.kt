package com.example.mytabata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.mytabata.CounterDown
import com.example.mytabata.ui.theme.MytabataTheme

var counterState : Boolean = false

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MytabataTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Counter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Counter(modifier: Modifier = Modifier) {
    var theCounter by remember { mutableStateOf(0L) }
    var countdownTime by remember { mutableStateOf(99) }
    var restTime by remember { mutableStateOf(30) }
    var cycleCount by remember { mutableStateOf(1) }
    var currentCycle by remember { mutableStateOf(1) }
    var miConterDown by remember {
        mutableStateOf(CounterDown(countdownTime) { newValue -> theCounter = newValue })
    }
    var isResting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Ciclo: $currentCycle / $cycleCount",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = if (isResting) "Descanso: $theCounter s" else "Tiempo: $theCounter s",
            modifier = modifier
        )


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                if (countdownTime > 1) countdownTime -= 1
            }) {
                Text(text = "-1s")
            }

            Text(text = "Tiempo: $countdownTime s", modifier = Modifier.padding(horizontal = 8.dp))

            Button(onClick = {
                countdownTime += 1
            }) {
                Text(text = "+1s")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                if (restTime > 1) restTime -= 1
            }) {
                Text(text = "-1s")
            }

            Text(text = "Descanso: $restTime s", modifier = Modifier.padding(horizontal = 8.dp))

            Button(onClick = {
                restTime += 1 
            }) {
                Text(text = "+1s")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                if (cycleCount > 1) cycleCount -= 1
            }) {
                Text(text = "-1 ciclo")
            }

            Text(text = "Ciclos: $cycleCount", modifier = Modifier.padding(horizontal = 8.dp))

            Button(onClick = {
                cycleCount += 1
            }) {
                Text(text = "+1 ciclo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = {
                miConterDown.toggle()
            }) {
                Text(text = if (isResting) "Pausar Descanso" else "Pulsar")
            }


            Button(onClick = {

                miConterDown.cancel()
                theCounter = 0L
                isResting = false
                currentCycle = 1
                miConterDown = CounterDown(countdownTime) { newValue ->
                    theCounter = newValue
                    if (newValue == 0L) {
                        if (isResting) {
                            isResting = false
                            if (currentCycle < cycleCount) {
                                currentCycle++
                                miConterDown = CounterDown(countdownTime) { workValue -> theCounter = workValue }
                                miConterDown.start()
                            }
                        } else {
                            isResting = true
                            miConterDown = CounterDown(restTime) { restValue -> theCounter = restValue }
                            miConterDown.start()
                        }
                    }
                }
            }) {
                Text(text = "Reset")
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MytabataTheme {
        Counter()
    }
}






