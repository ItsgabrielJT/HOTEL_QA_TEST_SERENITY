# language: es
@smoke
Característica: Búsqueda de habitaciones disponibles

  Como potencial huésped del hotel
  Quiero buscar habitaciones disponibles indicando fechas de entrada y salida
  Para poder evaluar las opciones antes de realizar una reserva

  Antecedentes:
    Dado que María se encuentra en la página principal del hotel

  @busqueda_con_resultados
  Escenario: El huésped visualiza habitaciones disponibles al buscar fechas válidas
    Cuando busca habitaciones del "2026-05-10" al "2026-05-11"
    Entonces ve un listado de habitaciones disponibles para seleccionar

  @busqueda_sin_fechas
  Escenario: El huésped no puede buscar sin haber ingresado ambas fechas
    Cuando intenta buscar sin seleccionar fechas
    Entonces no se muestran habitaciones en el listado

  @busqueda_parametrizada
  Esquema del escenario: El huésped busca con distintas combinaciones de fechas válidas
    Cuando busca habitaciones del "<entrada>" al "<salida>"
    Entonces ve un listado de habitaciones disponibles para seleccionar

    Ejemplos:
      | entrada    | salida     |
      | 2026-05-10 | 2026-05-11 |
      | 2026-05-15 | 2026-05-17 |
