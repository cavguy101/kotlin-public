package converter

import java.math.BigDecimal
import java.math.BigInteger

// convert num from base 'radix' to decimal
fun toDec(num: String, radix: Int): BigDecimal
{
    var dec: BigDecimal = BigDecimal.ZERO
    var dec2: BigDecimal = BigDecimal.ZERO
    var digit: BigDecimal
    var exp = BigDecimal.ONE
    val intPart: String
    val decPart: String

    val numString = num.uppercase()
    val indexOfDecimal = numString.indexOf(".")

    // assign variables depending on if a decimal point '.' is present
    if (indexOfDecimal == -1) {
        intPart = numString
        decPart = ""
    } else {
        intPart = numString.substring(0, indexOfDecimal)
        decPart = numString.substring(indexOfDecimal)
    }

    // convert integer part of decimal
    for (i in intPart.length - 1 downTo 0) {
        digit = if (intPart[i] in 'A'..'Z') (intPart[i] - 'A' + 10).toBigDecimal() else (intPart[i] - '0').toBigDecimal()
        dec += (digit * exp)
        exp *= radix.toBigDecimal()
    }

    // convert fractional part of decimal
    exp = BigDecimal.ONE
    for (i in 1 until decPart.length) {
        digit = if (decPart[i] in 'A'..'Z') (decPart[i] - 'A' + 10).toBigDecimal() else (decPart[i] - '0').toBigDecimal()
        exp *= radix.toBigDecimal()
        dec2 += (digit.setScale(5) / exp.setScale(5))
    }

    return if (decPart != "") dec.setScale(5) + dec2 else dec
}

// convert num from decimal to base 'radix'
fun fromDec(num: BigDecimal, radix: Int): String
{
    var intPart: BigInteger = num.toBigInteger()
    var fracPart: BigDecimal = num - intPart.toBigDecimal()
    var outputStr = ""
    var outputStr2 = ""
    var digit: Int
    val exp: BigInteger = radix.toBigInteger()

    val indexOfDecimal = num.toString().indexOf(".")

    // convert integer part of decimal
    do {
        digit = intPart.mod(exp).toInt()
        outputStr = if (digit < 10) (digit + '0'.code).toChar() + outputStr else (digit + 'A'.code - 10).toChar()  + outputStr
        intPart /= exp
    } while (intPart > BigInteger.ZERO)

    // convert fractional part of decimal
    do {
        digit = (fracPart * radix.toBigDecimal()).toInt()
        outputStr2 += if (digit < 10) (digit + '0'.code).toChar() else (digit + 'A'.code - 10).toChar()
        fracPart *= radix.toBigDecimal()
        fracPart = fracPart.subtract(fracPart.toBigInteger().toString().toBigDecimal())
    } while (fracPart > BigDecimal.ZERO && outputStr2.length < 5)

    return if (indexOfDecimal == -1)  outputStr else "$outputStr.${outputStr2.padEnd(5, '0')}"
}

fun main() {
    var userInput: String
    var userInput2: String
    var intermediate: BigDecimal
    var target: String

    do {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        userInput = readln()

        if (userInput != "/exit") {
            val (from_base, to_base) = userInput.split(" ").map { it.toInt() }
            do {
                println("\nEnter number in base $from_base to convert to base $to_base (To go back type /back)")
                userInput2 = readln()
                if (userInput2 != "/back") {
                    intermediate = toDec(userInput2, from_base)
                    target = fromDec(intermediate, to_base)
                    print("Conversion result: $target")
                }
            } while (userInput2 != "/back")
        }
    } while (userInput != "/exit")
}