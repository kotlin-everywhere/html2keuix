package com.minek.kotlin.everywhere.html2keuix


import com.minek.kotlin.everywhere.keuix.browser.html.*
import com.minek.kotlin.everywhere.keuix.browser.runBeginnerProgram
import kotlin.browser.document

data class Model(val html: String = "")

val o = """
    | <div class="container">
    |   <form class="form-signin">
    |     <h2 class="form-signin-heading">Please sign in</h2>
    |     <label for="inputEmail" class="sr-only">Email address</label>
    |     <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
    |     <label for="inputPassword" class="sr-only">Password</label>
    |     <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
    |     <div class="checkbox">
    |       <label>
    |         <input type="checkbox" value="remember-me"> Remember me
    |       </label>
    |     </div>
    |     <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    |   </form>
    | </div>""".trimMargin()

val t = """
    | <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    |   <a class="navbar-brand" href="#">Fixed navbar</a>
    |   <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
    |   <span class="navbar-toggler-icon"></span> </button>
    |   <div class="collapse navbar-collapse" id="navbarCollapse">
    |       <ul class="navbar-nav mr-auto">
    |           <li class="nav-item active">
    |               <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
    |           </li>
    |           <li class="nav-item">
    |               <a class="nav-link" href="#">Link</a>
    |           </li>
    |           <li class="nav-item">
    |               <a class="nav-link disabled" href="#">Disabled</a>
    |           </li>
    |       </ul>
    |       <form class="form-inline mt-2 mt-md-0">
    |           <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
    |           <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    |       </form>
    |   </div>
    | </nav>
    | <div class="container">
    |   <div class="mt-3">
    |       <h1>Sticky footer with fixed navbar</h1>
    |   </div>
    |   <p class="lead">
    |       Pin a fixed-height footer to the bottom of the viewport in desktop browsers with this custom HTML and CSS. A fixed navbar has been added with <code>padding-top: 60px;</code> on the <code>body &gt; .container</code>.
    |   </p>
    |   <p>
    |       Back to <a href="../sticky-footer">the default sticky footer</a> minus the navbar.
    |   </p>
    | </div>
    | <footer class="footer">
    |   <div class="container">
    |       <span class="text-muted">Place sticky footer content here.</span>
    |   </div>
    | </footer>""".trimMargin()

val examples = listOf("ONE" to o, "TWO" to t)

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