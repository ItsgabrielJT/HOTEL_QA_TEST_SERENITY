package com.hotel.ui;

import net.serenitybdd.screenplay.targets.Target;

public class SearchPageUi {

    private SearchPageUi() {
    }

    public static final Target CHECK_IN_DATE = Target
            .the("input fecha de entrada")
            .locatedBy("(//input[@type='date'])[1]");

    public static final Target CHECK_OUT_DATE = Target
            .the("input fecha de salida")
            .locatedBy("(//input[@type='date'])[2]");

    public static final Target SEARCH_BUTTON = Target
            .the("botón Buscar")
            .locatedBy("//button[@type='submit' and text()='Buscar']");

    public static final Target FIRST_ROOM_CARD = Target
            .the("primera tarjeta de habitación disponible")
            .locatedBy("(//article[.//button])[1]");

    public static final Target EMPTY_RESULTS_MESSAGE = Target
            .the("mensaje sin resultados")
            .locatedBy("//p[contains(text(), 'No hay habitaciones disponibles')]");

    public static Target selectButtonForRoom(String roomNumber) {
        return Target
                .the("botón Seleccionar de la habitación " + roomNumber)
                .locatedBy(
                        "(//article[.//span[text()='" + roomNumber + "']]//button[text()='Seleccionar'])[1]"
                );
    }

    public static Target roomCardWithNumber(String roomNumber) {
        return Target
                .the("tarjeta de la habitación " + roomNumber)
                .locatedBy("//article[.//span[text()='" + roomNumber + "']]");
    }
}
