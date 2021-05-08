
fun main(){
    val n1: Double = Double.NaN
    val n2: Double = Double.NaN
    println(n1 == n2)

    val n3: Any? = Double.NaN
    val n4: Any? = Double.NaN
    println(n3 == n4)

    val n5: Float = -0.0f
    val n6: Float = 0.0f
    println(n5 == n6)

    val n7: Any? = -0.0f
    val n8: Any? = 0.0f
    println(n7 == n8)

}