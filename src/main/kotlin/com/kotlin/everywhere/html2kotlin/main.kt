package com.kotlin.everywhere.html2kotlin

import com.github.kotlin.everywhere.browser.Html
import com.github.kotlin.everywhere.browser.onInput
import com.github.kotlin.everywhere.browser.runBeginnerProgram
import com.github.kotlin.everywhere.browser.value
import kotlin.browser.document

data class Model(val html: String = "")

sealed class Msg
data class NewHtml(val html: String) : Msg()

val update: (msg: Msg, model: Model) -> Model = { msg, model ->
    when (msg) {
        is NewHtml -> model.copy(html = msg.html)
    }
}

val view: (model: Model) -> Html<Msg> = { (html) ->
    Html.div {
        textarea(value(html), onInput(::NewHtml))
        pre(text = html2kotlin(html))
    }
}

fun main(args: Array<String>) {
    runBeginnerProgram(document.querySelector(args[0])!!, Model(), update, view)
}

