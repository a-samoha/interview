package com.artsam.interview.presentation.screen.interview

import androidx.lifecycle.viewModelScope
import com.artsam.interview.core.MviIntent
import com.artsam.interview.core.MviViewModel
import com.artsam.interview.data.JsonParser
import com.artsam.interview.data.QuestionsRepository
import com.artsam.interview.presentation.screen.interview.InterviewIntent.FetchQuestions
import com.artsam.interview.presentation.screen.interview.InterviewState.Initialising
import kotlinx.coroutines.launch

class InterviewViewModel() : MviViewModel<InterviewState, InterviewIntent>() {

    override val emptyState = Initialising

    override fun handleIntent(intent: MviIntent) {
        when (intent) {
            is FetchQuestions -> {
                viewModelScope.launch {
                    val jsonString =
                        QuestionsRepository().getJsonDataFromAsset(
                            intent.context,
                            "dou_android_interview_ru.json"
                        )
                    setState(InterviewState.Content(JsonParser.parseQuestionJson(jsonString)))
                }
            }
        }
    }
}
