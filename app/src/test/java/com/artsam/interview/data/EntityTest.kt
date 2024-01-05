package com.artsam.interview.data

import com.artsam.interview.data.entity.InterviewQuestion
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EntityTest {

    @Test
    fun `parse json for InterviewQuestion instance`() {
        // set
        val jsonString = """
            [
              {
                "levelTitle": "Junior",
                "questions": [
                  {
                    "topic": "testTopic",
                    "questions": [
                      {
                        "questionText": "testQuestion",
                        "answerItems": [
                          "testL1",
                          "testL2"
                          ]
                      }
                    ]
                  }
                ]
              }
            ]
            """.trimIndent()
        val expected = InterviewQuestion(
            level = InterviewQuestion.Level.JUNIOR,
            topic = "testTopic",
            question = "testQuestion",
            answer = listOf("testL1", "testL2"),
        )
        // do
        val actual = JsonParser.parseQuestionJson(jsonString)
        // check
        assertEquals(expected.level, actual[0].level)
        assertEquals(expected.topic, actual[0].topic)
        assertEquals(expected.question, actual[0].question)
        assertEquals(expected.answer, actual[0].answer)
    }
}