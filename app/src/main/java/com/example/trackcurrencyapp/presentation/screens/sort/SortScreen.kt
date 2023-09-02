package com.example.trackcurrencyapp.presentation.screens.sort

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.trackcurrencyapp.R
import com.example.trackcurrencyapp.presentation.screens.sort.viewModel.SortViewModel

@Composable
fun SortScreen(viewModel: SortViewModel = hiltViewModel()) {
   Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
   ) {

      viewModel.getSortingOrder()

      Text(
         modifier = Modifier.padding(
            top = 20.dp
         ),
         text = stringResource(
            id = R.string.sort_label
         ),
         color = Color.Black,
         fontSize = 32.sp,
         textAlign = TextAlign.Center,
         fontWeight = FontWeight.SemiBold,
         fontStyle = FontStyle.Normal
      )

      Spacer(
         modifier = Modifier.height(
            10.dp
         )
      )

      Row(
         modifier = Modifier
            .fillMaxWidth()
            .height(
               240.dp
            )
            .padding(
               10.dp
            )
      ) {

         Box(
            modifier = Modifier
               .fillMaxSize()
               .border(
                  width = 1.dp,
                  color = Color.Black,
                  shape = RoundedCornerShape(
                     20.dp
                  )
               )
               .background(
                  color = Color.White,
                  shape = RoundedCornerShape(
                     20.dp
                  )
               )
               .padding(
                  top = 20.dp,
                  start = 20.dp
               )
         ) {

            Text(
               text = stringResource(
                  id = R.string.type_sort_label
               ),
               color = Color.Black,
               fontSize = 22.sp,
               fontWeight = FontWeight.SemiBold,
               fontStyle = FontStyle.Normal
            )

            Column {
               val state by viewModel.typeStateFlow.collectAsState()

               Row(
                  modifier = Modifier.padding(
                     top = 50.dp
                  ),
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  RadioButton(
                     selected = state,
                     onClick = {
                        viewModel.apply {
                           setTypePositionState(state = true)
                           setAlphabeticalSorting(isAlphabet = true)
                        }
                     },
                     colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black,
                        unselectedColor = Color.Black
                     )
                  )

                  Text(
                     text = stringResource(
                        id = R.string.alphabetical_order_label
                     ),
                     color = Color.Black,
                     fontSize = 18.sp,
                     fontWeight = FontWeight.SemiBold,
                     fontStyle = FontStyle.Normal
                  )
               }

               Spacer(
                  modifier = Modifier.height(
                     20.dp
                  )
               )

               Row(
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  RadioButton(
                     selected = !state,
                     onClick = {
                        viewModel.apply {
                           setTypePositionState(state = false)
                           setAlphabeticalSorting(isAlphabet = false)
                        }
                     },
                     colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black,
                        unselectedColor = Color.Black
                     )
                  )

                  Text(
                     text = stringResource(
                        id = R.string.value_order_label
                     ),
                     color = Color.Black,
                     fontSize = 18.sp,
                     fontWeight = FontWeight.SemiBold,
                     fontStyle = FontStyle.Normal
                  )
               }
            }
         }

      }

      Spacer(
         modifier = Modifier.height(
            10.dp
         )
      )

      Row(
         modifier = Modifier
            .fillMaxWidth()
            .height(
               240.dp
            )
            .padding(
               10.dp
            )
      ) {

         Box(
            modifier = Modifier
               .fillMaxSize()
               .border(
                  width = 1.dp,
                  color = Color.Black,
                  shape = RoundedCornerShape(
                     20.dp
                  )
               )
               .background(
                  color = Color.White,
                  shape = RoundedCornerShape(
                     20.dp
                  )
               )
               .padding(
                  top = 20.dp,
                  start = 20.dp
               )
         ) {

            Text(
               text = stringResource(
                  id = R.string.order_sort_label
               ),
               color = Color.Black,
               fontSize = 22.sp,
               fontWeight = FontWeight.SemiBold,
               fontStyle = FontStyle.Normal
            )

            Column {
               val state by viewModel.orderStateFlow.collectAsState()

               Row(
                  modifier = Modifier.padding(
                     top = 50.dp
                  ),
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  RadioButton(
                     selected = state,
                     onClick = {
                        viewModel.apply {
                           setOrderState(state = true)
                           setValueSorting(isIncrease = true)
                        }
                     },
                     colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black,
                        unselectedColor = Color.Black
                     )
                  )

                  Text(
                     text = stringResource(
                        id = R.string.increase_order_label
                     ),
                     color = Color.Black,
                     fontSize = 18.sp,
                     fontWeight = FontWeight.SemiBold,
                     fontStyle = FontStyle.Normal
                  )
               }

               Spacer(
                  modifier = Modifier.height(
                     20.dp
                  )
               )

               Row(
                  verticalAlignment = Alignment.CenterVertically
               ) {
                  RadioButton(
                     selected = !state,
                     onClick = {
                        viewModel.apply {
                           setOrderState(state = false)
                           setValueSorting(isIncrease = false)
                        }
                     },
                     colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black,
                        unselectedColor = Color.Black
                     )
                  )

                  Text(
                     text = stringResource(
                        id = R.string.decrease_order_label
                     ),
                     color = Color.Black,
                     fontSize = 18.sp,
                     fontWeight = FontWeight.SemiBold,
                     fontStyle = FontStyle.Normal
                  )
               }
            }
         }
      }
   }
}