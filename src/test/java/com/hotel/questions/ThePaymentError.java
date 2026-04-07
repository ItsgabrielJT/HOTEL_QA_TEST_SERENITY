package com.hotel.questions;

import com.hotel.ui.CheckoutPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class ThePaymentError {

    private ThePaymentError() {
    }

    public static Question<String> message() {
        return Question.about("mensaje de error de pago").answeredBy(
                actor -> Text.of(CheckoutPageUi.PAYMENT_ERROR_MESSAGE).answeredBy(actor)
        );
    }
}
