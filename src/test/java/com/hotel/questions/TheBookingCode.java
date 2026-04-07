package com.hotel.questions;

import com.hotel.ui.ConfirmationPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class TheBookingCode {

    private TheBookingCode() {
    }

    public static Question<String> displayed() {
        return Question.about("código de reserva en pantalla").answeredBy(
                actor -> Text.of(ConfirmationPageUi.BOOKING_CODE).answeredBy(actor)
        );
    }
}
