package com.hotel.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Hooks de Cucumber para gestión del ciclo de vida del Escenario (Stage) y del Elenco (Cast).
 *
 * <p>Buenas prácticas aplicadas:
 * <ul>
 *   <li>Se usa {@link OnlineCast} en lugar de instanciar WebDriver manualmente,
 *       dejando que Serenity gestione el ciclo de vida del navegador (SRP).</li>
 *   <li>Se usa {@link OnStage#drawTheCurtain()} en {@code @After} para garantizar
 *       que el navegador se cierre y el escenario quede limpio después de cada test
 *       (escenarios independientes, sin acoplamiento).</li>
 * </ul>
 */
public class Hooks {

    /**
     * Se ejecuta antes de cada escenario.
     * Inicializa el Stage con un elenco online (cada actor tendrá su propio navegador).
     */
    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    /**
     * Se ejecuta después de cada escenario.
     * Baja el telón: cierra todos los navegadores abiertos y limpia el Stage.
     */
    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }
}
