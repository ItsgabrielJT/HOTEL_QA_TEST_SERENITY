# language: es
@regression
Característica: Reserva de habitación – Happy Path

  Como huésped del hotel
  Quiero seleccionar una habitación disponible, completar mis datos y confirmar el pago
  Para garantizar mi estadía en las fechas elegidas

  Antecedentes:
    Dado que María se encuentra en la página principal del hotel

  @happy_path @smoke
  Escenario: Reserva exitosa luego de reintentar el pago rechazado
    Cuando busca habitaciones del "2026-05-10" al "2026-05-11"
    Y selecciona la habitación "#201"
    Y completa sus datos con nombre "Juan García" y email "juan@email.com"
    Y intenta confirmar el pago
    Entonces el sistema informa que el pago fue rechazado por el banco
    Cuando reintenta confirmar el pago
    Entonces su reserva queda confirmada con un código de reserva válido

  @seleccion_habitacion
  Escenario: Al seleccionar una habitación se redirige al checkout
    Cuando busca habitaciones del "2026-04-07" al "2026-04-08"
    Y selecciona la habitación "#201"
    Entonces es redirigido a la página de checkout con el resumen de reserva

  @validacion_pago_rechazado
  Escenario: El sistema muestra un mensaje claro cuando el pago es rechazado
    Cuando busca habitaciones del "2026-04-07" al "2026-04-08"
    Y selecciona la habitación "#201"
    Y completa sus datos con nombre "Juan García" y email "juan@email.com"
    Y intenta confirmar el pago
    Entonces el sistema informa que el pago fue rechazado por el banco

  @confirmacion_con_codigo
  Escenario: La página de confirmación muestra el código de reserva
    Cuando busca habitaciones del "2026-04-07" al "2026-04-08"
    Y selecciona la habitación "#201"
    Y completa sus datos con nombre "Juan García" y email "juan@email.com"
    Y intenta confirmar el pago
    Y reintenta confirmar el pago
    Entonces la confirmación muestra un código de reserva no vacío
    Y la confirmación muestra el texto "¡Reserva confirmada!"
