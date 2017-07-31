package com.kotlin.everywhere.html2kotlin

import org.junit.Test
import kotlin.test.assertEquals


class TestHtml2Kotlin {
    @Test
    fun testTextNode() {
        // test empty string
        assertEquals("", html2kotlin(""))

        // test string only
        assertEquals("""text("Hello, world!")""", html2kotlin("Hello, world!"))
    }

    @Test
    fun testElement() {
        assertEquals("div()", html2kotlin("<div></div>"))
    }

    @Test
    fun testStringProperty() {
        assertEquals(
                """div(class_("col-md-10 col-md-offset-12"))""",
                html2kotlin("""<div class="col-md-10 col-md-offset-12"></div>""")
        )
    }

    @Test
    fun testBooleanProperty() {
        assertEquals(
                "input(disabled(true))",
                html2kotlin("""<input disabled>""")
        )
    }
}