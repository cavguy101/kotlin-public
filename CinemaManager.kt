package cinema

const val EMPTYSEAT = 'S'
const val OCCUPIED = 'B'

const val SEATS = 60
const val COST10 = 10
const val COST8 = 8

const val HEADING = "\nCinema:"
const val MENU = "\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit"

fun getMenuInput(): String {
    println(MENU)
    return(readln())
}

fun getCinemaSize(): List<Int> {
    println("Enter the number of rows:")
    val rows: Int = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow: Int = readln().toInt()
    return listOf(rows, seatsPerRow)
}

fun calculateTotalIncome(rows: Int, seatsPerRow: Int): Int {
    val income = if (rows * seatsPerRow <= SEATS) {
        rows * seatsPerRow * COST10
    } else {
        ((rows / 2) * seatsPerRow * COST10) + ((rows - (rows / 2)) * seatsPerRow * COST8)
    }
    return income
}

fun printSeating(rows: Int, cols: Int, seats: List<List<Char>>) {
    println(HEADING)
    for (i in 0..rows) {
        if (i == 0) {
            print("  ")
            for (j in 0 until cols) {
                print("${j + 1} ")
            }
        } else {
            print("$i ")
            for (j in 0 until cols) {
                print("${seats[i - 1][j]} ")
            }
        }
        println("")
    }
    println("")
}

fun getSeatLocation(rows: Int, seatsPerRow: Int): MutableList<Int> {
    var rowPosn: Int
    var colPosn: Int
    do {
        println("\nEnter a row number:")
        rowPosn = readln().toInt()

        println("Enter a seat number in that row:")
        colPosn = readln().toInt()

        if (rowPosn > rows || colPosn > seatsPerRow) println("Wrong input!") else break
    } while (true)

    return mutableListOf(rowPosn, colPosn)
}

fun buyTicket(rows: Int, seatsPerRow: Int, seats: MutableList<MutableList<Char>>): Int {
    var cost = 0
    var (rowPosn, colPosn) = getSeatLocation(rows, seatsPerRow)

    if (seats[rowPosn - 1][colPosn - 1] == OCCUPIED) {
        println("\nThat ticket has already been purchased!")
    } else {
        cost = if (rows * seatsPerRow <= SEATS || rowPosn <= rows / 2) COST10 else COST8
        println("\nTicket price: $$cost")
        updateSeating(rowPosn, colPosn, seats)
    }
    return cost
}

fun updateSeating(rowPosn: Int, colPosn: Int, seats: MutableList<MutableList<Char>>) {
    if (rowPosn <= seats.size && colPosn <= seats[0].size) {
        seats[rowPosn - 1][colPosn - 1] = OCCUPIED
    }
//    try {
//        seats[rowPosn - 1][colPosn - 1] = OCCUPIED
//    } catch (e: Exception) {
//        println("Wrong input!")
//    }
}

fun displayStatistics(ticketsBought: Int, totalTickets: Int, currentIncome: Int, totalIncome: Int) {
    val percentage = ticketsBought * 100.0 / totalTickets
    val formatPercentage = "%.2f".format(percentage)
    println("Number of purchased tickets: $ticketsBought")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

fun main() {
    val (rows, seatsPerRow) = getCinemaSize()
    val seats = MutableList(rows) { MutableList(seatsPerRow) { EMPTYSEAT } }
    var userInput: String
    var ticketsBought = 0
    var currentIncome = 0
    val totalIncome = calculateTotalIncome(rows, seatsPerRow)

    do {
        userInput = getMenuInput()
        when (userInput) {
            "1" -> printSeating(rows, seatsPerRow, seats)
            "2" -> {
                var costOfTicket = 0
                do {
                    costOfTicket = buyTicket(rows, seatsPerRow, seats)
                } while (costOfTicket == 0)
                currentIncome += costOfTicket
                ticketsBought++
            }
            "3" -> displayStatistics(ticketsBought, rows * seatsPerRow, currentIncome, totalIncome)
            "0" -> return
            else -> continue
        }
    } while (true)
}