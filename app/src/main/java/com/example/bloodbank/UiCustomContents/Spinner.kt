package com.example.bloodbank.UiCustomContents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloodbank.ui.theme.primary
import com.example.bloodbank.ui.theme.primaryDark

@Composable
fun NormalSpinner(
    list:List<String>,
    spinnerText: MutableState<Int>
){
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
       modifier = Modifier
           .fillMaxSize()
           .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    expanded = !expanded
                }
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = list.get(spinnerText.value),
                modifier = Modifier.weight(9f)
            )
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded=false }) {
                list.forEachIndexed { index,newSpinnerText ->
                    DropdownMenuItem(onClick = {
                        spinnerText.value=index
                        expanded=false
                    }) {
                        if(spinnerText.equals(newSpinnerText)){
                            Text(
                                text = newSpinnerText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            )
                        } else{
                            Text(
                                text = newSpinnerText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun displayNormalSpinner(){
    NormalSpinner(listOf(),remember{ mutableStateOf(0)})
}