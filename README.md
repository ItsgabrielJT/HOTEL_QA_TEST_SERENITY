# Hotel Booking – Automatización E2E con Serenity BDD + Screenplay

Proyecto de automatización de pruebas extremo a extremo (E2E) para la aplicación
**Hotel Booking MVP** (`http://localhost:5173`), construido con:

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Gradle | 8.7 |
| Serenity BDD | 4.2.34 |
| Cucumber | 7.x (gestionado por Serenity) |
| Selenium WebDriver | 4.x (gestionado por Serenity) |
| JUnit | 4.13.2 |

---

## Índice

1. [Requisitos previos](#1-requisitos-previos)
2. [Estructura del proyecto](#2-estructura-del-proyecto)
3. [Patrón Screenplay](#3-patrón-screenplay)
4. [Flujo automatizado (Happy Path)](#4-flujo-automatizado-happy-path)
5. [Archivos Feature (Gherkin)](#5-archivos-feature-gherkin)
6. [Buenas prácticas aplicadas](#6-buenas-prácticas-aplicadas)
7. [Cómo ejecutar los tests](#7-cómo-ejecutar-los-tests)
8. [Reportes de Serenity](#8-reportes-de-serenity)
9. [Configuración avanzada](#9-configuración-avanzada)

---

## 1. Requisitos previos

- **Java 17+** instalado (`java -version`)
- **Chrome** instalado (Serenity gestiona el ChromeDriver automáticamente)
- **Aplicación corriendo** en `http://localhost:5173` antes de ejecutar los tests

---

## 2. Estructura del proyecto

```
hotel-booking-e2e/
├── build.gradle                          # Configuración Gradle + dependencias Serenity
├── settings.gradle                       # Nombre del proyecto
├── gradlew / gradlew.bat                 # Wrapper de Gradle
├── src/
│   └── test/
│       ├── java/
│       │   └── com/hotel/
│       │       ├── interactions/
│       │       │   └── SetDateValue.java         # Interacción personalizada para inputs de fecha React
│       │       ├── ui/                           # Selectores del DOM (Targets)
│       │       │   ├── SearchPageUi.java
│       │       │   ├── CheckoutPageUi.java
│       │       │   └── ConfirmationPageUi.java
│       │       ├── tasks/                        # Tareas de negocio (alto nivel)
│       │       │   ├── NavigateToTheHotel.java
│       │       │   ├── SearchAvailableRooms.java
│       │       │   ├── SelectRoom.java
│       │       │   ├── CompleteGuestPayment.java
│       │       │   └── RetryPayment.java
│       │       ├── questions/                    # Preguntas sobre el estado del sistema
│       │       │   ├── ThePaymentError.java
│       │       │   ├── TheBookingCode.java
│       │       │   ├── TheRoomListIsDisplayed.java
│       │       │   ├── TheRoomIsNotListed.java
│       │       │   └── TheConfirmationTitle.java
│       │       ├── stepdefinitions/
│       │       │   ├── Hooks.java                # @Before / @After del Stage
│       │       │   ├── HotelBookingStepDefinitions.java
│       │       │   └── RoomBlockingStepDefinitions.java
│       │       └── runners/
│       │           └── CucumberTestSuite.java    # Runner JUnit 4
│       └── resources/
│           ├── serenity.conf                     # Configuración WebDriver + Serenity
│           └── features/
│               └── hotel_booking/
│                   ├── busqueda_habitaciones.feature
│                   ├── reserva_habitacion.feature
│                   └── bloqueo_habitacion.feature
└── target/
    └── site/serenity/                            # Reporte HTML generado
```

---

## 3. Patrón Screenplay

El patrón Screenplay organiza los tests alrededor de **cuatro conceptos**:

### Actores (Actors)
Representan a los usuarios del sistema. Se crean dinámicamente mediante el Stage:
```java
OnStage.theActorCalled("María")   // Primera mención (introduce al actor)
OnStage.theActorInTheSpotlight()  // Pasos siguientes (mismo escenario)
```

### Habilidades (Abilities)
Definen **qué puede hacer** un actor. Se asignan automáticamente por `OnlineCast`:
```java
OnStage.setTheStage(new OnlineCast());
// → cada actor recibe BrowseTheWeb automáticamente
```

### Tareas (Tasks)
Agrupan interacciones de bajo nivel en conceptos de negocio reutilizables:
```java
actor.attemptsTo(
    SearchAvailableRooms.from("2026-04-07").to("2026-04-08"),
    SelectRoom.withNumber("#101"),
    CompleteGuestPayment.withName("Juan García").andEmail("juan@email.com")
);
```

### Preguntas (Questions)
Permiten al actor consultar el estado del sistema para validaciones:
```java
actor.attemptsTo(
    Ensure.that(TheBookingCode.displayed()).isNotEmpty(),
    Ensure.that(ThePaymentError.message())
          .containsIgnoringCase("Pago rechazado por el banco")
);
```

---

## 4. Flujo automatizado (Happy Path)

```
[Home] Ingresar fechas + Buscar
        ↓
[Listado] Seleccionar habitación #101
        ↓
[Checkout] Llenar nombre y email + Pagar
        ↓
[Checkout] ❌ Error: "Pago rechazado por el banco. La habitación fue liberada."
        ↓
[Checkout] Reintentar pago
        ↓
[Confirmación] ✅ Código de reserva generado (p.ej. G8DEMUEN)
               URL: http://localhost:5173/confirmation/G8DEMUEN
```

---

## 5. Archivos Feature (Gherkin)

### `busqueda_habitaciones.feature`
Cubre la funcionalidad de búsqueda:
- Búsqueda exitosa con fechas válidas muestra el listado
- Búsqueda sin fechas no muestra habitaciones
- `Scenario Outline` parametrizado con múltiples combinaciones de fechas

### `reserva_habitacion.feature`
Cubre el flujo completo de reserva:
- **Happy Path** completo (búsqueda → selección → checkout → reintento si hay rechazo → confirmación)
- Verificación de redirección al checkout
- Verificación del mensaje de pago rechazado
- Verificación del código de reserva en la confirmación

### `bloqueo_habitacion.feature`
Cubre la disponibilidad concurrente de habitaciones:
- Habitación seleccionada por el usuario A desaparece del listado para el usuario B

---

## 6. Buenas prácticas aplicadas

### SOLID

| Principio | Aplicación |
|---|---|
| **SRP** | Cada clase tiene una única responsabilidad: `SearchPageUi` solo conoce selectores, `SearchAvailableRooms` solo busca habitaciones, `TheBookingCode` solo lee el código. |
| **OCP** | Las Tasks usan el patrón Builder (`from(...).to(...)`) para ser extensibles sin modificar la clase base. |
| **LSP** | Todas las Tasks implementan `Task` (que extiende `Performable`), son intercambiables y substituibles entre sí. |
| **ISP** | Las Questions exponen solo la interfaz mínima necesaria (`Question<String>`, `Question<Boolean>`). |
| **DIP** | Los Step Definitions dependen de abstracciones (`Task`, `Question`), nunca de implementaciones concretas de Selenium. |

### Composición sobre Herencia
Los actores adquieren habilidades via `OnlineCast`; no se extiende la clase `Actor`.

### Constructores por defecto
Todas las clases `Task` incluyen un constructor sin parámetros para que Serenity
registre correctamente cada paso en el reporte.

### Escenarios independientes
Cada escenario es autónomo y no depende del estado dejado por otro.
El `@After` con `OnStage.drawTheCurtain()` garantiza el cierre limpio del navegador.

### Interacción personalizada para inputs de fecha React
`SetDateValue` asigna el valor del `<input type="date">` mediante JavaScript y dispara
los eventos `input` y `change` para que React actualice su estado interno. Esto es
necesario porque Chrome no acepta `sendKeys` directamente en date inputs con locale
diferente a en-US.

---

## 7. Cómo ejecutar los tests

> **Requisito previo:** la aplicación debe estar corriendo en `http://localhost:5173`
> antes de lanzar cualquier suite.

### Suite completa (todos los escenarios)

```bash
./gradlew test
```

### Suite completa + reporte Serenity agregado

```bash
./gradlew test aggregate
```

### Solo Happy Path (reserva exitosa con o sin reintento de pago)

```bash
./gradlew test -Dcucumber.filter.tags="@happy_path"
```

### Solo tests de humo (smoke)

```bash
./gradlew test -Dcucumber.filter.tags="@smoke"
```

### Solo bloqueo concurrente de habitación

```bash
./gradlew test -Dcucumber.filter.tags="@bloqueo_concurrente"
```

### Solo búsqueda de habitaciones

```bash
./gradlew test -Dcucumber.filter.tags="@busqueda_con_resultados"
```

### Combinar etiquetas (OR)

```bash
./gradlew test -Dcucumber.filter.tags="@smoke or @bloqueo_concurrente"
```

### Combinar etiquetas (AND)

```bash
./gradlew test -Dcucumber.filter.tags="@regression and @happy_path"
```

### Ejecutar con Chrome headless (para CI/CD)

```bash
./gradlew test -Dchrome.switches="--headless=new,--disable-gpu,--no-sandbox"
```

---

## 8. Reportes de Serenity

Tras ejecutar los tests, el reporte HTML se genera en:

```
target/site/serenity/index.html
```

El reporte incluye:
- Capturas de pantalla de cada paso (configurado en `serenity.conf` con `AFTER_EACH_STEP`)
- Resumen ejecutivo de escenarios aprobados / fallidos
- Documentación viva con los pasos en lenguaje natural
- Línea de tiempo de ejecución

---

## 9. Configuración avanzada

### Cambiar la URL base de la aplicación

Editar `src/test/resources/serenity.conf`:
```
webdriver {
  base.url = "http://tu-entorno.com"
}
```

O sobreescribir en tiempo de ejecución:
```bash
./gradlew test -Dwebdriver.base.url="http://staging.tuempresa.com"
```

### Cambiar el navegador

```bash
./gradlew test -Dwebdriver.driver=firefox
```

### Ejecutar en paralelo

Agregar en `build.gradle`:
```groovy
test {
    maxParallelForks = 4
}
```

Y configurar `@CucumberOptions` con `parallel`:
```java
@CucumberOptions(plugin = {"io.cucumber.core.plugin.SerenityReporter"}, parallel = true)
```

---

## Etiquetas disponibles

| Tag | Feature | Descripción |
|---|---|---|
| `@smoke` | ambas | Tests críticos de humo (ejecución rápida) |
| `@regression` | `reserva_habitacion` | Suite completa de regresión de reserva |
| `@happy_path` | `reserva_habitacion` | Flujo completo de reserva exitosa (con o sin reintento de pago) |
| `@bloqueo_concurrente` | `bloqueo_habitacion` | Habitación bloqueada no aparece para segundo usuario |
| `@busqueda_con_resultados` | `busqueda_habitaciones` | Búsqueda con resultados positivos |
| `@busqueda_sin_fechas` | `busqueda_habitaciones` | Validación sin fechas seleccionadas |
| `@busqueda_parametrizada` | `busqueda_habitaciones` | Scenario Outline con múltiples combinaciones de fechas |
| `@seleccion_habitacion` | `reserva_habitacion` | Verificación de redirección al checkout |
| `@validacion_pago_rechazado` | `reserva_habitacion` | Verificación del mensaje de pago rechazado |
| `@confirmacion_con_codigo` | `reserva_habitacion` | Verificación del código de reserva en confirmación |
