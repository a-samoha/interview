package com.artsam.interview.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artsam.interview.data.entity.InterviewQuestion
import com.artsam.interview.presentation.theme.AppTheme
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    viewModel: QuestionViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Android Interview",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                })
        }
    ) { paddingValues ->

        when (state) {
            QuestionUiState.Initialising -> Text(text = "Loading ...")
            is QuestionUiState.Content -> Content(
                state = state,
                modifier = Modifier
                    .padding(paddingValues)
            )
        }
    }
}

@Composable
fun Content(
    state: QuestionUiState.Content,
    modifier: Modifier = Modifier
) {
    val questionState = remember {
        mutableStateOf(state.questions.first())
    }

    Box(
        modifier = modifier
            .background(Color.Blue)
    ) {
        Column(
            modifier.background(Color.Green)
        ) {
            val question = questionState.value
            TitleItem("Level:", question.level.name)
            TitleItem("Topic:", question.topic)
            TitleItem("Question:", question.question)
            AnswerItem("Answer", question.answer)
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxHeight()
                .width(150.dp)
                .align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
            )
        }
        IconButton(
            onClick = {
                questionState.value = state.questions[Random.nextInt(state.questions.size)]
            },
            modifier = Modifier
                .fillMaxHeight()
                .width(150.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
            )
        }
    }
}

@Composable
fun TitleItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
    ) {
        Text(text = title)
        Text(text = value)
    }
}

@Composable
fun AnswerItem(
    title: String,
    answers: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
    ) {
        Text(text = title)
        Column {
            answers.forEach {
                Text(text = it)
            }
        }
    }
}












@Preview(showSystemUi = true)
@Composable
fun MainPreview() {
    AppTheme {
        Content(
            QuestionUiState.Content(
                listOf(
                    InterviewQuestion(
                        level = InterviewQuestion.Level.JUNIOR,
                        topic = "Базові питання",
                        question = "Назвіть основні принципи ООП",
                        answer = listOf(
                            "Абстракция: Представление реальных объектов в коде. Она позволяет сконцентрироваться на важных аспектах объекта, игнорируя несущественные.",
                            "Инкапсуляция: Скрытие деталей реализации. Это обеспечивает безопасность данных и уменьшает сложность программы.",
                            "Наследование: Использование свойств и методов одного класса в другом. Это способствует повторному использованию кода.",
                            "Полиморфизм: Возможность использовать объекты разных классов с одинаковым интерфейсом без информации о конкретном классе. Это упрощает работу с различными типами данных."
                        ),
                    ),
                    InterviewQuestion(
                        level = InterviewQuestion.Level.JUNIOR,
                        topic = "Базові питання",
                        question = "Що таке клас? Що таке інтерфейс? Яка між ними різниця?",
                        answer = listOf(
                            "Класс: Шаблон для создания объектов, содержащий их состояние (поля) и поведение (методы).",
                            "Интерфейс: Определяет поведение (методы), которое должны реализовывать классы, но не предоставляет их реализацию.",
                            "Различие: Классы представляют собой конкретную реализацию, в то время как интерфейсы определяют только структуру поведения, которую классы должны следовать.",
                        ),
                    )
                )
            )
        )
    }
}
