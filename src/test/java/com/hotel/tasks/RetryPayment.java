package com.hotel.tasks;

import com.hotel.ui.CheckoutPageUi;
import com.hotel.ui.ConfirmationPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class RetryPayment implements Task {

    public RetryPayment() {
    }

    public static RetryPayment afterRejection() {
        return new RetryPayment();
    }

    @Override
    @Step("{0} reintenta el pago luego del rechazo bancario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CheckoutPageUi.PAY_BUTTON, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(10).seconds(),
                Click.on(CheckoutPageUi.PAY_BUTTON),
                WaitUntil.the(ConfirmationPageUi.BOOKING_CODE, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(15).seconds()
        );
    }
}
