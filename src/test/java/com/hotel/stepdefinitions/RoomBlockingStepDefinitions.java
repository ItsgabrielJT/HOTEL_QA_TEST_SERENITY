package com.hotel.stepdefinitions;

import com.hotel.questions.TheRoomIsNotListed;
import com.hotel.tasks.SearchAvailableRooms;
import com.hotel.tasks.SelectRoom;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

/**
 * Step Definitions del flujo de bloqueo de habitación durante reserva concurrente.
 *
 * <p>Buenas prácticas aplicadas:
 * <ul>
 *   <li>Cada paso recibe el nombre del actor explícitamente para permitir
 *       múltiples actores en el mismo escenario sin depender del spotlight.</li>
 *   <li>Se reutilizan las mismas {@code Task} y {@code Question} del flujo principal;
 *       no se duplica lógica de interacción.</li>
 * </ul>
 */
public class RoomBlockingStepDefinitions {

    /**
     * Busca habitaciones disponibles con el actor nombrado.
     * Variante multi-actor de {@code HotelBookingStepDefinitions#searchesForRooms}.
     */
    @Cuando("{word} busca habitaciones del {string} al {string}")
    public void actorSearchesForRooms(String actorName, String checkIn, String checkOut) {
        OnStage.theActorCalled(actorName).attemptsTo(
                SearchAvailableRooms.from(checkIn).to(checkOut)
        );
    }

    /**
     * Selecciona una habitación del listado con el actor nombrado.
     * Variante multi-actor de {@code HotelBookingStepDefinitions#selectsRoom}.
     */
    @Cuando("{word} selecciona la habitación {string}")
    public void actorSelectsRoom(String actorName, String roomNumber) {
        OnStage.theActorCalled(actorName).attemptsTo(
                SelectRoom.withNumber(roomNumber)
        );
    }

    /**
     * Verifica que la habitación indicada no aparece en el listado de resultados
     * de búsqueda del actor nombrado.
     */
    @Entonces("{word} no ve la habitación {string} en el listado de disponibles")
    public void actorDoesNotSeeRoom(String actorName, String roomNumber) {
        OnStage.theActorCalled(actorName).attemptsTo(
                Ensure.that(TheRoomIsNotListed.withNumber(roomNumber)).isTrue()
        );
    }
}
