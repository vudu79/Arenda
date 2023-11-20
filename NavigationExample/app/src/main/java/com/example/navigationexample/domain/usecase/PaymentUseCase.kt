package com.example.navigationexample.domain.usecase

class PaymentUseCase {
    var priceOfStay: Int = 0
    var prePayment: Int = 0
    var completedPrePayment: Int = 0
    var completedPayment: Int = 0

    constructor(
        priceOfStay: Int,
        prePayment: Int,
        completedPrePayment: Int,
        completedPayment: Int,
    ) {
        this.priceOfStay = priceOfStay - completedPayment - completedPrePayment
        this.prePayment = prePayment - completedPrePayment
        this.completedPrePayment = completedPrePayment
        this.completedPayment = completedPayment

    }
}