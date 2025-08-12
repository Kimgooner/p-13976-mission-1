package com.ll
import java.io.File

class WiseSayingRepository {
    val index_file = "db/wiseSaying/lastId.txt"

    fun loadIndex(): Int{
        val file = File(index_file)
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("1")
        }

        return file.readText().toInt()
    }

    fun saveIndex(index: Int) {
        val file = File(index_file)
        file.writeText(index.toString())
    }

    fun saveWiseSaying(wise: WiseSaying) {
        val file = File("db/wiseSaying/${wise.index}.json")
        file.writeText(wise.toJson())
    }

    fun loadById(id: Int): String {
        val file = File("db/wiseSaying/${id}.json")
        if (!file.exists()) {
            return "err"
        }
        return file.readText()
    }

    fun loadAll(): List<String> {
        val dir = File("db/wiseSaying")
        return dir.listFiles { file -> file.extension == "json" }
            ?.sortedByDescending { file ->
                file.nameWithoutExtension.toIntOrNull() ?: 0
            }
            ?.map { it.readText() }
            ?: emptyList()
    }

    fun deleteById(id: Int) {
        val file = File("db/wiseSaying/${id}.json")
        file.delete()
    }

    fun saveData(data: String) {
        val file = File("db/wiseSaying/data.json")
        file.writeText(data)
    }
}