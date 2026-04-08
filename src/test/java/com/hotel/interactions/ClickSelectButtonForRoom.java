package com.hotel.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ClickSelectButtonForRoom implements Interaction {

    private final String roomNumber;

    public ClickSelectButtonForRoom() {
        this.roomNumber = "";
    }

    private ClickSelectButtonForRoom(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public static ClickSelectButtonForRoom withNumber(String roomNumber) {
        return new ClickSelectButtonForRoom(roomNumber);
    }

    @Override
    @Step("{0} hace clic en Seleccionar para la habitación {roomNumber}")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Polling asíncrono con Promise para no bloquear el event loop del browser
        String script =
            "var num = arguments[0];" +
            "var done = arguments[arguments.length - 1];" +
            "var maxWait = 10000;" +
            "var interval = 300;" +
            "function tryFind() {" +
            "  var articles = Array.from(document.querySelectorAll('article'));" +
            "  for (var i = 0; i < articles.length; i++) {" +
            "    var spans = Array.from(articles[i].querySelectorAll('span'));" +
            "    var match = spans.find(function(s) { return s.textContent.trim() === num; });" +
            "    if (match) {" +
            "      articles[i].scrollIntoView({ behavior: 'instant', block: 'center' });" +
            "      var btn = articles[i].querySelector('button');" +
            "      if (btn) { btn.click(); return true; }" +
            "    }" +
            "  }" +
            "  return false;" +
            "}" +
            "var deadline = Date.now() + maxWait;" +
            "function poll() {" +
            "  if (tryFind()) { done(true); return; }" +
            "  if (Date.now() >= deadline) { done(new Error('No se encontró la habitación tras ' + maxWait + 'ms: ' + num)); return; }" +
            "  setTimeout(poll, interval);" +
            "}" +
            "poll();";

        js.executeAsyncScript(script, roomNumber);
    }
}
