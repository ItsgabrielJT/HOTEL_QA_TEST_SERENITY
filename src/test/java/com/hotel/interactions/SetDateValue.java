package com.hotel.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Interacción personalizada para establecer el valor de un {@code <input type="date">}
 * en aplicaciones React/Vite.
 *
 * <p>El motivo de esta clase: los inputs de fecha nativos de HTML5 en Chrome no aceptan
 * correctamente {@code sendKeys} con formato ISO cuando el sistema operativo usa locale
 * diferente a en-US. La solución más robusta y multiplataforma es asignar el valor
 * mediante JavaScript y disparar los eventos {@code input} y {@code change} para que
 * React actualice su estado interno.
 *
 * <p>Principios aplicados:
 * <ul>
 *   <li>SRP – esta clase sólo sabe cómo inyectar una fecha en un input.</li>
 *   <li>OCP – se puede reutilizar con cualquier {@link Target}.</li>
 * </ul>
 *
 * <p>Uso:
 * <pre>{@code
 * actor.attemptsTo(
 *     SetDateValue.of("2026-04-07").in(SearchPageUi.CHECK_IN_DATE)
 * );
 * }</pre>
 */
public class SetDateValue implements Interaction {

    private final String isoDate;
    private Target target;

    /** Constructor por defecto requerido por Serenity para registrar el paso en reportes. */
    public SetDateValue() {
        this.isoDate = "";
    }

    private SetDateValue(String isoDate) {
        this.isoDate = isoDate;
    }

    /**
     * Crea la interacción con la fecha en formato ISO-8601 (yyyy-MM-dd).
     *
     * @param isoDate fecha en formato "2026-04-07"
     */
    public static SetDateValue of(String isoDate) {
        return new SetDateValue(isoDate);
    }

    /**
     * Especifica el {@link Target} (input de fecha) donde se establecerá el valor.
     *
     * @param target locator del input de tipo date
     * @return esta misma instancia para encadenamiento fluido
     */
    public SetDateValue in(Target target) {
        this.target = target;
        return this;
    }

    @Override
    @Step("{0} establece la fecha '{isoDate}' en el campo de fecha")
    public <T extends Actor> void performAs(T actor) {
        if (isoDate == null || isoDate.isEmpty()) {
            return; // fecha vacía: no hacer nada, dejar el campo sin valor
        }

        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        WebElementFacade facade = target.resolveFor(actor);
        WebElement element = facade.getWrappedElement();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        /*
         * React 16+ intercepta el setter nativo de HTMLInputElement.prototype.value
         * para rastrear cambios. Si se asigna directamente con element.value = ...
         * el state interno de React no se actualiza. La solución es:
         *   1. Obtener el setter original del prototipo.
         *   2. Llamarlo sobre el elemento.
         *   3. Disparar el evento 'input' (con bubbles:true) para que React lo procese.
         */
        js.executeScript(
                "var nativeSetter = Object.getOwnPropertyDescriptor(" +
                "  window.HTMLInputElement.prototype, 'value').set;" +
                "nativeSetter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input',  { bubbles: true }));" +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element,
                isoDate
        );
    }
}
