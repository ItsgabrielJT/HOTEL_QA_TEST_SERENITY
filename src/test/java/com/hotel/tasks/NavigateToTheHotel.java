package com.hotel.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

/**
 * Tarea de alto nivel: navegar a la página principal del Hotel Booking.
 *
 * <p>Encapsula la URL de la aplicación en un único lugar (SRP + OCP).
 * Si la URL cambia, solo se modifica aquí.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.wasAbleTo(NavigateToTheHotel.homePage());
 * }</pre>
 */
public class NavigateToTheHotel implements Task {

    /** URL base de la aplicación. Configurable aquí o via serenity.conf base.url. */
    public static final String BASE_URL = "http://localhost:5173";

    /** Constructor por defecto requerido por Serenity para reportes correctos. */
    public NavigateToTheHotel() {
    }

    /** Método de fábrica para mayor legibilidad en los tests. */
    public static NavigateToTheHotel homePage() {
        return new NavigateToTheHotel();
    }

    @Override
    @Step("{0} navega a la página principal del Hotel Booking")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(BASE_URL));
    }
}
