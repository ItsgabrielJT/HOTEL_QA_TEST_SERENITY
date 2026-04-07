package com.hotel.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Selectores de la página de Checkout.
 *
 * <p>SRP: sólo conoce los locators de la página de checkout.
 * DOM de referencia: chekout.html / invalid.html del workspace.
 */
public class CheckoutPageUi {

    private CheckoutPageUi() {
        // Clase utilitaria – no instanciar
    }

    /** Temporizador de reserva (cuenta regresiva). */
    public static final Target TIMER = Target
            .the("temporizador de reserva")
            .locatedBy("//span[contains(@class,'_countdown_')]");

    /** Input de nombre completo del huésped. */
    public static final Target GUEST_NAME = Target
            .the("campo nombre completo")
            .locatedBy("//input[@placeholder='Juan García']");

    /** Input de email del huésped. */
    public static final Target GUEST_EMAIL = Target
            .the("campo email")
            .locatedBy("//input[@type='email']");

    /** Botón de pago (el texto incluye el monto, p.ej. "Pagar U$85.00 (simulado)"). */
    public static final Target PAY_BUTTON = Target
            .the("botón Pagar")
            .locatedBy("//button[@type='submit' and contains(text(),'Pagar')]");

    /**
     * Mensaje de error de pago: "Pago rechazado por el banco. La habitación fue liberada."
     * Aparece después del primer intento fallido.
     */
    public static final Target PAYMENT_ERROR_MESSAGE = Target
            .the("mensaje de error de pago")
            .locatedBy("//p[contains(text(),'Pago rechazado')]");

    /** Título de la sección de resumen de reserva. */
    public static final Target BOOKING_SUMMARY_TITLE = Target
            .the("título resumen de reserva")
            .locatedBy("//h3[text()='Resumen de reserva']");

    /** Botón "← Volver" de la cabecera. */
    public static final Target BACK_BUTTON = Target
            .the("botón Volver")
            .locatedBy("//button[contains(text(),'Volver')]");
}
