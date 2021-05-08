package test.kotlin.com.example

import com.example.Properties
import com.example.StringTemplates
import com.example.hello
import main.kotlin.com.example.main
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class StringTemplatesTest {

    val stringTemplates = StringTemplates()

    @Test
    fun testHelloName() {
        assertEquals("Hello Peter", stringTemplates.helloName("Peter"));
    }

    @Test
    fun testHelloNames() {
        val names = arrayOf("Peter", "Ann")
        val helloNames = stringTemplates.helloNames(names);
        assertEquals("Hello Peter with index: 0", helloNames[0])
        assertEquals("Hello Ann with index: 1", helloNames[1])
    }
}