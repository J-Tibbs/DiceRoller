package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}



@Preview
@Composable
fun DiceRollerApp(){
    Roll_button()
}


@Composable
fun Roll_button(
){
    var amountInput by remember { mutableStateOf("")}
    var maxAmount by remember {mutableStateOf("")}
    var result by remember { mutableStateOf(listOf<Int>())}
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(modifier = Modifier) { //d6

            Row() {
                EditQuantityField(
                    amountInput = amountInput,
                    onValueChange = { amountInput = it },
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                EditDieField(
                    maxAmount = maxAmount,
                    onValueChange = { maxAmount = it },
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                result.dropLast(1).forEachIndexed { index, roll ->
                    Text(
                        text = "${index + 1}: $roll",
                        color = Color.Black
                    )
                }

            }
            if (result.isNotEmpty()) {
                Text(
                    text = "Total: ${result.last()}"
                )
            }


        }


        Button(onClick = { result = calculateRoll(amountInput.toIntOrNull() ?: 0, maxAmount.toIntOrNull() ?: 6) },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 24.dp)) {
            Image(painter = painterResource(
                if (isPressed)
                    R.drawable.roll2
                else
                    R.drawable.roll1
            ),
                contentDescription = "Roll Dice")

        }

    }


}

@Composable
fun EditQuantityField(amountInput: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier){

    TextField(
        value = amountInput,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(stringResource(R.string.d6))},
        modifier = modifier.width(72.dp)
    )
}


@Composable
fun EditDieField(maxAmount: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier){

    TextField(
        value = maxAmount,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(stringResource(R.string.max))},
        modifier = modifier.width(72.dp)
    )
}
private fun calculateRoll(amount: Int, max: Int) : MutableList<Int>{

    var rolls : MutableList<Int> = mutableListOf()
    var total : Int
    var rollTotals: Int = 0
    var totalNumOfRolls = amount

    if (totalNumOfRolls < 0) {
        totalNumOfRolls = 0
    }

    for(i in 1..totalNumOfRolls){
        total = (1..max).random()
        rolls.add(total)
        rollTotals += total
    }
    rolls.add(rollTotals)

    return rolls
}
