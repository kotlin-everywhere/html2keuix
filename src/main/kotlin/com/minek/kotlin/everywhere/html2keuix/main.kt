package com.minek.kotlin.everywhere.html2keuix

import com.minek.kotlin.everywhere.keuix.browser.*
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
    Html.div(class_("container")) {
        div(class_("row")) {
            div(class_("col-md-12")) { +"Html2Keuix" }
        }

        div(class_("row")) {
            textarea(class_("col-md-6"), style("min-height: 500px"), value(html), onInput(::NewHtml))
            pre(class_("col-md-6"), style("min-height: 500px"), text = html2kotlin(html))
        }
    }
}

@JsName("main")
fun main(args: Array<String>) {
    runBeginnerProgram(document.querySelector(args[0])!!, Model(), update, view)
}

