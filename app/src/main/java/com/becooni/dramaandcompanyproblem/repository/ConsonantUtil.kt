package com.becooni.dramaandcompanyproblem.repository

import javax.inject.Inject

class ConsonantUtil @Inject constructor() {

    private val initialConsonants = arrayOf(
        "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
        "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
        "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
        "ㅋ", "ㅌ", "ㅍ", "ㅎ"
    )

    private fun isHangul(char: Char) = isHangul(char.toInt())

    private fun isHangul(code: Int) = code in 0xAC00..0xD7A3

    fun getInitial(str: String): Char? {
        val initial = str.firstOrNull() ?: return null

        if (!isHangul(initial)) return initial

        val a = (initial - 0xAC00).toInt()

        val result = ((a - (a % 28)) / 28) / 21

        return initialConsonants[result].first()
    }
}