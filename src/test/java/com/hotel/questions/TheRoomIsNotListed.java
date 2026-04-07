package com.hotel.questions;

import com.hotel.ui.SearchPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

public class TheRoomIsNotListed {

    private TheRoomIsNotListed() {
    }

    public static Question<Boolean> withNumber(String roomNumber) {
        return Question.about("ausencia de la habitación " + roomNumber + " en el listado").answeredBy(
                actor -> !Visibility.of(SearchPageUi.roomCardWithNumber(roomNumber)).answeredBy(actor)
        );
    }
}
