package com.hotel.tasks;

import com.hotel.ui.CheckoutPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class CompleteGuestPayment implements Task {

    private final String guestName;
    private String guestEmail;

    public CompleteGuestPayment() {
        this.guestName = "";
    }

    private CompleteGuestPayment(String guestName) {
        this.guestName = guestName;
    }

    public static CompleteGuestPayment withName(String name) {
        return new CompleteGuestPayment(name);
    }

    public CompleteGuestPayment andEmail(String email) {
        this.guestEmail = email;
        return this;
    }

    @Override
    @Step("{0} completa sus datos con nombre '{guestName}' y email '{guestEmail}' y confirma el pago")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CheckoutPageUi.GUEST_NAME, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(10).seconds(),
                Enter.theValue(guestName).into(CheckoutPageUi.GUEST_NAME),
                Enter.theValue(guestEmail).into(CheckoutPageUi.GUEST_EMAIL),
                Click.on(CheckoutPageUi.PAY_BUTTON)
        );
    }
}
