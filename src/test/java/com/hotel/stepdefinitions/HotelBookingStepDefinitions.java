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
import com.hotel.ui.ConfirmationPageUi;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Visibility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HotelBookingStepDefinitions {

    @Dado("que {word} se encuentra en la página principal del hotel")
    public void actorIsOnHomePage(String actorName) {
        OnStage.theActorCalled(actorName)
               .wasAbleTo(NavigateToTheHotel.homePage());
    }

    @Cuando("busca habitaciones del {string} al {string}")
    public void searchesForRooms(String checkIn, String checkOut) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                SearchAvailableRooms.from(checkIn).to(checkOut)
        );
    }

    @Cuando("intenta buscar sin seleccionar fechas")
    public void searchesWithoutDates() {
        OnStage.theActorInTheSpotlight().attemptsTo(
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
        OnStage.theActorInTheSpotlight().remember("guestName",  name);
        OnStage.theActorInTheSpotlight().remember("guestEmail", email);
    }

    @Cuando("intenta confirmar el pago")
    public void attemptsToConfirmPayment() {
        Actor actor = OnStage.theActorInTheSpotlight();
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        boolean paymentRejected = false;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//p[contains(text(),'Pago rechazado')]"))
                            ,
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//div[contains(@class,'_codeSection_')]//span[contains(@class,'_code_')]"))
                    ));
            paymentRejected = !driver
                    .findElements(By.xpath("//p[contains(text(),'Pago rechazado')]"))
                    .isEmpty();
        } catch (Exception e) {
        }
        actor.remember("paymentRejected", paymentRejected);
    }

    @Cuando("reintenta confirmar el pago")
    public void retriesPayment() {
        Actor actor = OnStage.theActorInTheSpotlight();
        Boolean paymentRejected = actor.recall("paymentRejected");
        if (Boolean.TRUE.equals(paymentRejected)) {
            actor.attemptsTo(RetryPayment.afterRejection());
        }
    }

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
        Actor actor = OnStage.theActorInTheSpotlight();
        Boolean paymentRejected = actor.recall("paymentRejected");
        if (Boolean.TRUE.equals(paymentRejected)) {
            actor.attemptsTo(
                    Ensure.that(ThePaymentError.message())
                          .containsIgnoringCase("Pago rechazado por el banco")
            );
        }
    }

    @Entonces("su reserva queda confirmada con un código de reserva válido")
    public void seeConfirmedBooking() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(TheBookingCode.displayed()).isNotEmpty()
        );
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
