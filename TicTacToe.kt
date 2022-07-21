package tictactoe

const val XWINS = "X wins"
const val OWINS = "O wins"
const val DRAW = "Draw"
const val NOTFINISHED = "Game not finished"
const val IMPOSSIBLE = "Impossible"
const val TOTALMOVES = 9

val players = mutableListOf("X", "O")
var currPlayer = 0

// reads new coordinates, validates and adds them to the grid
fun addCoorInput(grid: String, player: String): String {
    var coor = "0 0".split(" ").map { it -> it.toInt() }
    var isValid: Boolean  // is the input valid?
    var posn = 0
    var input = ""

    do {
        input = readln()
        isValid = true

        for (i in 0 until input.length) {
            if (!(input[i] in '0'..'9' || input[i] == ' ')) {
                isValid = false
                println("You should enter numbers!")
            }
        }

        if (isValid) {
            for (i in 0 until input.length) {
                if (!(input[i] in '1'..'3' || input[i] == ' ')) {
                    isValid = false
                    println("Coordinates should be from 1 to 3!")
                }
            }
        }

        if (isValid) {
            coor = input.split(" ").map { it -> it.toInt() }
            posn = ((((coor[0] - 1) * 3) + (coor[1] - 1)))
            println("coor[0] = ${coor[0]}, coor[1] = ${coor[1]}, posn = $posn")
            if (grid[posn] != '_') {
                isValid = false
                println("This cell is occupied! Choose another one!")
            }
        }
    } while  (!isValid)
//    println(posn)
    return grid.replaceRange(posn, posn + 1, player)
}

// prints the current state of the tic-tac-toe grid
fun printGrid(grid: String) {
    val separator = "---------"
    println(separator)
    for (i in 0..2) {
        print("| ")
        for (j in 0..2) {
            print("${grid[i * 3 + j]} ")
        }
        println("|")
    }
    println(separator)    
}

// checks if all characters in a line across are the same
fun checkRowWin(grid: String, ch: Char): Boolean {
    for (i in 0..2) {
        if (grid[i * 3] == ch && grid[i * 3] == grid[i * 3 + 1] && grid[i * 3 + 1] == grid[i * 3 + 2]) return true
    }
    return false
}

// checks if all characters in a line down are the same
fun checkColWin(grid: String, ch: Char): Boolean {
    for (i in 0..2) {
        if (grid[i] == ch && grid[i] == grid[i + 3] && grid[i + 3] == grid[i + 6]) return true
    }
    return false
}

// checks if all characters in a diagonal are the same
fun checkDiagWin(grid: String, ch: Char): Boolean {
    return if (grid[0] == ch && grid[0] == grid[4] && grid[4] == grid[8]) true
    else grid[2] == ch && grid[2] == grid[4] && grid[4] == grid[6]
}

// analyzes the grid to see if there are any wins or a draw
fun analyzeGrid(grid: String): String {
    val numO = grid.count { it == 'O' }
    val numX = grid.count { it == 'X' }
    var result = ""

    if (numO - numX >= 2 || numX - numO >= 2) println(IMPOSSIBLE)
    else if (checkRowWin(grid, 'X') && checkRowWin(grid, 'O')) result = IMPOSSIBLE
    else if (checkColWin(grid, 'X') && checkColWin(grid, 'O')) result = IMPOSSIBLE
    else if (checkRowWin(grid, 'X')) result = XWINS
    else if (checkRowWin(grid, 'O')) result = OWINS
    else if (checkColWin(grid, 'X')) result = XWINS
    else if (checkColWin(grid, 'O')) result = OWINS
    else if (checkDiagWin(grid, 'X')) result = XWINS
    else if (checkDiagWin(grid, 'O')) result = OWINS
    else if (numO + numX == TOTALMOVES) result = DRAW
//    else if (numO + numX < TOTALMOVES) result = NOTFINISHED
    else ""
//    println(result)
    return result
}

fun main() {
    var grid = "_________"
    var result = ""

    do {
        printGrid(grid)
        grid = addCoorInput(grid, players[currPlayer])
        result = analyzeGrid(grid)
        currPlayer = 1 - currPlayer
    } while (result == "")
    printGrid(grid)
    println(result)    
}