package com.hotel.tasks;

import com.hotel.interactions.ClickSelectButtonForRoom;
import com.hotel.ui.CheckoutPageUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

/**
 * Tarea de negocio: seleccionar una habitación del listado por su número.
 *
 * <p>SRP – sólo localiza la tarjeta correcta y hace clic en "Seleccionar".
 * No conoce ni el formulario de checkout ni la confirmación.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(SelectRoom.withNumber("#101"));
 * }</pre>
 */
public class SelectRoom implements Task {

    private final String roomNumber;

    /** Constructor por defecto requerido por Serenity para reportes correctos. */
    public SelectRoom() {
        this.roomNumber = "";
    }

    private SelectRoom(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Método de fábrica.
     *
     * @param roomNumber número de habitación con el prefijo '#', p.ej. "#101"
     */
    public static SelectRoom withNumber(String roomNumber) {
        return new SelectRoom(roomNumber);
    }

    @Override
    @Step("{0} selecciona la habitación {roomNumber}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Hacer clic en el botón "Seleccionar" via JavaScript para evitar
                // problemas de XPath text() vs React render en CSS modules.
                ClickSelectButtonForRoom.withNumber(roomNumber),

                // Esperar que el temporizador de checkout sea visible (redirección exitosa)
                WaitUntil.the(CheckoutPageUi.TIMER, WebElementStateMatchers.isVisible())
                         .forNoMoreThan(10).seconds()
        );
    }
}
