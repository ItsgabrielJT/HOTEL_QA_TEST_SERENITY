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

public class SetDateValue implements Interaction {

    private final String isoDate;
    private Target target;

    public SetDateValue() {
        this.isoDate = "";
    }

    private SetDateValue(String isoDate) {
        this.isoDate = isoDate;
    }

    public static SetDateValue of(String isoDate) {
        return new SetDateValue(isoDate);
    }

    public SetDateValue in(Target target) {
        this.target = target;
        return this;
    }

    @Override
    @Step("{0} establece la fecha '{isoDate}' en el campo de fecha")
    public <T extends Actor> void performAs(T actor) {
        if (isoDate == null || isoDate.isEmpty()) {
            return;
        }

        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        WebElementFacade facade = target.resolveFor(actor);
        WebElement element = facade.getWrappedElement();

        JavascriptExecutor js = (JavascriptExecutor) driver;
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
