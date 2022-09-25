package machine

const val WATER: Int = 400
const val MILK: Int = 540
const val BEANS: Int = 120
const val CUPS: Int = 9
const val MONEY: Int = 550

enum class State {
    WAITING, BUY, FILL, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS, TAKE, REMAINING, EXIT
}

class CoffeeMachine {
    var water: Int = WATER
    var milk: Int = MILK
    var beans: Int = BEANS
    var cups: Int = CUPS
    var money: Int = MONEY
    var input: String = ""
    var state: State = State.WAITING

    var temp_water: Int = 0
    var temp_milk: Int = 0
    var temp_beans: Int = 0
    var temp_cups: Int = 0
    var temp_money: Int = 0

    fun displayPrompt() {
        when (state) {
            State.WAITING -> print("\nWrite action (buy, fill, take): ")
            State.BUY -> print("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ")
            State.FILL_WATER -> print("Write how many ml of water do you want to add: ")
            State.FILL_MILK -> print("Write how many ml of milk do you want to add: ")
            State.FILL_BEANS -> print("Write how many grams of coffee beans do you want to add: ")
            State.FILL_CUPS -> print("Write how many disposable cups of coffee do you want to add: ")
        }
    }


    fun setState() {
        if (state == State.WAITING && input == "buy") {
            state = State.BUY
        } else if (state == State.WAITING && input == "fill") {
            state = State.FILL
        } else if (state == State.FILL) {
            state = State.FILL_WATER
        } else if (state == State.FILL_WATER) {
            state = State.FILL_MILK
        } else if (state == State.FILL_MILK) {
            state = State.FILL_BEANS
        } else if (state == State.FILL_BEANS) {
            state = State.FILL_CUPS
        } else if (state == State.FILL_CUPS) {
            state = State.FILL_BEANS
        } else if (state == State.WAITING && input == "take") {
            state = State.TAKE
        } else if (state == State.WAITING && input == "remaining") {
            state = State.REMAINING
        } else if (input == "back") {
            state = State.WAITING
        } else if (state == State.WAITING && input == "exit") {
            state = State.EXIT
        }
    }


    fun processState() {
        when(state) {
            State.BUY -> {
                if (input in "1".."3") {
                    when(input) {
                        "1" -> {
                            temp_water = 250
                            temp_milk = 0
                            temp_beans = 16
                            temp_cups = 1
                            temp_money = 4
                        }
                        "2" -> {
                            temp_water = 350
                            temp_milk = 75
                            temp_beans = 20
                            temp_cups = 1
                            temp_money = 7
                        }
                        "3" -> {
                            temp_water = 200
                            temp_milk = 100
                            temp_beans = 12
                            temp_cups = 1
                            temp_money = 6
                        }
                    }
                    buy(temp_water, temp_milk, temp_beans, temp_cups, temp_money)
                    state = State.WAITING
                }
            }
            State.FILL_WATER -> {
                temp_water = input.toInt()
            }
            State.FILL_MILK -> {
                temp_milk = input.toInt()
            }
            State.FILL_BEANS -> {
                temp_beans = input.toInt()
            }
            State.FILL_CUPS -> {
                temp_cups = input.toInt()
                fill(temp_water, temp_milk, temp_beans, temp_cups) 
                state = State.WAITING
            }
            State.TAKE -> {
                take()
                state = State.WAITING
            }
            State.REMAINING -> {
                printContents() // water, milk, beans, cups, money)
                state = State.WAITING
            }
        }
    }
    
    fun printContents() {
        println("\nThe coffee machine has:\n$water ml of water\n$milk ml of milk\n$beans g of coffee beans\n$cups disposable cups\n$$money of money")
    }
    
    fun buy(water1: Int = 0, milk1: Int = 0, beans1: Int = 0, cups1: Int = 0, money1: Int = 0) {
        if (water < water1) println ("Sorry, not enough water!")
        else if (milk < milk1) println ("Sorry, not enough milk!") 
        else if (beans < beans1) println ("Sorry, not enough beans!")
        else if (cups < cups1) println ("Sorry, not enough cups!")
        else {
            println("I have enough resources, making you a coffee!")
            water -= water1
            milk -= milk1
            beans -= beans1
            cups -= cups1
            money += money1        
        }
    }

    fun fill(water1: Int = 0, milk1: Int = 0, beans1: Int = 0, cups1: Int = 0) {
        water += water1
        milk += milk1
        beans += beans1
        cups += cups1
    }

    fun take() {
        println("\nI gave you $$money")
        money = 0
    }
}

fun getInput(): String {
    return readLine()!!
}


fun main() {
    var cm: CoffeeMachine = CoffeeMachine()
    do {
        cm.displayPrompt()
        cm.input = getInput()
        cm.setState()
        cm.processState()
    } while (cm.state != State.EXIT)
}