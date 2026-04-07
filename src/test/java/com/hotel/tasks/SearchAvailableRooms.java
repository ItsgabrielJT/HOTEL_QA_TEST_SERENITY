package com.hotel.tasks;

import com.hotel.interactions.SetDateValue;
import com.hotel.ui.SearchPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

/**
 * Tarea de negocio: buscar habitaciones disponibles ingresando fechas de entrada y salida.
 *
 * <p>SOLID aplicado:
 * <ul>
 *   <li>SRP – sólo realiza la búsqueda; no selecciona ni confirma nada.</li>
 *   <li>OCP – se extiende via {@link #from(String)} y {@link #to(String)} sin
 *       modificar la clase base.</li>
 * </ul>
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(SearchAvailableRooms.from("2026-04-07").to("2026-04-08"));
 * }</pre>
 */
public class SearchAvailableRooms implements Task {

    private final String checkIn;
    private String checkOut;

    /** Constructor por defecto requerido por Serenity para reportes correctos. */
    public SearchAvailableRooms() {
        this.checkIn = "";
    }

    private SearchAvailableRooms(String checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * Inicia el builder con la fecha de entrada.
     *
     * @param checkInDate fecha ISO-8601, p.ej. "2026-04-07"
     */
    public static SearchAvailableRooms from(String checkInDate) {
        return new SearchAvailableRooms(checkInDate);
    }

    /**
     * Completa el builder con la fecha de salida.
     *
     * @param checkOutDate fecha ISO-8601, p.ej. "2026-04-08"
     */
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

        // Solo esperar tarjetas si se ingresaron fechas válidas
        if (checkIn != null && !checkIn.isEmpty() && checkOut != null && !checkOut.isEmpty()) {
            actor.attemptsTo(
                    WaitUntil.the(SearchPageUi.FIRST_ROOM_CARD, WebElementStateMatchers.isVisible())
                             .forNoMoreThan(10).seconds()
            );
        }
    }
}
