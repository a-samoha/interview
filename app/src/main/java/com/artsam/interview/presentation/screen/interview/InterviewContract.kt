package com.artsam.interview.presentation.screen.interview

import android.content.Context
import com.artsam.interview.core.MviIntent
import com.artsam.interview.core.MviState
import com.artsam.interview.data.entity.InterviewQuestion

sealed interface InterviewState : MviState {

    data object Initialising : InterviewState
    class Content(val questions: List<InterviewQuestion>) : InterviewState
}

sealed interface InterviewIntent : MviIntent {

    data class FetchQuestions(val context: Context) : InterviewIntent
}
