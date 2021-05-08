package test.kotlin.com.example

import com.example.CountryEnum
import com.example.CountryEnum.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class CountryEnumTest {
    @Test
    fun testLanguage() {
        assertEquals("NL", CountryEnum.NETHERLANDS.countrycode)
        assertEquals("Countrycode: NL", CountryEnum.NETHERLANDS.retrieveCountryCodeText())
    }

    fun retrieveGoodmorning(country: CountryEnum): String =
        when (country) {
            GERMANY -> "Guten morgen"
            ENGLAND -> "Good morning"
            BELGIUM, NETHERLANDS -> "Goede morgen"
        }

    @Test
    fun testGoodmorning() {
        assertEquals("Guten morgen", retrieveGoodmorning(CountryEnum.GERMANY))
        assertEquals("Good morning", retrieveGoodmorning(CountryEnum.ENGLAND))
        assertEquals("Goede morgen", retrieveGoodmorning(CountryEnum.BELGIUM))
        assertEquals("Goede morgen", retrieveGoodmorning(CountryEnum.NETHERLANDS))
    }
}