# language: es
@regression
Característica: Bloqueo de habitación durante el proceso de reserva

  Como sistema de reservas
  Quiero que una habitación en proceso de checkout quede bloqueada temporalmente
  Para que otro usuario no pueda seleccionarla mientras está siendo reservada

  @bloqueo_concurrente @smoke
  Escenario: La habitación bloqueada por un usuario no aparece para otro usuario
    Dado que Carlos se encuentra en la página principal del hotel
    Y que Laura se encuentra en la página principal del hotel
    Cuando Carlos busca habitaciones del "2026-05-10" al "2026-05-11"
    Y Carlos selecciona la habitación "#203"
    Cuando Laura busca habitaciones del "2026-05-10" al "2026-05-11"
    Entonces Laura no ve la habitación "#203" en el listado de disponibles
