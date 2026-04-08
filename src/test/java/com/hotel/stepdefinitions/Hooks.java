package com.hotel.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.net.HttpURLConnection;
import java.net.URL;

public class Hooks {

    private static final String HOLD_ROOM_NUMBER = "203";
    private static final String CHECKIN  = "2026-05-10";
    private static final String CHECKOUT = "2026-05-11";
    private static final String API_BASE  = System.getProperty("webdriver.base.url", "http://localhost:5173");

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    /**
     * Antes del escenario de bloqueo, verifica que la habitación #203 esté disponible
     * consultando directamente la API de disponibilidad. Si la habitación sigue bloqueada
     * por un hold activo de una ejecución anterior, lanza un error claro en lugar de
     * fallar silenciosamente dentro del paso de selección.
     */
    @Before("@bloqueo_concurrente")
    public void verificarHabitacionDisponibleParaBloqueo() throws Exception {
        String urlStr = API_BASE + "/api/rooms/available"
                + "?checkin=" + CHECKIN
                + "&checkout=" + CHECKOUT;

        int maxAttempts = 3;
        for (int i = 1; i <= maxAttempts; i++) {
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            String body = new String(conn.getInputStream().readAllBytes());
            conn.disconnect();

            if (status == 200 && body.contains("\"" + HOLD_ROOM_NUMBER + "\"")) {
                return; // Habitación disponible, continuar con el test
            }

            if (i < maxAttempts) {
                Thread.sleep(3000);
            }
        }

        throw new IllegalStateException(
            "La habitación #" + HOLD_ROOM_NUMBER + " no está disponible para " +
            CHECKIN + "/" + CHECKOUT + ". Puede haber un hold activo de una ejecución " +
            "anterior (expira en 10 min). Reinicia los servicios con " +
            "'docker compose down -v && docker compose up -d' para limpiar el estado."
        );
    }

    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }
}
