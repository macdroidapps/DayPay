package ru.macdroid.daypay.presentation.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.macdroid.daypay.domain.MainEvents
import ru.macdroid.daypay.domain.MainState
import ru.macdroid.daypay.domain.model.SalaryByMonth
import ru.macdroid.daypay.presentation.main.components.SelectableCalendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainRoot(
    onNavigateNext: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),

    ) {

    val state by viewModel.state.collectAsState(initial = MainState())

    LaunchedEffect(key1 = Unit, block = {
        viewModel.onEvent(MainEvents.GetCalendar)
    })

    MainScreen(
        state = state,
        onSalaryByMonth = {
            viewModel.onEvent(MainEvents.GetMonthSalaryByMonth)
        },
        onSalaryInput = {
            viewModel.onEvent(MainEvents.SalaryInput(it))
        },
        onYearInput = {
            viewModel.onEvent(MainEvents.YearInput(it))
        },
        onMonthInput = {
            viewModel.onEvent(MainEvents.MonthInput(it))
        },
        onWorkingDaysInput = {
            viewModel.onEvent(MainEvents.WorkingDaysInput(it))
        }
    )

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    state: MainState,
    onSalaryByMonth: () -> Unit,
    onSalaryInput: (Float) -> Unit,
    onYearInput: (Int) -> Unit,
    onMonthInput: (Int) -> Unit,
    onWorkingDaysInput: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            value = state.salary.toString(),
            onValueChange = {
                if (it.isNotEmpty())
                onSalaryInput(it.toFloat())
            },
            label = { Text(text = "Ваш оклад") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            value = state.year.toString(),
            onValueChange = {
                if (it.isNotEmpty())
                onYearInput(it.toInt())
            },
            label = { Text(text = "Год") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            value = state.month.toString(),
            onValueChange = {
                if (it.isNotEmpty())
                onMonthInput(it.toInt())
            },
            label = { Text(text = "Месяц") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            value = state.workingDays.toString(),
            onValueChange = {
                if (it.isNotEmpty())
                onWorkingDaysInput(it.toInt())
            },
            label = { Text(text = "Отработанных дней") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedButton(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            onClick = {
                onSalaryByMonth()
            }
        ) {
            Text(text = "Рассчитать")
        }

        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = "Количество рабочих дней в месяце"
            )
            Text(
                modifier = Modifier,
                text = state.workingDaysInMonth.toString()
            )
        }

        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = "Заработок за период с НДФЛ"
            )
            Text(
                modifier = Modifier,
                text = state.salaryByMonth.toString()
            )
        }

        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 16.dp),
                text = "Заработок за период без НДФЛ"
            )
            Text(
                modifier = Modifier,
                text = state.salaryByMonthWithoutNalog.toString()
            )
        }

//        SelectableCalendar()

    }
}