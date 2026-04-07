package com.hotel.stepdefinitions;

import com.hotel.questions.TheBookingCode;
import com.hotel.questions.TheConfirmationTitle;
import com.hotel.questions.ThePaymentError;
import com.hotel.questions.TheRoomListIsDisplayed;
import com.hotel.tasks.CompleteGuestPayment;
import com.hotel.tasks.NavigateToTheHotel;
import com.hotel.tasks.RetryPayment;
import com.hotel.tasks.SearchAvailableRooms;
import com.hotel.tasks.SelectRoom;
import com.hotel.ui.CheckoutPageUi;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;

/**
 * Step Definitions del flujo de reserva de habitación.
 *
 * <p>Buenas prácticas aplicadas:
 * <ul>
 *   <li>Los pasos describen el QUÉ (negocio), no el CÓMO (clics, selectors).</li>
 *   <li>Toda la lógica de interacción está delegada en {@code Task} y {@code Question}.</li>
 *   <li>Se usa {@link OnStage#theActorInTheSpotlight()} para los pasos sin sujeto explícito,
 *       siguiendo el patrón de Stage de Serenity.</li>
 *   <li>El actor se recuerda en memoria ({@code remember}/{@code recall}) para transferir
 *       estado entre pasos sin acoplar los escenarios entre sí.</li>
 * </ul>
 */
public class HotelBookingStepDefinitions {

    // -----------------------------------------------------------------------
    // GIVEN – Precondiciones
    // -----------------------------------------------------------------------

    @Dado("que {word} se encuentra en la página principal del hotel")
    public void actorIsOnHomePage(String actorName) {
        OnStage.theActorCalled(actorName)
               .wasAbleTo(NavigateToTheHotel.homePage());
    }

    // -----------------------------------------------------------------------
    // WHEN – Acciones
    // -----------------------------------------------------------------------

    @Cuando("busca habitaciones del {string} al {string}")
    public void searchesForRooms(String checkIn, String checkOut) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                SearchAvailableRooms.from(checkIn).to(checkOut)
        );
    }

    @Cuando("intenta buscar sin seleccionar fechas")
    public void searchesWithoutDates() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                // Pulsar el botón de búsqueda sin setear fechas;
                // React puede bloquear el submit o mostrar mensaje vacío.
                SearchAvailableRooms.from("").to("")
        );
    }

    @Cuando("selecciona la habitación {string}")
    public void selectsRoom(String roomNumber) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                SelectRoom.withNumber(roomNumber)
        );
    }

    @Cuando("completa sus datos con nombre {string} y email {string}")
    public void completesGuestData(String name, String email) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                CompleteGuestPayment.withName(name).andEmail(email)
        );
        // Guardar datos del huésped en memoria del actor para trazabilidad en reportes
        OnStage.theActorInTheSpotlight().remember("guestName",  name);
        OnStage.theActorInTheSpotlight().remember("guestEmail", email);
    }

    @Cuando("intenta confirmar el pago")
    public void attemptsToConfirmPayment() {
        // El formulario ya fue llenado; aquí sólo se confirma el pago
        // (esperar que el mensaje de error aparezca)
        // La acción de "Pagar" ya se ejecutó dentro de CompleteGuestPayment.
        // Este paso espera que el error sea visible.
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(Visibility.of(CheckoutPageUi.PAYMENT_ERROR_MESSAGE))
                      .isTrue()
        );
    }

    @Cuando("reintenta confirmar el pago")
    public void retriesPayment() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                RetryPayment.afterRejection()
        );
    }

    // -----------------------------------------------------------------------
    // THEN – Verificaciones
    // -----------------------------------------------------------------------

    @Entonces("ve un listado de habitaciones disponibles para seleccionar")
    public void seesRoomList() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheRoomListIsDisplayed.onScreen()).isTrue()
        );
    }

    @Entonces("no se muestran habitaciones en el listado")
    public void seesNoRooms() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheRoomListIsDisplayed.isEmpty()).isTrue()
        );
    }

    @Entonces("es redirigido a la página de checkout con el resumen de reserva")
    public void isRedirectedToCheckout() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(Visibility.of(CheckoutPageUi.BOOKING_SUMMARY_TITLE)).isTrue(),
                Ensure.that(Visibility.of(CheckoutPageUi.TIMER)).isTrue()
        );
    }

    @Entonces("el sistema informa que el pago fue rechazado por el banco")
    public void seesPaymentRejectedError() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(ThePaymentError.message())
                      .containsIgnoringCase("Pago rechazado por el banco")
        );
    }

    @Entonces("su reserva queda confirmada con un código de reserva válido")
    public void seeConfirmedBooking() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheBookingCode.displayed()).isNotEmpty()
        );

        // Guardar el código en memoria del actor para potenciales pasos siguientes
        String code = OnStage.theActorInTheSpotlight().asksFor(TheBookingCode.displayed());
        OnStage.theActorInTheSpotlight().remember("bookingCode", code);
    }

    @Entonces("la confirmación muestra un código de reserva no vacío")
    public void confirmationShowsBookingCode() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheBookingCode.displayed()).isNotEmpty()
        );
    }

    @Entonces("la confirmación muestra el texto {string}")
    public void confirmationShowsText(String expectedText) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheConfirmationTitle.displayed())
                      .containsIgnoringCase(expectedText.replace("¡", "").replace("!", ""))
        );
    }
}
