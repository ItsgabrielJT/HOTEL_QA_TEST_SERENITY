package com.hotel.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Interacción personalizada para hacer clic en el botón "Seleccionar" de una tarjeta
 * de habitación en aplicaciones React/Vite.
 *
 * <p>El motivo de esta clase: los selectores XPath con {@code text()} pueden fallar en
 * apps React porque el contenido puede tener whitespace invisible, y los eventos de clic
 * de Selenium pueden no disparar correctamente en componentes controlados por React.
 * La solución más robusta es encontrar la tarjeta por su número usando JavaScript y
 * disparar el evento {@code click()} directamente sobre el botón.
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(
 *     ClickSelectButtonForRoom.withNumber("#101")
 * );
 * }</pre>
 */
public class ClickSelectButtonForRoom implements Interaction {

    private final String roomNumber;

    /** Constructor por defecto requerido por Serenity para reportes correctos. */
    public ClickSelectButtonForRoom() {
        this.roomNumber = "";
    }

    private ClickSelectButtonForRoom(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Crea la interacción para el número de habitación indicado.
     *
     * @param roomNumber número de habitación con prefijo '#', p.ej. "#101"
     */
    public static ClickSelectButtonForRoom withNumber(String roomNumber) {
        return new ClickSelectButtonForRoom(roomNumber);
    }

    @Override
    @Step("{0} hace clic en Seleccionar para la habitación {roomNumber}")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Buscar la primera tarjeta cuyo span de número (class que incluye "_number_")
        // contenga exactamente el roomNumber buscado, con polling hasta 10 segundos.
        // IMPORTANTE: capturar arguments[0] en una variable local ANTES de cualquier
        // callback anidado; de lo contrario cada función tiene su propio objeto arguments.
        String script =
            "var num = arguments[0];" +
            "var maxWait = 10000;" +
            "var interval = 300;" +
            "var waited = 0;" +
            "function tryFind() {" +
            "  var articles = Array.from(document.querySelectorAll('article'));" +
            "  for (var i = 0; i < articles.length; i++) {" +
            "    var numberSpan = articles[i].querySelector('span[class*=\"_number_\"]');" +
            "    if (numberSpan && numberSpan.textContent.trim() === num) {" +
            "      articles[i].scrollIntoView({ behavior: 'instant', block: 'center' });" +
            "      var btn = articles[i].querySelector('button');" +
            "      if (btn) { btn.click(); return true; }" +
            "    }" +
            "  }" +
            "  return false;" +
            "}" +
            "var found = false;" +
            "var deadline = Date.now() + maxWait;" +
            "while (!found && Date.now() < deadline) {" +
            "  found = tryFind();" +
            "  if (!found) {" +
            "    var start = Date.now(); while(Date.now() - start < interval) {}" +
            "  }" +
            "}" +
            "if (!found) { throw new Error('No se encontró la habitación tras ' + maxWait + 'ms: ' + num); }" +
            "return true;";

        js.executeScript(script, roomNumber);
    }
}
