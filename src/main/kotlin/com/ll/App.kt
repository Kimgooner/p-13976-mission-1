package com.ll

class App {
    val wiseSayingController = WiseSayingController()
    fun run() {
        println("== 명언 앱 ==")
        while (true) {
            print("명령) ")
            val input = readLine()!!
            val cmd = input.substringBefore("?")
            val queryString = input.substringAfter("?", "")

            val map = if (queryString.isNotBlank()) { // 쿼리가 비어있지 않은 경우
                queryString.split("&").associate { // &를 delim으로 나누기, associate로 map으로 전환
                    val (key, value) = it.split("=")
                    key to value
                }
            } else {
                emptyMap()
            }

            when (cmd) {
                "종료" -> break
                "등록" -> wiseSayingController.addWiseSaying()
                "목록" -> {
                    val type = map["keywordType"]?.trim() ?: ""
                    val keyword = map["keyword"]?.trim() ?: ""
                    val page = map["page"]?.trim()?.toInt() ?: 1
                    wiseSayingController.readWiseSaying(type, keyword, page)
                }
                "삭제" -> {
                    val index = map["id"]!!.trim().toInt()
                    wiseSayingController.deleteWiseSaying(index)
                }
                "수정" -> {
                    val index = map["id"]!!.trim().toInt()
                    wiseSayingController.updateWiseSaying(index)
                }
                "빌드" -> wiseSayingController.build()
            }
        }
    }
}