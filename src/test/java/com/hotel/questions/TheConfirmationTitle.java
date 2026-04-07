package com.hotel.questions;

import com.hotel.ui.ConfirmationPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Pregunta: ¿cuál es el título visible en la página de confirmación?
 *
 * <p>Permite validar que la redirección a la página de confirmación fue exitosa
 * comprobando el título "¡Reserva confirmada!".
 */
public class TheConfirmationTitle {

    private TheConfirmationTitle() {
    }

    /** Retorna el texto del título de confirmación. */
    public static Question<String> displayed() {
        return Question.about("título de la página de confirmación").answeredBy(
                actor -> Text.of(ConfirmationPageUi.CONFIRMATION_TITLE).answeredBy(actor)
        );
    }
}
