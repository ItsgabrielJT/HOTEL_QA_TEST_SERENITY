package com.hotel.tasks;

import com.hotel.ui.CheckoutPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

/**
 * Tarea de negocio: completar los datos del huésped y pulsar el botón de pago.
 *
 * <p>SRP – esta tarea sólo llena el formulario y hace clic en "Pagar".
 * No verifica el resultado del pago; eso es responsabilidad de una {@code Question}.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(
 *     CompleteGuestPayment.withName("Juan García").andEmail("juan@email.com")
 * );
 * }</pre>
 */
public class CompleteGuestPayment implements Task {

    private final String guestName;
    private String guestEmail;

    /** Constructor por defecto requerido por Serenity para reportes correctos. */
    public CompleteGuestPayment() {
        this.guestName = "";
    }

    private CompleteGuestPayment(String guestName) {
        this.guestName = guestName;
    }

    /**
     * Inicia el builder con el nombre del huésped.
     *
     * @param name nombre completo del huésped
     */
    public static CompleteGuestPayment withName(String name) {
        return new CompleteGuestPayment(name);
    }

    /**
     * Completa el builder con el email del huésped.
     *
     * @param email dirección de correo electrónico
     */
    public CompleteGuestPayment andEmail(String email) {
        this.guestEmail = email;
        return this;
    }

    @Override
    @Step("{0} completa sus datos con nombre '{guestName}' y email '{guestEmail}' y confirma el pago")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Esperar que el formulario esté disponible
                WaitUntil.the(CheckoutPageUi.GUEST_NAME, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(10).seconds(),

                // Limpiar e ingresar nombre completo
                Enter.theValue(guestName).into(CheckoutPageUi.GUEST_NAME),

                // Limpiar e ingresar email
                Enter.theValue(guestEmail).into(CheckoutPageUi.GUEST_EMAIL),

                // Pulsar el botón de pago
                Click.on(CheckoutPageUi.PAY_BUTTON)
        );
    }
}
