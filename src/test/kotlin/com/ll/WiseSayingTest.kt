package com.ll

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import java.io.File
import kotlin.test.Test

class WiseSayingTest {
    val wiseSayingRepository = WiseSayingRepository()

    @BeforeEach
    fun cleanDbFolder() {
        val dir = File("db/wiseSaying")
        if (dir.exists() && dir.isDirectory) {
            dir.listFiles()?.forEach { file ->
                if (file.isFile) {
                    file.delete()
                } else if (file.isDirectory) {
                    file.deleteRecursively()
                }
            }
        }
    }

    @Test
    fun `통합 테스트`() {
        val result = TestRunner.run(
        """
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1           
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                빌드
                목록
        """.trimIndent()
        )

            assertThat(result).contains("1번 명언이 등록되었습니다.")
            assertThat(result).contains("2번 명언이 등록되었습니다.")
            assertThat(result).contains("번호 / 작가 / 명언")
            assertThat(result).contains("----------------------")
            assertThat(result).contains("2 / 작자미상 / 과거에 집착하지 마라.")
            assertThat(result).contains("1 / 작자미상 / 현재를 사랑하라.")
            assertThat(result).contains("1번 명언이 삭제되었습니다.")
            assertThat(result).contains("1번 명언은 존재하지 않습니다.")
            assertThat(result).contains("명언(기존) : 과거에 집착하지 마라.")
            assertThat(result).contains("작가(기존) : 작자미상")
            assertThat(result).contains("번호 / 작가 / 명언")
            assertThat(result).contains("----------------------")
            assertThat(result).contains("2 / 홍길동 / 현재와 자신을 사랑하라.")
            assertThat(result).contains("data.json 파일의 내용이 갱신되었습니다.")
    }

    @Test
    fun `통합 테스트2`() {
        val result = TestRunner.run(
            """
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                목록?keywordType=author&keyword=작자
                종료
        """.trimIndent()
        )

        assertThat(result).contains("1번 명언이 등록되었습니다.")
        assertThat(result).contains("2번 명언이 등록되었습니다.")
        assertThat(result).contains("----------------------")
        assertThat(result).contains("검색타입 : content")
        assertThat(result).contains("검색어 : 과거")
        assertThat(result).contains("----------------------")
        assertThat(result).contains("번호 / 작가 / 명언")
        assertThat(result).contains("----------------------")
        assertThat(result).contains("2 / 작자미상 / 과거에 집착하지 마라.")
        assertThat(result).contains("----------------------")
        assertThat(result).contains("검색타입 : author")
        assertThat(result).contains("검색어 : 작자")
        assertThat(result).contains("----------------------")
        assertThat(result).contains("번호 / 작가 / 명언")
        assertThat(result).contains("----------------------")
        assertThat(result).contains("2 / 작자미상 / 과거에 집착하지 마라.")
        assertThat(result).contains("1 / 작자미상 / 현재를 사랑하라.")
    }

    @Nested
    inner class GeneratedTest {
        @BeforeEach
        fun setUp() {
            for(i in 1 until 11) {
                var index = wiseSayingRepository.loadIndex()
                wiseSayingRepository.saveWiseSaying(WiseSaying(index++, "작자미상 $i", "명언 $i"))
                wiseSayingRepository.saveIndex(index)
            }
        }

        @Test
        fun `통합 테스트3`() {
            val result = TestRunner.run(
                """
                목록
                목록?page=2
                종료
        """.trimIndent()
            )
            assertThat(result).contains("번호 / 작가 / 명언")
            assertThat(result).contains("----------------------")
            assertThat(result).contains("10 / 작자미상 10 / 명언 10")
            assertThat(result).contains("9 / 작자미상 9 / 명언 9")
            assertThat(result).contains("8 / 작자미상 8 / 명언 8")
            assertThat(result).contains("7 / 작자미상 7 / 명언 7")
            assertThat(result).contains("6 / 작자미상 6 / 명언 6")
            assertThat(result).contains("----------------------")
            assertThat(result).contains("페이지 : [1] / 2")
            assertThat(result).contains("번호 / 작가 / 명언")
            assertThat(result).contains("----------------------")
            assertThat(result).contains("5 / 작자미상 5 / 명언 5")
            assertThat(result).contains("4 / 작자미상 4 / 명언 4")
            assertThat(result).contains("3 / 작자미상 3 / 명언 3")
            assertThat(result).contains("2 / 작자미상 2 / 명언 2")
            assertThat(result).contains("1 / 작자미상 1 / 명언 1")
            assertThat(result).contains("----------------------")
            assertThat(result).contains("페이지 : 1 / [2]")
        }
    }
}