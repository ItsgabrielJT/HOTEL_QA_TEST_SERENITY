package com.hotel.questions;

import com.hotel.ui.SearchPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

public class TheRoomListIsDisplayed {

    private TheRoomListIsDisplayed() {
    }

    public static Question<Boolean> onScreen() {
        return Question.about("listado de habitaciones disponibles").answeredBy(
                actor -> Visibility.of(SearchPageUi.FIRST_ROOM_CARD).answeredBy(actor)
        );
    }

    public static Question<Boolean> isEmpty() {
        return Question.about("mensaje de habitaciones no disponibles").answeredBy(
                actor -> Visibility.of(SearchPageUi.EMPTY_RESULTS_MESSAGE).answeredBy(actor)
        );
    }
}
