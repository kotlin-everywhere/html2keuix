package com.minek.kotlin.everywhere.html2keuix

import com.minek.kotlin.everywhere.keuix.browser.*
import com.minek.kotlin.everywhere.keuix.browser.html.*
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
    /**
     * Jiyeon Bootstrap 디자인
     *
     */

    Html.div(class_("wrap")){
        div(class_("header"), style("")) {
            div(class_("col-md-12 _headerTitle")) {
                +"Html2Keuix"
                div(class_("_headerRightWrap")){

                    a(class_("_gitUrl"), href("https://github.com/kotlin-everywhere/html2keuix")) {
                        img(class_("_gitImg img-circle"), src("https://github.com/apple-touch-icon.png"))
                        + "https://github.com/kotlin-everywhere/html2keuix"
                    }
                }
            }
        }
        div(class_("container")){
            div(class_("form-inline"), style("text-align:center; ")) {
                div(class_("control-label col-md-12 _bTitle")) { +"Html2Keuix is help to your easy programming" }
                div(class_("control-label col-md-12 _subTitle1")) { +"Input your Html code in textarea." }
                div(class_("control-label col-md-12 _subTitle2")) { +"It's help to translate to keuix code automatically." }
                div(class_("col-md-6")){
                    textarea(class_("rounded form-control _textarea"), value(html), onInput(::NewHtml))
                }
                div(class_("col-md-6")) {
                    pre(class_("form-control rounded _pre"), text = html2kotlin(html))
                }
            }

        }
        div(class_("footer")) {
            div(class_("col-md-12 _btnWrap")) {
                button(class_("btn btn-secondary _exampleBtn"), dataset("toggle","tooltip"), dataset("placement","top"), attribute("type","button"),
                        attribute("title","Apple")) { +"Example1" }
                button(class_("btn btn-secondary _exampleBtn"), dataset("toggle","tooltip"), dataset("placement","top"), attribute("type","button"),
                        attribute("title","Apple")) { +"Example2" }
            }
        }
    }
}

@JsName("main")
fun main(args: Array<String>) {
    runBeginnerProgram(document.querySelector(args[0])!!, Model(), update, view)
}