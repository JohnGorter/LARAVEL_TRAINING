data class Person(val country: Country?)
data class Country(val code: String?)

fun main() {
    val p: Person? = Person(Country("ENG"))
    var res: String?

    // method 1. null checks
    if (p != null && p.country != null && p.country.code != null) {
        print (p.country.code)
        res = p.country.code
        print(res == "ENG")
    }

    // method 2. safe calls
    print (p?.country?.code)
    res = p?.country?.code
    print(res == "ENG")


    // method 3. !! operator  
    // COMMENT OUT THIS CODE IF YOU START AT method 4.
    // print (p!!.country!!.code)
    // res = p.country!!.code
    // print(res == "ENG")
    

    // method 4. Safe casts
    val result: Any? = p?.country?.code
    res = result as? String
    
    print(res == "ENG")
     
}