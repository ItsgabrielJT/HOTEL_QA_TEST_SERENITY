package com.hotel.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * Selectores de la página de Confirmación de reserva.
 *
 * <p>SRP: sólo conoce los locators de la página de confirmación.
 * DOM de referencia: confirmed.html del workspace.
 */
public class ConfirmationPageUi {

    private ConfirmationPageUi() {
        // Clase utilitaria – no instanciar
    }

    /** Ícono de éxito ("✅"). */
    public static final Target SUCCESS_ICON = Target
            .the("ícono de reserva confirmada")
            .locatedBy("//span[contains(@class,'_icon_')]");

    /** Título "¡Reserva confirmada!". */
    public static final Target CONFIRMATION_TITLE = Target
            .the("título ¡Reserva confirmada!")
            .locatedBy("//h2[contains(text(),'Reserva confirmada')]");

    /** Elemento que muestra el código de reserva alfanumérico (p.ej. "G8DEMUEN"). */
    public static final Target BOOKING_CODE = Target
            .the("código de reserva")
            .locatedBy("//div[contains(@class,'_codeSection_')]//span[contains(@class,'_code_')]");

    /** Botón "Copiar" del código de reserva. */
    public static final Target COPY_CODE_BUTTON = Target
            .the("botón Copiar código")
            .locatedBy("//button[text()='Copiar']");

    /** Botón "Realizar otra reserva". */
    public static final Target HOME_BUTTON = Target
            .the("botón Realizar otra reserva")
            .locatedBy("//button[text()='Realizar otra reserva']");

    /** Total pagado mostrado en el detalle de confirmación. */
    public static final Target TOTAL_PAID = Target
            .the("total pagado en confirmación")
            .locatedBy(
                    "//div[contains(@class,'_details_')]//div[contains(@class,'_total_')]//strong"
            );
}
