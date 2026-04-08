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
        return Question.about("ausencia del listado de habitaciones").answeredBy(
                actor -> !Visibility.of(SearchPageUi.FIRST_ROOM_CARD).answeredBy(actor)
        );
    }
}
