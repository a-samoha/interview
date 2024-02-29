package com.artsam.interview.presentation.screen.interview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artsam.interview.R
import com.artsam.interview.data.entity.InterviewQuestion
import com.artsam.interview.presentation.theme.AppTheme
import kotlin.random.Random

private const val DEFAULT_RECENT_QUESTIONS_AMOUNT = 10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewScreen(
    viewModel: InterviewViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.android_interview),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                })
        }
    ) { paddingValues ->
        val scaffoldPaddingModifier = Modifier.padding(paddingValues)
        when (state) {
            InterviewState.Initialising -> {
                viewModel.handleIntent(InterviewIntent.FetchQuestions(LocalContext.current))
                Box(
                    modifier = scaffoldPaddingModifier,
                    contentAlignment = Alignment.Center
                ) { Text(text = stringResource(R.string.loading)) }
            }
            is InterviewState.Content -> Content(
                state = state,
                modifier = scaffoldPaddingModifier
            )
        }
    }
}

@Composable
fun Content(
    state: InterviewState.Content,
    modifier: Modifier = Modifier
) {
    val questionState = remember { mutableStateOf(state.questions.first()) }
    val recentQuestionIndexes = remember { RecentValues<Int>(DEFAULT_RECENT_QUESTIONS_AMOUNT) }

    Box(modifier) {
        Column(modifier = Modifier.padding(0.dp, 8.dp)) {
            val question = questionState.value
            ContentItem(stringResource(R.string.level), listOf(question.level.name))
            ContentItem(stringResource(R.string.topic), listOf(question.topic))
            ContentItem(stringResource(R.string.question), listOf(question.question))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .padding(0.dp, 8.dp, 0.dp, 16.dp),
                text = stringResource(R.string.answer),
                fontSize = 20.sp
            )
            Card(
                modifier = Modifier.padding(16.dp, 0.dp),
                elevation = CardDefaults.outlinedCardElevation(4.dp)
            ) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    question.answer.forEach {
                        Text(
                            text = it,
                            modifier = Modifier.padding(12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp)
                .align(Alignment.CenterStart)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() } // This is mandatory
                ) {
                    val previousQuestionIndex = recentQuestionIndexes.getPrevious()
                    questionState.value = state.questions[previousQuestionIndex]
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = "button previous",
                modifier = Modifier.padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp)
                .align(Alignment.CenterEnd)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() } // This is mandatory
                ) {
                    val nextQuestionIndex =
                        try {
                            recentQuestionIndexes.getNext()
                        } catch (e: Exception) {
                            val index = Random.nextInt(state.questions.size)
                            recentQuestionIndexes.add(index)
                            index
                        }
                    questionState.value = state.questions[nextQuestionIndex]
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "button next",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun ContentItem(
    title: String,
    answers: List<String>,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
    ) {
        Text(text = title)

        val offsetX = (maxWidth * 23 / 100)
        val width = (maxWidth - offsetX)
        Card(
            modifier = Modifier
                .offset(x = offsetX, y = 0.dp)
                .width(width),
            elevation = CardDefaults.outlinedCardElevation(4.dp)
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                answers.forEach { Text(text = it, modifier = Modifier.padding(12.dp)) }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun MainPreview() {
    AppTheme {
        Content(
            InterviewState.Content(
                listOf(
                    InterviewQuestion(
                        level = InterviewQuestion.Level.JUNIOR,
                        topic = "Базові питання",
                        question = "Що таке клас? Що таке інтерфейс? Яка між ними різниця?",
                        answer = listOf(
                            "Класс: Шаблон для создания объектов, содержащий их состояние (поля) и поведение (методы).",
                            "Интерфейс: Определяет поведение (методы), которое должны реализовывать классы, но не предоставляет их реализацию.",
                            "Различие: Классы представляют собой конкретную реализацию, в то время как интерфейсы определяют только структуру поведения, которую классы должны следовать.",
                        ),
                    ),
                )
            )
        )
    }
}
