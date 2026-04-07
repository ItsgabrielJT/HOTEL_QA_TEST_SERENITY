package com.hotel.questions;

import com.hotel.ui.ConfirmationPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class TheConfirmationTitle {

    private TheConfirmationTitle() {
    }

    public static Question<String> displayed() {
        return Question.about("título de la página de confirmación").answeredBy(
                actor -> Text.of(ConfirmationPageUi.CONFIRMATION_TITLE).answeredBy(actor)
        );
    }
}
