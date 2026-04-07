package com.hotel.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CheckoutPageUi {

    private CheckoutPageUi() {
    }

    public static final Target TIMER = Target
            .the("temporizador de reserva")
            .locatedBy("//span[contains(@class,'_countdown_')]");

    public static final Target GUEST_NAME = Target
            .the("campo nombre completo")
            .locatedBy("//input[@placeholder='Juan García']");

    public static final Target GUEST_EMAIL = Target
            .the("campo email")
            .locatedBy("//input[@type='email']");

    public static final Target PAY_BUTTON = Target
            .the("botón Pagar")
            .locatedBy("//button[@type='submit' and contains(text(),'Pagar')]");

    public static final Target PAYMENT_ERROR_MESSAGE = Target
            .the("mensaje de error de pago")
            .locatedBy("//p[contains(text(),'Pago rechazado')]");

    public static final Target BOOKING_SUMMARY_TITLE = Target
            .the("título resumen de reserva")
            .locatedBy("//h3[text()='Resumen de reserva']");

    public static final Target BACK_BUTTON = Target
            .the("botón Volver")
            .locatedBy("//button[contains(text(),'Volver')]");
}
