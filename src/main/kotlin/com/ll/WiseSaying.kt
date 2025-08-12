package com.ll

class WiseSaying(val index: Int, var author: String, var content: String) {
    fun print() {
        println("$index / $author / $content")
    }

    fun toJson(): String {
        return """
        {
            "id": ${index},
            "content": "${content}",
            "author": "${author}"
        }
    """.trimIndent()
    }

    fun toJsonForData(): String {
        return "\t{\n" +
                "\t\t\"id\": ${index},\n" +
                "\t\t\"content\": \"${content}\",\n" +
                "\t\t\"author\": \"${author}\"\n" +
                "\t}"
    }
}