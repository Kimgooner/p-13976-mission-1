package com.ll

class WiseSayingService {
    val wiseSayingRepository = WiseSayingRepository();

    fun getIndex(): Int {
        return wiseSayingRepository.loadIndex()
    }

    fun setIndex(index: Int) {
        return wiseSayingRepository.saveIndex(index)
    }

    fun findById(id: Int): WiseSaying {
        val json = wiseSayingRepository.loadById(id)
        return textToData(json)
    }

    fun findAll(keywordType: String = "", keyword: String = ""): List<WiseSaying> {
        val jsons: List<String> = wiseSayingRepository.loadAll()
        val allList = jsons.map { json -> textToData(json) }

        if (keywordType.isBlank()) {
            return allList
        }

        return allList.filter { w ->
            if (keywordType == "author") {
                w.author.contains(keyword)
            } else {
                w.content.contains(keyword)
            }
        }
    }

    fun textToData(json: String): WiseSaying{
        if (json.equals("err")) {
            return WiseSaying(0, "", "")
        }

        val idRegex = """"id"\s*:\s*(\d+)""".toRegex()
        val contentRegex = """"content"\s*:\s*"([^"]*)"""".toRegex()
        val authorRegex = """"author"\s*:\s*"([^"]*)"""".toRegex()

        val id = idRegex.find(json)?.groupValues?.get(1)?.toInt() ?: 0
        val content = contentRegex.find(json)?.groupValues?.get(1) ?: ""
        val author = authorRegex.find(json)?.groupValues?.get(1) ?: ""

        return WiseSaying(id, author, content)
    }

    fun deleteById(id: Int) {
        wiseSayingRepository.deleteById(id)
    }

    fun updateById(id: Int, author: String, content: String) {
        wiseSayingRepository.saveWiseSaying(WiseSaying(id, author, content))
    }

    fun saveWiseSaying(author: String, content: String) {
        var index = wiseSayingRepository.loadIndex()
        wiseSayingRepository.saveWiseSaying(WiseSaying(index, author, content))
        wiseSayingRepository.saveIndex(index+1)
    }

    fun writeData(){
        val items = mutableListOf<String>()
        val index = wiseSayingRepository.loadIndex()
        for(i in index downTo 1) {
            val read = findById(i)
            if(read.index == 0) continue
            items.add(read.toJsonForData())
        }
        val data = "[\n" + items.joinToString(",\n") + "\n]"
        wiseSayingRepository.saveData(data)
    }
}