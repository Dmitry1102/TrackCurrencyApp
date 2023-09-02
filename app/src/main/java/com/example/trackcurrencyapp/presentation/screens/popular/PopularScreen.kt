package com.example.trackcurrencyapp.presentation.screens.popular

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.trackcurrencyapp.R
import com.example.trackcurrencyapp.domain.model.CurrencyView
import com.example.trackcurrencyapp.navigation.NavigateScreen
import com.example.trackcurrencyapp.presentation.screens.popular.viewModel.PopularViewModel
import com.example.trackcurrencyapp.presentation.screens.popular.viewModel.PopularViewModel.CurrencyEvent
import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.RESPONSE_DELAY
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularScreen(
    navController: NavHostController,
    viewModel: PopularViewModel = hiltViewModel()
) {
    LaunchedEffect(
        true
    ) {
        while (true) {
            viewModel.getCurrencies()
            delay(
                RESPONSE_DELAY
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(
                    100.dp
                )
                .background(
                    Color.Black
                ),
        ) {

            var selectedCurrency by remember {
                mutableStateOf(
                    ""
                )
            }
            val currencyOptionsList =
                stringArrayResource(
                    id = R.array.currency_codes
                )
            var expanded by remember {
                mutableStateOf(
                    false
                )
            }
            var textFieldSize by remember {
                mutableStateOf(
                    Size.Zero
                )
            }

            val icon =
                if (expanded) {
                    Icons.Filled.KeyboardArrowUp
                } else {
                    Icons.Filled.KeyboardArrowDown
                }

            Column(
                modifier = Modifier.fillMaxHeight()
                    .width(
                        350.dp
                    )
                    .padding(
                        10.dp
                    )
            ) {
                OutlinedTextField(
                    value = selectedCurrency,
                    onValueChange = {
                        selectedCurrency =
                            it
                    },
                    modifier = Modifier.fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize =
                                coordinates.size.toSize()

                        },
                    label = {
                        Text(
                            text = stringResource(
                                id = R.string.select_label
                            ),
                            color = Color.White,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Icon",
                            modifier = Modifier.clickable {
                                expanded =
                                    true
                            },
                            tint = Color.White
                        )
                    })

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded =
                            false
                    },
                    modifier = Modifier.width(
                            with(
                                LocalDensity.current
                            ) {
                                textFieldSize.width.toDp()
                            })
                        .background(
                            Color.Black
                        )
                ) {
                    currencyOptionsList.forEach { currencyLabel ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCurrency =
                                    currencyLabel
                                viewModel.getCurrencyByName(
                                    currencyLabel
                                )
                                expanded =
                                    false
                            }) {
                            Text(
                                text = currencyLabel,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal
                            )
                        }
                    }
                }
            }

            Image(
                modifier = Modifier.size(
                        height = 90.dp,
                        width = 80.dp
                    )
                    .clickable {
                        navController.navigate(
                            NavigateScreen.Sort.route
                        )
                    },
                painter = painterResource(
                    id = R.drawable.ic_baseline_sort_24
                ),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = Color.White
                ),
            )
        }

        val conversionState by viewModel.conversion.collectAsState()

        when (conversionState) {
            is CurrencyEvent.Success -> {
                val currencyList =
                    (conversionState as CurrencyEvent.Success).currency
                FetchLazyColumn(
                    currencyList = currencyList,
                    viewModel = viewModel
                )
            }

            is CurrencyEvent.Failure -> {
                val message =
                    (conversionState as CurrencyEvent.Failure).message
                ShowAlertDialog(
                    message = message
                )
            }

            is CurrencyEvent.Loading -> {
                ShowLoadingIndicator()
            }

            else -> Unit
        }
    }
}

@Composable
private fun FetchLazyColumn(currencyList: List<CurrencyView>, viewModel: PopularViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
       items(currencyList) { currency ->
           ItemRow(
               item = currency,
               viewModel = viewModel
           )

       }
    }
}

@Composable
private fun ShowAlertDialog(message: String) {
    var showDialog by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.alert_dialog_title),
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                },
                text = {
                    Text(
                        text = message,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                },
                confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                16.dp
                            ),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black
                            ),
                            modifier = Modifier.width(100.dp)

                        ) {
                            Text(
                                text = stringResource(id = R.string.alert_dialog_button),
                                color = Color.White
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun ShowLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
private fun ItemRow(item: CurrencyView, viewModel: PopularViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.currency,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = item.value.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.weight(1f)
        )

        val isClicked by remember { mutableStateOf(false) }

        Image(
            painter = if(!item.favourite){
                painterResource(id = R.drawable.ic_baseline_star_outline_24)
            }else {
                painterResource(
                    id = R.drawable.ic_baseline_star_24
                )  
            },
            contentDescription = null,
            modifier = Modifier
                .size(
                    width = 40.dp,
                    height = 40.dp
                )
                .clickable {
                    viewModel.editFavouriteCurrencies(
                        item.currency
                    )
                    isClicked != isClicked
                },
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Black)

        )
    }
}


