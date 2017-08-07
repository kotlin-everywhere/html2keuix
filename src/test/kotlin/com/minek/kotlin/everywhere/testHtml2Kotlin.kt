package com.minek.kotlin.everywhere

import com.minek.kotlin.everywhere.html2keuix.html2kotlin
import org.junit.Test
import kotlin.test.assertEquals


class TestHtml2Kotlin {
    @Test
    fun testTextNode() {
        // test empty string
        assertEquals("", html2kotlin(""))

        // test string only
        assertEquals("""Html.text("Hello, world!")""", html2kotlin("Hello, world!"))
    }

    @Test
    fun testElement() {
        assertEquals("Html.div()", html2kotlin("<div></div>"))
    }

    @Test
    fun testStringProperty() {
        assertEquals(
                """Html.div(class_("col-md-10 col-md-offset-12"))""",
                html2kotlin("""<div class="col-md-10 col-md-offset-12"></div>""")
        )
    }

    @Test
    fun testBooleanProperty() {
        assertEquals(
                "Html.input(disabled(true))",
                html2kotlin("""<input disabled>""")
        )
    }

    @Test
    fun testNested() {
        // nested div
        assertEquals("Html.div {\n    div()\n}", html2kotlin("<div><div></div></div>"))
        // nested div & text
        assertEquals("Html.div {\n    div {\n        +\"text\"\n    }\n}", html2kotlin("<div><div>text</div></div>"))
    }

    @Test
    fun testMultipleRootElements() {
        assertEquals("Html.div()\nHtml.div()", html2kotlin("<div></div><div></div>"))
    }

    @Test
    fun testTrimString() {
        // 들여쓰기용 공백은 모두 무시
        assertEquals("Html.div {\n    div {\n        +\"text\"\n    }\n}", html2kotlin("<div>\n  <div>\n    text\n  </div>\n</div>"))
    }
}