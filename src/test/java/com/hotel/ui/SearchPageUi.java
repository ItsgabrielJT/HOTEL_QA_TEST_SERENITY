package com.hotel.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Selectores de la página de búsqueda (Home / Search Page).
 *
 * <p>Principio de Responsabilidad Única (SRP): esta clase sólo conoce los
 * locators de la página de búsqueda. Cualquier cambio en el DOM se centraliza aquí.
 *
 * <p>DOM de referencia: dom.html del workspace.
 */
public class SearchPageUi {

    private SearchPageUi() {
        // Clase utilitaria – no instanciar
    }

    /** Input de fecha de entrada (primer date input del formulario). */
    public static final Target CHECK_IN_DATE = Target
            .the("input fecha de entrada")
            .locatedBy("(//input[@type='date'])[1]");

    /** Input de fecha de salida (segundo date input del formulario). */
    public static final Target CHECK_OUT_DATE = Target
            .the("input fecha de salida")
            .locatedBy("(//input[@type='date'])[2]");

    /** Botón "Buscar" del formulario de búsqueda. */
    public static final Target SEARCH_BUTTON = Target
            .the("botón Buscar")
            .locatedBy("//button[@type='submit' and text()='Buscar']");

    /**
     * Primera tarjeta de habitación visible en el listado.
     * Usa XPath sin text() para evitar fallos por whitespace o idioma.
     */
    public static final Target FIRST_ROOM_CARD = Target
            .the("primera tarjeta de habitación disponible")
            .locatedBy("(//article[.//button])[1]");

    /** Mensaje mostrado cuando no hay habitaciones disponibles. */
    public static final Target EMPTY_RESULTS_MESSAGE = Target
            .the("mensaje sin resultados")
            .locatedBy("//p[contains(text(), 'No hay habitaciones disponibles')]");

    // -----------------------------------------------------------------------
    // Targets dinámicos
    // -----------------------------------------------------------------------

    /**
     * Botón "Seleccionar" de la tarjeta que corresponde al número de habitación dado.
     * Si hay múltiples tarjetas con el mismo número, selecciona la primera.
     *
     * @param roomNumber número de habitación con el prefijo '#', p.ej. "#101"
     */
    public static Target selectButtonForRoom(String roomNumber) {
        return Target
                .the("botón Seleccionar de la habitación " + roomNumber)
                .locatedBy(
                        "(//article[.//span[text()='" + roomNumber + "']]//button[text()='Seleccionar'])[1]"
                );
    }
}
