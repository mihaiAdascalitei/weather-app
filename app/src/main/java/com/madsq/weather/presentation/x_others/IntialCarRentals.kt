package com.madsq.weather.presentation.x_others

import java.lang.StringBuilder

data class Car(
    val title: String,
    var priceCode: Int
) {
    companion object {
        const val ECONOMY = 0
        const val SUPERCAR = 1
        const val MUSCLE = 2
    }
}

data class Rental(
    val car: Car,
    val daysRented: Int
)

data class Customer(
    val name: String,
    private val rentals: MutableList<Rental> = mutableListOf()
) {
    fun addRental(arg: Rental) {
        rentals.add(arg)
    }

    fun billingStatement(): String {
        var totalAmount = 0.0
        var frequentRenterPoints = 0

        val result = StringBuilder("Rental Record for $name\n").apply {
            rentals.forEach { rental ->

                var thisAmount = 0.0
                when (rental.car.priceCode) {
                    Car.ECONOMY -> {
                        thisAmount += 80
                        if (rental.daysRented > 2) {
                            thisAmount += (rental.daysRented - 2) * 30
                        }
                    }
                    Car.SUPERCAR -> thisAmount += rental.daysRented * 200
                    Car.MUSCLE -> {
                        thisAmount += 200
                        if (rental.daysRented > 3) {
                            thisAmount += (rental.daysRented - 3) * 50
                        }
                        if (rental.daysRented > 1) {
                            frequentRenterPoints++
                        }
                    }
                }

                frequentRenterPoints++
                totalAmount += thisAmount

                append("\t${rental.car.title}\t$thisAmount\n")
            }

            append("Final rental payment owed $totalAmount\n")
            append("You received an additional $frequentRenterPoints frequent customer points")
        }
        return result.toString()
    }
}

fun testRentals() {
    val rental1 = Rental(Car("Mustang", Car.MUSCLE), 5)
    val rental2 = Rental(Car("Lambo", Car.SUPERCAR), 20)
    val customer = Customer("Liviu")
    customer.addRental(rental1)
    customer.addRental(rental2)
    println(customer.billingStatement())
}



