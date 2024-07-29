package com.artsam.interview.presentation.screen.interview

private const val MAX_INDEX = 10

class RecentValues<T>(private val capacity: Int) {
    private val deque = ArrayDeque<T>(capacity)
    private var previousIndex = 0

    fun add(value: T) {
        if (deque.size == capacity) deque.removeFirst()
        deque.addLast(value)
        previousIndex = deque.size - 1
        println("test add() previousIndex $previousIndex")
    }

    fun getPrevious(): T =
        deque[
            when (previousIndex) {
                0 -> 0
                MAX_INDEX -> previousIndex - 2
                else -> --previousIndex
            }]

    fun getNext(): T {
        val index = ++previousIndex
        println("test getNext() index $index previousIndex $previousIndex")
        if (index != MAX_INDEX) return deque[index]
        else throw IndexOutOfBoundsException("")
    }
}