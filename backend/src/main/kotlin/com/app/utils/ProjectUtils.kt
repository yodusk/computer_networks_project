package com.app.utils

class ProjectUtils {

  companion object {
    private fun getRandomString(length: Int): String {
      val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
      return (1..length).map { allowedChars.random() }.joinToString("")
    }

    private const val randomIdLength = 10

    fun getProjectName(name: String): String {
      return "$name-${this.getRandomString(randomIdLength)}"
    }
  }
}
