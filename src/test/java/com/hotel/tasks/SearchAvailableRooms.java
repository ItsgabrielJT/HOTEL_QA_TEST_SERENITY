package com.hotel.tasks;

import com.hotel.interactions.SetDateValue;
import com.hotel.ui.SearchPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class SearchAvailableRooms implements Task {

    private final String checkIn;
    private String checkOut;

    public SearchAvailableRooms() {
        this.checkIn = "";
    }

    private SearchAvailableRooms(String checkIn) {
        this.checkIn = checkIn;
    }

    public static SearchAvailableRooms from(String checkInDate) {
        return new SearchAvailableRooms(checkInDate);
    }

    public SearchAvailableRooms to(String checkOutDate) {
        this.checkOut = checkOutDate;
        return this;
    }

    @Override
    @Step("{0} busca habitaciones disponibles del {checkIn} al {checkOut}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                SetDateValue.of(checkIn).in(SearchPageUi.CHECK_IN_DATE),
                SetDateValue.of(checkOut).in(SearchPageUi.CHECK_OUT_DATE),
                Click.on(SearchPageUi.SEARCH_BUTTON)
        );

        if (checkIn != null && !checkIn.isEmpty() && checkOut != null && !checkOut.isEmpty()) {
            actor.attemptsTo(
                    WaitUntil.the(SearchPageUi.FIRST_ROOM_CARD, WebElementStateMatchers.isVisible())
                             .forNoMoreThan(10).seconds()
            );
        }
    }
}
