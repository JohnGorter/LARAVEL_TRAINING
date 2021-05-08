package test.kotlin.com.example

import com.example.Loops
import com.example.Properties
import com.example.hello
import main.kotlin.com.example.main
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class LoopsTest {

    private val loops = Loops()

    @Test
    fun testSimpleLoop() {
        val total = loops.simpleLoop()
        assertEquals(10, total)

    }

    @Test
    fun testSimpleLoopSteps() {
        val total = loops.simpleLoopSteps()
        assertEquals(9, total)

    }

    @Test
    fun testCountDownLoop() {
        val total = loops.countDownLoop();
        assertEquals(6, total)
    }

    @Test
    fun testExclusiveLoop() {
        val total = loops.exclusiveLoop()
        assertEquals(3, total)
    }

    @Test
    fun testMapLoop() {
        val total = loops.mapLoop()
        assertEquals(16, total)
    }

    @Test
    fun testLoopWithIndex() {
        val names = arrayOf("Ann", "Sophie", "Wim")
        val resultArray = loops.loopWithIndex(names)
        assertEquals("Index 0 has name Ann", resultArray[0])
        assertEquals("Index 1 has name Sophie", resultArray.get(1))
    }
}