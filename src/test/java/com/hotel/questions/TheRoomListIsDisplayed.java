package com.hotel.questions;

import com.hotel.ui.SearchPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

/**
 * Pregunta: ¿se muestra actualmente al menos una habitación disponible en la pantalla?
 *
 * <p>SRP – responde únicamente sobre la visibilidad del listado de habitaciones.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(
 *     Ensure.that(TheRoomListIsDisplayed.onScreen()).isTrue()
 * );
 * }</pre>
 */
public class TheRoomListIsDisplayed {

    private TheRoomListIsDisplayed() {
    }

    /**
     * Retorna {@code true} si la primera tarjeta de habitación es visible en pantalla.
     */
    public static Question<Boolean> onScreen() {
        return Question.about("listado de habitaciones disponibles").answeredBy(
                actor -> Visibility.of(SearchPageUi.FIRST_ROOM_CARD).answeredBy(actor)
        );
    }

    /**
     * Retorna {@code true} si el mensaje de "sin resultados" es visible en pantalla.
     */
    public static Question<Boolean> isEmpty() {
        return Question.about("mensaje de habitaciones no disponibles").answeredBy(
                actor -> Visibility.of(SearchPageUi.EMPTY_RESULTS_MESSAGE).answeredBy(actor)
        );
    }
}
