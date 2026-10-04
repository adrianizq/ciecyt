/**
 * Calculo de dias habiles a partir de fechas, alineado con los plazos del
 * Acuerdo 025 (reglamento de opciones de grado):
 *   - 2 dias habiles: jurado impedido informa (art. 9 par. 5)
 *   - 7 dias habiles: designacion de jurado (art. 9 par. 3) /
 *                     decanatura revisa propuesta de pasantia (art. 28 par. 1)
 *   - 10 dias habiles: evaluacion de propuesta (art. 10) /
 *                      correcciones (art. 10 par. 4) /
 *                      evaluacion del documento final (art. 13)
 *
 * Sabados y domingos no cuentan. Festivos nacionales no estan modelados aqui;
 * la idea es aproximar el calculo al del Acuerdo sin obligar a configurar
 * un calendario. Si la fecha de inicio es domingo o sabado se cuenta desde el
 * siguiente dia habil.
 */

const MS_PER_DAY = 24 * 60 * 60 * 1000;

function esFinDeSemana(d: Date): boolean {
  const dow = d.getUTCDay();
  return dow === 0 || dow === 6;
}

/**
 * Normaliza una fecha a medianoche UTC para que las comparaciones sean
 * independientes de la zona horaria del navegador. Se admite string ISO
 * 'YYYY-MM-DD' (interpretado como UTC) o un objeto Date.
 */
function aMedianocheUtc(fecha: string | Date): Date {
  const d = fecha instanceof Date ? fecha : new Date(fecha);
  if (isNaN(d.getTime())) {
    return d;
  }
  return new Date(Date.UTC(d.getUTCFullYear(), d.getUTCMonth(), d.getUTCDate()));
}

/**
 * Devuelve el numero de dias habiles entre dos fechas, ambos extremos incluidos.
 * Si `hasta` es anterior a `desde`, devuelve un numero negativo con el mismo
 * calculo (util para comparar plazos vencidos con dias de retraso).
 *
 * Si alguno es falsy o invalido, devuelve null para que el llamador lo muestre
 * como "sin fecha".
 */
export function diasHabilesEntre(desde: string | Date | null | undefined, hasta: string | Date | null | undefined): number | null {
  if (!desde || !hasta) {
    return null;
  }
  const dDesde = aMedianocheUtc(desde);
  const dHasta = aMedianocheUtc(hasta);
  if (isNaN(dDesde.getTime()) || isNaN(dHasta.getTime())) {
    return null;
  }
  let cuenta = 0;
  if (dDesde.getTime() <= dHasta.getTime()) {
    const cursor = new Date(dDesde.getTime());
    while (cursor.getTime() <= dHasta.getTime()) {
      if (!esFinDeSemana(cursor)) {
        cuenta++;
      }
      cursor.setUTCDate(cursor.getUTCDate() + 1);
    }
  } else {
    const cursor = new Date(dHasta.getTime());
    while (cursor.getTime() <= dDesde.getTime()) {
      if (!esFinDeSemana(cursor)) {
        cuenta--;
      }
      cursor.setUTCDate(cursor.getUTCDate() + 1);
    }
  }
  return cuenta;
}

/**
 * Cuenta los dias habiles transcurridos desde una fecha de inicio hasta hoy.
 * Devuelve null si la fecha es faltante.
 */
export function diasHabilesDesde(fechaInicio: string | Date | null | undefined, ahora: Date = new Date()): number | null {
  return diasHabilesEntre(fechaInicio, ahora);
}

/**
 * Estado de un plazo a partir de su fecha de inicio y la cantidad de dias habiles
 * concedidos. Si la fecha de inicio es null, el plazo esta "sin programar".
 */
export type EstadoPlazo = {
  vigente: 'sin-programar' | 'en-curso' | 'vence-pronto' | 'vencido' | 'completo';
  diasTranscurridos: number | null;
  diasRestantes: number | null;
};

const UMBRAL_VENCE_PRONTO = 2;

export function evaluarPlazo(fechaInicio: string | Date | null | undefined, diasPlazo: number, ahora: Date = new Date()): EstadoPlazo {
  if (!fechaInicio) {
    return { vigente: 'sin-programar', diasTranscurridos: null, diasRestantes: null };
  }
  const transcurridos = diasHabilesDesde(fechaInicio, ahora);
  if (transcurridos == null) {
    return { vigente: 'sin-programar', diasTranscurridos: null, diasRestantes: null };
  }
  if (transcurridos > diasPlazo) {
    return { vigente: 'vencido', diasTranscurridos: transcurridos, diasRestantes: null };
  }
  if (transcurridos === diasPlazo) {
    return { vigente: 'completo', diasTranscurridos: transcurridos, diasRestantes: 0 };
  }
  const restantes = diasPlazo - transcurridos;
  if (restantes <= UMBRAL_VENCE_PRONTO) {
    return { vigente: 'vence-pronto', diasTranscurridos: transcurridos, diasRestantes: restantes };
  }
  return { vigente: 'en-curso', diasTranscurridos: transcurridos, diasRestantes: restantes };
}

/**
 * Etiqueta en lenguaje natural del estado del plazo para mostrar en la UI.
 */
export function etiquetaPlazo(estado: EstadoPlazo): string {
  switch (estado.vigente) {
    case 'sin-programar':
      return 'Sin programar';
    case 'en-curso':
      return `En curso (${estado.diasRestantes} dias habiles restantes)`;
    case 'vence-pronto':
      return `Vence pronto (${estado.diasRestantes} dias habiles)`;
    case 'vencido':
      return `Vencido (retraso ${estado.diasTranscurridos} dias habiles)`;
    case 'completo':
      return 'Completado';
    default:
      return '';
  }
}

/**
 * Variant Bootstrap 5 para el badge del plazo.
 */
export function variantePlazo(estado: EstadoPlazo): string {
  switch (estado.vigente) {
    case 'sin-programar':
      return 'secondary';
    case 'en-curso':
      return 'info';
    case 'vence-pronto':
      return 'warning';
    case 'vencido':
      return 'danger';
    case 'completo':
      return 'success';
    default:
      return 'secondary';
  }
}

export const PLAZOS_ACUERDO_25 = {
  /**
   * Art. 9 par. 3 + art. 28 par. 1: designacion de jurado y revision de
   * propuesta de pasantia por el decano.
   */
  DESIGNACION_JURADO: 7,

  /**
   * Art. 9 par. 5: jurado impedido informa al CIECYT.
   */
  IMPEDIMENTO_JURADO: 2,

  /**
   * Art. 10 + art. 13: evaluacion de propuesta, correcciones y evaluacion
   * del documento final. Tambien art. 10 par. 4 para correcciones.
   */
  EVALUACION_PROPUESTA: 10,
  CORRECCIONES_PROPUESTA: 10,
  EVALUACION_DOCUMENTO_FINAL: 10,
};
