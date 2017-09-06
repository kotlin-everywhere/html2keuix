package com.minek.kotlin.everywhere.html2keuix


import com.minek.kotlin.everywhere.keuix.browser.html.*
import com.minek.kotlin.everywhere.keuix.browser.runBeginnerProgram
import kotlin.browser.document

data class Model(val html: String = "")

val examples = listOf("ONE" to "<h1>Hello</h1>", "TWO" to "<ul><li>1</li><li>2</li></ul>")

sealed class Msg
data class NewHtml(val html: String) : Msg()

fun update(msg: Msg, model: Model): Model {
    return when (msg) {
        is NewHtml -> model.copy(html = msg.html)
    }
}


fun view(model: Model): Html<Msg> {
    return Html.div(class_("wrap")) {
        +viewHead

        div(class_("container")) {
            div(class_("form-inline"), style("text-align:center")) {
                div(class_("control-label col-md-12 _bTitle"), text = "Html2Keuix is help to your easy programming")
                div(class_("control-label col-md-12 _subTitle1"), text = "Input your Html code in textarea.")
                div(class_("control-label col-md-12 _subTitle2"), text = "It's help to translate to keuix code automatically.")
                div(class_("col-md-6")) {
                    textarea(class_("rounded form-control _textarea"), value(model.html), onInput(::NewHtml))
                }
                div(class_("col-md-6")) {
                    pre(class_("form-control rounded _pre"), text = html2kotlin(model.html))
                }
            }
        }
        +viewFooter
    }
}

val viewHead: Html<Msg> = Html.div(class_("header")) {
    div(class_("col-md-12 _headerTitle")) {
        span(class_("_headerTitle")) { +"Html2Keuix" }
        span(class_("_headerRightWrap")){

            a(class_("_gitUrl"), href("https://github.com/kotlin-everywhere/html2keuix")) {
                img(class_("_gitImg img-circle"), src("https://github.com/apple-touch-icon.png"))
                + "https://github.com/kotlin-everywhere/html2keuix"
            }
        }
    }
}

val viewFooter: Html<Msg> = Html.div(class_("footer")) {
    div(class_("col-md-12 _btnWrap")) {
        examples.forEach {
            button(class_("btn btn-secondary _exampleBtn"), onClick(NewHtml(it.second)), text = it.first)
        }
    }
}

@JsName("main")
fun main(args: Array<String>) {
    runBeginnerProgram(document.querySelector(args[0])!!, Model(), ::update, ::view)
}