package com.hotel.stepdefinitions;

import com.hotel.questions.TheRoomIsNotListed;
import com.hotel.tasks.SearchAvailableRooms;
import com.hotel.tasks.SelectRoom;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class RoomBlockingStepDefinitions {

    @Cuando("{word} busca habitaciones del {string} al {string}")
    public void actorSearchesForRooms(String actorName, String checkIn, String checkOut) {
        OnStage.theActorCalled(actorName).attemptsTo(
                SearchAvailableRooms.from(checkIn).to(checkOut)
        );
    }

    @Cuando("{word} selecciona la habitación {string}")
    public void actorSelectsRoom(String actorName, String roomNumber) {
        OnStage.theActorCalled(actorName).attemptsTo(
                SelectRoom.withNumber(roomNumber)
        );
    }

    @Entonces("{word} no ve la habitación {string} en el listado de disponibles")
    public void actorDoesNotSeeRoom(String actorName, String roomNumber) {
        OnStage.theActorCalled(actorName).attemptsTo(
                Ensure.that(TheRoomIsNotListed.withNumber(roomNumber)).isTrue()
        );
    }
}
