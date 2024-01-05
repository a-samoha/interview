package com.artsam.interview.data

import com.artsam.interview.data.entity.InterviewQuestion
import org.json.JSONArray

class JsonParser {

    companion object {
        fun parseQuestionJson(jsonString: String): List<InterviewQuestion> {
            val resultList = mutableListOf<InterviewQuestion>()
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val level = jsonArray.getJSONObject(i)
                val levelTitle = level.getString("levelTitle")
                val questionsArray = level.getJSONArray("questions")

                for (j in 0 until questionsArray.length()) {
                    val question = questionsArray.getJSONObject(j)
                    val topic = question.getString("topic")
                    val questions = question.getJSONArray("questions")

                    for (k in 0 until questions.length()) {
                        val questionItem = questions.getJSONObject(k)
                        val questionText = questionItem.getString("questionText")
                        val answerItems = questionItem.getJSONArray("answerItems")

                        val answers = mutableListOf<String>()
                        for (l in 0 until answerItems.length()) {
                            val answer = answerItems.getString(l)
                            answers.add(answer)
                        }

                        resultList.add(
                            InterviewQuestion(
                                InterviewQuestion.Level.from(levelTitle),
                                topic,
                                questionText,
                                answers
                            )
                        )
                    }
                }
            }

            return resultList
        }
    }
}
