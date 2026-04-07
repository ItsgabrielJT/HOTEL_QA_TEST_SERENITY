package com.hotel.questions;

import com.hotel.ui.ConfirmationPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Pregunta: ¿cuál es el código de reserva generado en la página de confirmación?
 *
 * <p>SRP – sólo extrae el texto del código de reserva.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(
 *     Ensure.that(TheBookingCode.displayed()).isNotEmpty()
 * );
 *
 * // También se puede recordar para uso futuro:
 * String code = actor.asksFor(TheBookingCode.displayed());
 * actor.remember("bookingCode", code);
 * }</pre>
 */
public class TheBookingCode {

    private TheBookingCode() {
    }

    /**
     * Retorna el código alfanumérico de reserva visible en pantalla (p.ej. "G8DEMUEN").
     */
    public static Question<String> displayed() {
        return Question.about("código de reserva en pantalla").answeredBy(
                actor -> Text.of(ConfirmationPageUi.BOOKING_CODE).answeredBy(actor)
        );
    }
}
