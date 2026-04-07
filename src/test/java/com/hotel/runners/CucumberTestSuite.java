package com.hotel.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner principal de la suite de pruebas Cucumber + Serenity BDD.
 *
 * <p>Configuración:
 * <ul>
 *   <li>{@code features} – directorio raíz de los archivos .feature.</li>
 *   <li>{@code glue} – paquetes con Step Definitions y Hooks.</li>
 *   <li>{@code plugin} – pretty para salida legible en consola.</li>
 *   <li>{@code tags} – etiqueta activa por defecto; cambiar según la suite a ejecutar.</li>
 *   <li>{@code monochrome} – salida de consola sin códigos de color para CI.</li>
 * </ul>
 *
 * <p>Para ejecutar sólo tests de humo:
 * <pre>{@code
 * ./gradlew test -Dcucumber.filter.tags="@smoke"
 * }</pre>
 *
 * <p>Para ejecutar toda la regresión:
 * <pre>{@code
 * ./gradlew test -Dcucumber.filter.tags="@smoke or @regression"
 * }</pre>
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features  = "src/test/resources/features",
        glue      = "com.hotel.stepdefinitions",
        plugin    = {"pretty"},
        tags      = "@happy_path",
        monochrome = true
)
public class CucumberTestSuite {
    // Clase runner – sin lógica de negocio
}
