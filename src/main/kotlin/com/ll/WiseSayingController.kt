package com.ll

import kotlin.math.floor

class WiseSayingController {
    val wiseSayingService = WiseSayingService()

    fun addWiseSaying() {
        print("명언 : ")
        val content = readLine()!!
        print("작가 : ")
        val author = readLine()!!
        val index = wiseSayingService.getIndex()
        wiseSayingService.saveWiseSaying(author,content)
        println((index).toString() + "번 명언이 등록되었습니다.")
    }

    fun readWiseSaying(keywordType: String, keyword: String, page: Int) {
        val list = wiseSayingService.findAll()

        val filteredList = if (keywordType.isEmpty()) {
            list
        } else {
            println("----------------------")
            print("검색타입 : ")
            println(keywordType)
            print("검색어 : ")
            println(keyword)
            println("----------------------")

            list.filter { w ->
                if (keywordType == "author") {
                    w.author.contains(keyword)
                } else {
                    w.content.contains(keyword)
                }
            }
        }

        println("번호 / 작가 / 명언")
        println("----------------------")

        val pageSize = 5
        val fromIndex = (page - 1) * pageSize
        val toIndex = (fromIndex + pageSize).coerceAtMost(filteredList.size)

        filteredList.subList(fromIndex, toIndex).forEach { it.print() }

        print("페이지 : ")
        val maxPage = (filteredList.size / 5) + 1
        for(i in 1 until maxPage) {
            if(i == page) print("[$i]")
            else print("$i")

            if(i != maxPage-1) print(" / ")
        }
        println()
    }

    fun deleteWiseSaying(index: Int) {
        val target = wiseSayingService.findById(index)
        if(target.index == 0) {
            println("${index}번 명언은 존재하지 않습니다.")
            return
        }
        wiseSayingService.deleteById(index)
        println("${index}번 명언이 삭제되었습니다.")
    }

    fun updateWiseSaying(index: Int) {
        val target = wiseSayingService.findById(index)
        if(target.index == 0) {
            println("${index}번 명언은 존재하지 않습니다.")
            return
        }
        println("명언(기존) : ${target.content}")
        print("명언 : ")
        val newContent = readLine()!!
        println("작가(기존) : ${target.author}")
        print("작가 : ")
        val newAuthor = readLine()!!
        wiseSayingService.updateById(index, newAuthor, newContent)
    }

    fun build(){
        wiseSayingService.writeData()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }

}