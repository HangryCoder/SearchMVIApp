package com.hangrycoder.searchmviapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hangrycoder.searchmviapp.intent.UserIntent
import com.hangrycoder.searchmviapp.model.Transaction
import com.hangrycoder.searchmviapp.state.SearchState
import com.hangrycoder.searchmviapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    val userIntent = mainViewModel.userIntent
    val searchState = mainViewModel.searchState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Column {
        SearchView(value = query, onValueChange = {
            query = it
            scope.launch {
                userIntent.send(UserIntent.Search(query))
            }
        })

        when (searchState.value) {
            is SearchState.Loading -> {
                LoadingView()
            }

            is SearchState.Success -> {
                val transactions = (searchState.value as SearchState.Success).transactions
                if (!transactions.isNullOrEmpty()) {
                    TransactionList(list = transactions)
                }
            }

            is SearchState.Error -> {
                val error = (searchState.value as SearchState.Error).error
                    ?: "Something went wrong. Try again"
                ErrorView(message = error)
            }

            else -> {

            }
        }
    }
}
@ExperimentalMaterial3Api
@Composable
fun SearchView(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text("Input text here") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp)
            .wrapContentHeight()
    )
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Loading....",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp
        )
    }
}

@Composable
fun ErrorView(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp
        )
    }
}

@Composable
fun TransactionList(list: List<Transaction>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(list) {
            Text(
                text = "${it.merchant} - ${it.amount}", modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 16.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(1.dp)
            )
        }
    }
}