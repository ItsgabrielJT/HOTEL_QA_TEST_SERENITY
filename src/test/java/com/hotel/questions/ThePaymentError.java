package com.hotel.questions;

import com.hotel.ui.CheckoutPageUi;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Pregunta: ¿cuál es el mensaje de error de pago visible en la pantalla?
 *
 * <p>SRP – sólo extrae el texto del elemento de error.
 * ISP – expone únicamente el contrato de lectura, no detalles de la UI.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(
 *     Ensure.that(ThePaymentError.message())
 *           .containsIgnoringCase("Pago rechazado por el banco")
 * );
 * }</pre>
 */
public class ThePaymentError {

    private ThePaymentError() {
    }

    /**
     * Método de fábrica con nombre descriptivo para mejorar la legibilidad de los
     * reportes de Serenity.
     */
    public static Question<String> message() {
        return Question.about("mensaje de error de pago").answeredBy(
                actor -> Text.of(CheckoutPageUi.PAYMENT_ERROR_MESSAGE).answeredBy(actor)
        );
    }
}
