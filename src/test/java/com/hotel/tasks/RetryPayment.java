package com.hotel.tasks;

import com.hotel.ui.CheckoutPageUi;
import com.hotel.ui.ConfirmationPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

/**
 * Tarea de negocio: reintentar el pago luego de un rechazo bancario.
 *
 * <p>SRP – sólo vuelve a pulsar "Pagar" y espera la redirección a confirmación.
 * La validación del resultado es responsabilidad de las {@code Question}.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(RetryPayment.afterRejection());
 * }</pre>
 */
public class RetryPayment implements Task {

    /** Constructor por defecto requerido por Serenity para reportes correctos. */
    public RetryPayment() {
    }

    /** Método de fábrica para mayor expresividad en los tests. */
    public static RetryPayment afterRejection() {
        return new RetryPayment();
    }

    @Override
    @Step("{0} reintenta el pago luego del rechazo bancario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // El botón de pago sigue visible tras el error
                WaitUntil.the(CheckoutPageUi.PAY_BUTTON, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(10).seconds(),

                Click.on(CheckoutPageUi.PAY_BUTTON),

                // Esperar redirección a la página de confirmación
                WaitUntil.the(ConfirmationPageUi.BOOKING_CODE, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(15).seconds()
        );
    }
}
