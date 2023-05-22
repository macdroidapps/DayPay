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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.macdroid.daypay.domain.MainEvents
import ru.macdroid.daypay.domain.MainState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainRoot(
    onNavigateNext: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
    ) {

    val state by viewModel.feature.state.collectAsState()

    MainScreen(
        state = state,
        onSalaryByMonth = {
            viewModel.onEvent(MainEvents.GetSalaryByMonth(
                salary = state.salary,
                workedDays = state.workedDays,
                year = state.year,
                month = state.month
            ))
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
            viewModel.onEvent(MainEvents.WorkedDaysInput(it))
        }
    )

}


@Composable
fun MainScreen(
    state: MainState,
    onSalaryByMonth: () -> Unit,
    onSalaryInput: (String) -> Unit,
    onYearInput: (String) -> Unit,
    onMonthInput: (String) -> Unit,
    onWorkingDaysInput: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            value = state.salary,
            onValueChange = {
                if (it.isNotEmpty()) {
                    onSalaryInput(it)
                }
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
                if (it.isNotEmpty()) {
                    onYearInput(it)
                }
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
                    onMonthInput(it)
            },
            label = { Text(text = "Месяц") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            value = state.workedDays,
            onValueChange = {
                if (it.isNotEmpty())
                    onWorkingDaysInput(it)
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