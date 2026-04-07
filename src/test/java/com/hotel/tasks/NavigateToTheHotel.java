package com.hotel.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateToTheHotel implements Task {

    public static final String BASE_URL = "http://localhost:5173";

    public NavigateToTheHotel() {
    }

    public static NavigateToTheHotel homePage() {
        return new NavigateToTheHotel();
    }

    @Override
    @Step("{0} navega a la página principal del Hotel Booking")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(BASE_URL));
    }
}
