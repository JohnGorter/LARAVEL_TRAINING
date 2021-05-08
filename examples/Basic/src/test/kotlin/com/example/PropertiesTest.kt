package test.kotlin.com.example

import com.example.Properties
import com.example.hello
import main.kotlin.com.example.main
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class PropertiesTest {

    @Test
    fun testProperties() {
        var properties = Properties("Freek", 23)

        assertEquals("Freek", properties.firstName);
        assertEquals(23, properties.age)

        properties.age = 24
        assertEquals(24, properties.age)

    }
}