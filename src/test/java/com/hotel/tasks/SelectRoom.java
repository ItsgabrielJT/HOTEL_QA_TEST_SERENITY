package com.hotel.tasks;

import com.hotel.interactions.ClickSelectButtonForRoom;
import com.hotel.ui.CheckoutPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class SelectRoom implements Task {

    private final String roomNumber;

    public SelectRoom() {
        this.roomNumber = "";
    }

    private SelectRoom(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public static SelectRoom withNumber(String roomNumber) {
        return new SelectRoom(roomNumber);
    }

    @Override
    @Step("{0} selecciona la habitación {roomNumber}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickSelectButtonForRoom.withNumber(roomNumber),
                WaitUntil.the(CheckoutPageUi.TIMER, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(10).seconds()
        );
    }
}

