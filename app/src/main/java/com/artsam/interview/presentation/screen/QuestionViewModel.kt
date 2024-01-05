package com.artsam.interview.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artsam.interview.data.JsonParser
import com.artsam.interview.data.entity.InterviewQuestion
import com.artsam.interview.presentation.screen.QuestionUiState.Initialising
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel() : ViewModel() {

    private val mutableState by lazy { MutableStateFlow<QuestionUiState>(Initialising) }
    val state by lazy { mutableState.asStateFlow() }

    private val jsonString = """
            [
              {
                "levelTitle": "Junior",
                "questions": [
                  {
                    "topic": "Базові питання",
                    "questions": [
                      {
                        "questionText": "Назвіть основні принципи ООП",
                        "answerItems": [
                          "Абстракция: Представление реальных объектов в коде. Она позволяет сконцентрироваться на важных аспектах объекта, игнорируя несущественные.",
                          "Инкапсуляция: Скрытие деталей реализации. Это обеспечивает безопасность данных и уменьшает сложность программы.",
                          "Наследование: Использование свойств и методов одного класса в другом. Это способствует повторному использованию кода.",
                          "Полиморфизм: Возможность использовать объекты разных классов с одинаковым интерфейсом без информации о конкретном классе. Это упрощает работу с различными типами данных."
                        ]
                      },
                      {
                        "questionText": "Що таке клас? Що таке інтерфейс? Яка між ними різниця?",
                        "answerItems": [
                          "Класс: Шаблон для создания объектов, содержащий их состояние (поля) и поведение (методы).",
                          "Интерфейс: Определяет поведение (методы), которое должны реализовывать классы, но не предоставляет их реализацию.",
                          "Различие: Классы представляют собой конкретную реализацию, в то время как интерфейсы определяют только структуру поведения, которую классы должны следовать."
                        ]
                      }
                    ]
                  }
                ]
              }
            ]
            """.trimIndent()

    init {
        viewModelScope.launch {
            delay(2000)
            mutableState.value = QuestionUiState.Content(
                JsonParser.parseQuestionJson(jsonString)
            )
        }
    }
}

sealed class QuestionUiState {
    data object Initialising : QuestionUiState()
    class Content(val questions: List<InterviewQuestion>) : QuestionUiState()
}
