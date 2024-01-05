package com.artsam.interview.data.entity

import java.util.Locale

class InterviewQuestion(
    val level: Level,
    val topic: String,
    val question: String,
    val answer: List<String>
) {

    enum class Level {
        JUNIOR, MIDDLE, SENIOR, UNKNOWN;

        companion object {
            fun from(value: String) = entries.find {
                it.name == value.uppercase(Locale.US)
            } ?: UNKNOWN
        }
    }

    enum class Topic {
        BASE, ALGORITHMS, STRUCTURES, STORAGE, NETWORK, MULTITHREADING, CORE, KOTLIN, ANDROID, UNKNOWN;

        companion object {
            fun from(value: String) = entries.find {
                it.name == value.uppercase(Locale.US)
            } ?: UNKNOWN
        }
    }
}
