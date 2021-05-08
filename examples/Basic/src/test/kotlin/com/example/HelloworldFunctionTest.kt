package test.kotlin.com.example

import com.example.hello
import main.kotlin.com.example.main
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class HelloworldFunctionTest {

    @Test
    fun testHelloWorldFunction() {
        assertEquals("Helloworld function", hello());
    }
}