package com.kotlin.everywhere.html2kotlin

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.get
import org.w3c.dom.parsing.DOMParser

private val booleanPropertyNames = setOf("disabled")

private fun fixName(name: String): String {
    return when (name) {
        "class" -> "class_"
        else -> name
    }
}

private fun indent(depth: Int): String {
    if (depth > 0) {
        return "    ".repeat(depth)
    } else {
        return "Html."
    }
}

private fun render(depth: Int, node: Node): String {
    return when (node.nodeType) {
        Node.TEXT_NODE -> {
            if (depth == 0) {
                """${indent(depth)}text("${node.textContent}")"""
            }
            else {
                """${indent(depth)}+"${node.textContent}""""
            }
        }
        Node.ELEMENT_NODE -> {
            val element = node as Element
            val attributes = (0..(element.attributes.length - 1))
                    .map { element.attributes[it]?.let { it.name to it.value } }
                    .filterNotNull()
                    .map {
                        (name, value) ->
                        """${fixName(name)}(${if (booleanPropertyNames.contains(name)) true else """"$value""""})"""
                    }
            val invoker =
                    if (attributes.isNotEmpty() || !node.hasChildNodes())
                        attributes.joinToString(", ", prefix = "(", postfix = ")")
                    else ""
            val row = """${indent(depth)}${node.nodeName.toLowerCase()}$invoker"""
            if (node.hasChildNodes()) {
                val children = (0..(node.childNodes.length - 1))
                        .map { node.childNodes[it] }
                        .filterNotNull()
                        .map { render(depth + 1, it) }
                        .joinToString("\n", prefix = " {\n", postfix = "\n${"    ".repeat(depth)}}")
                "$row$children"
            } else {
                row
            }
        }
        else -> """throw NotImplemented("$node")"""
    }
}

fun html2kotlin(html: String): String {
    if (html.isEmpty()) {
        return ""
    }

    val body = DOMParser().parseFromString(html, "text/html").getElementsByTagName("body")[0]

    if (body == null || body.childNodes.length == 0) {
        return ""
    }

    if (body.childNodes.length == 1) {
        val head = body.childNodes[0]!!
        return render(0, head)
    }

    return "error"
}