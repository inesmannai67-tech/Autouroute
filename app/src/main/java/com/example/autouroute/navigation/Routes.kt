package com.example.autouroute.navigation

object Routes {
    const val LANGUAGE = "language"
    const val WELCOME = "welcome"
    const val REGISTER = "register"
    const val MAIN = "main"
    const val REPORT = "report/{type}"
    const val SUCCESS = "success"
    const val ADMINISTRATION = "administration"

    fun report(type: String) = "report/$type"
}
