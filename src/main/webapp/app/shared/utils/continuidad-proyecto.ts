import { IProyecto } from '@/shared/model/proyecto.model';

/**
 * Acuerdo 25, art. 5 par. 3-5: una vez terminado el plan de estudios el
 * estudiante dispone de 3 periodos academicos de continuidad para sustentar
 * o socializar su opcion de grado. Transcurridos esos 3 periodos sin
 * culminar el tramite, pierde definitivamente el derecho.
 *
 * Art. 5 par. 6: por fuerza mayor o caso fortuito, el estudiante puede
 * solicitar un (1) periodo academico ADICIONAL, llevando el total a 4.
 *
 * Este modulo centraliza el conteo de los periodos consumidos en funcion
 * de los datos que ya expone el modelo (periodosContinuidadUsados y
 * continuidadPeriodoAdicional), sin asumir que el backend transiciona
 * el estado automaticamente: ese calculo de perdida es responsabilidad
 * del servicio y debe confirmar la marca CONTINUIDAD_PERDIDA.
 */

export type EstadoContinuidad = 'REGULAR' | 'CONTINUIDAD' | 'APLAZADO' | 'CONTINUIDAD_PERDIDA' | undefined;

export type LimiteInfo = {
  /** Periodos academicos ya consumidos reportados por el backend. */
  periodosUsados: number;
  /** Marcador del total esperado segun el Acuerdo (3 o 4 si hubo aplazamiento). */
  totalPeriodos: number;
  /** True cuando el marcador del modelo indica que el estudiante recibio +1. */
  tienePeriodoAdicional: boolean;
  /** True cuando periodosUsados >= totalPeriodos y aun no hay CONTINUIDAD_PERDIDA. */
  alLimite: boolean;
  /** True cuando periodosUsados > totalPeriodos o estado = CONTINUIDAD_PERDIDA. */
  excedido: boolean;
};

/**
 * Total de periodos academicos disponibles:
 *   - 3 normal (par. 3 art. 5)
 *   - 4 si se otorgo aplazamiento (par. 6 art. 5)
 */
export function totalPeriodosContinuidad(proyecto: IProyecto): number {
  return proyecto.continuidadPeriodoAdicional ? 4 : 3;
}

/**
 * Periodos ya consumidos. Si el backend aun no reporta nada, devuelve 0.
 * Esto refleja renovaciones de matricula en continuidad. NO incluye el
 * semestre regular: el Acuerdo cuenta solo DESPUES de terminado el plan.
 */
export function periodosContados(proyecto: IProyecto): number {
  return proyecto.periodosContinuidadUsados || 0;
}

/**
 * Etiqueta corta del marcador del Acuerdo segun el estado del modelo:
 *   - 3 sin aplazamiento
 *   - '3+1' con aplazamiento (mas acompanado del +1 visual)
 *   - '-' cuando el proyecto nunca ha entrado en continuidad.
 */
export function marcadorContinuidad(proyecto: IProyecto): string {
  if (proyecto.estadoContinuidad === 'CONTINUIDAD_PERDIDA') {
    return 'sin derecho';
  }
  if (proyecto.estadoContinuidad === 'CONTINUIDAD' || proyecto.estadoContinuidad === 'APLAZADO') {
    return proyecto.continuidadPeriodoAdicional ? '3+1' : '3';
  }
  return '-';
}

export function resumenLimite(proyecto: IProyecto): LimiteInfo {
  const totalPeriodos = totalPeriodosContinuidad(proyecto);
  const periodosUsados = periodosContados(proyecto);
  return {
    periodosUsados,
    totalPeriodos,
    tienePeriodoAdicional: !!proyecto.continuidadPeriodoAdicional,
    alLimite: proyecto.estadoContinuidad !== 'CONTINUIDAD_PERDIDA' && periodosUsados >= totalPeriodos,
    excedido: proyecto.estadoContinuidad === 'CONTINUIDAD_PERDIDA' || periodosUsados > totalPeriodos,
  };
}

/**
 * Etiqueta enriquecida para el badge de continuidad.
 * La frase incluye el numero de periodos ya consumidos y el total disponible,
 * sumando "(al limite de N)" cuando el estudiante ya no tiene margen.
 */
export function continuidadEtiqueta(proyecto: IProyecto): string {
  if (proyecto.estadoContinuidad === 'CONTINUIDAD_PERDIDA') {
    return 'Derecho perdido';
  }
  if (proyecto.estadoContinuidad === 'REGULAR' || !proyecto.estadoContinuidad) {
    return 'Regular';
  }
  // CONTINUIDAD o APLAZADO
  const resumen = resumenLimite(proyecto);
  const base = `En continuidad ${resumen.periodosUsados}/${resumen.totalPeriodos}`;
  if (resumen.excedido) {
    return `${base} (perdido tras ${resumen.periodosUsados} periodos)`;
  }
  if (resumen.alLimite) {
    return `${base} (al limite Acdo. 25 art. 5)`;
  }
  return base;
}

/**
 * Etiqueta de plazo de continuidad aplicable al artefacto plazosDelProyecto:
 * arranca cuando fechaInicioContinuidad esta seteada y vence a los 3/4
 * periodos academicos del Acuerdo. El conteo en dias habiles es aproximado:
 * 1 periodo academico ~= 4 meses ~= 60 dias habiles. Esto es unicamente una
 * senal visual para que el operador vea cuando esta cerca del fin del
 * margen. La transicion efectiva a CONTINUIDAD_PERDIDA la dispara el backend.
 */
export function plazoFinalContinuidad(proyecto: IProyecto, fechaFinPeriodo: Date = new Date()): number | null {
  if (!proyecto.fechaInicioContinuidad) {
    return null;
  }
  const totalMeses = totalPeriodosContinuidad(proyecto) * 4;
  const fechaFinEsperada = new Date(proyecto.fechaInicioContinuidad);
  fechaFinEsperada.setMonth(fechaFinEsperada.getMonth() + totalMeses);
  const msPorDia = 24 * 60 * 60 * 1000;
  const diffMs = fechaFinEsperada.getTime() - fechaFinPeriodo.getTime();
  return Math.ceil(diffMs / msPorDia);
}
