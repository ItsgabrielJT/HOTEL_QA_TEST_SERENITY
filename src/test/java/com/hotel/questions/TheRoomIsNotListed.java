package com.hotel.questions;

import com.hotel.ui.SearchPageUi;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

/**
 * Pregunta: ¿está ausente una habitación específica del listado de resultados?
 *
 * <p>SRP – responde únicamente sobre la ausencia de una tarjeta de habitación
 * identificada por su número (p.ej. "#202").
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(
 *     Ensure.that(TheRoomIsNotListed.withNumber("#202")).isTrue()
 * );
 * }</pre>
 */
public class TheRoomIsNotListed {

    private TheRoomIsNotListed() {
    }

    /**
     * Retorna {@code true} si la habitación con el número indicado NO está visible
     * en el listado de resultados de búsqueda.
     *
     * @param roomNumber número de habitación con el prefijo '#', p.ej. "#202"
     */
    public static Question<Boolean> withNumber(String roomNumber) {
        return Question.about("ausencia de la habitación " + roomNumber + " en el listado").answeredBy(
                actor -> !Visibility.of(SearchPageUi.roomCardWithNumber(roomNumber)).answeredBy(actor)
        );
    }
}
