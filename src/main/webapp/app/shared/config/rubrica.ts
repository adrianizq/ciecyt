import { categoriaCalificacion, esCalificacionEscalonada, ICalificacion } from '@/shared/config/calificacion';

export interface INivelRubrica {
  nivel: string;
  puntaje: number;
  descripcion: string;
}

export interface ICriterioRubrica {
  codigo: string;
  criterio: string;
  descripcion: string;
  peso: number;
  niveles: INivelRubrica[];
}

export interface IRubricaEvaluacion {
  puntajePromedio: number;
  notaSugerida: number;
  categoria: ICalificacion;
}

// Rúbrica institucional de evaluación de la propuesta (Acuerdo 025, art. 14, parágrafo 7).
// Está alineada con la estructura de la propuesta (art. 6) y con las calificaciones
// del artículo 14, parágrafo 9 del reglamento.
export const NIVELES_RUBRICA_PROCESO = ((): INivelRubrica[] => [
  {
    nivel: 'Insuficiente',
    puntaje: 1.0,
    descripcion: 'No cumple o cumple de forma deficiente el criterio evaluado.',
  },
  {
    nivel: 'Básico',
    puntaje: 3.0,
    descripcion: 'Cumple parcialmente el criterio evaluado, con aspectos por mejorar.',
  },
  {
    nivel: 'Bueno',
    puntaje: 4.0,
    descripcion: 'Cumple adecuadamente el criterio evaluado, con observaciones menores.',
  },
  {
    nivel: 'Sobresaliente',
    puntaje: 5.0,
    descripcion: 'Cumple de forma excelente el criterio evaluado, sin observaciones.',
  },
])();

export const CRITERIOS_RUBRICA_PROPUESTA: ICriterioRubrica[] = [
  {
    codigo: 'RP1',
    criterio: 'Planteamiento del problema y pertinencia',
    descripcion:
      'El problema está claramente planteado y es pertinente con el programa académico y con las necesidades de la región y la institución.',
    peso: 20,
    niveles: NIVELES_RUBRICA_PROCESO,
  },
  {
    codigo: 'RP2',
    criterio: 'Justificación en términos de necesidades y pertinencia',
    descripcion:
      'La justificación expresa las necesidades, la pertinencia y la utilidad de la propuesta para la comunidad y la disciplina.',
    peso: 15,
    niveles: NIVELES_RUBRICA_PROCESO,
  },
  {
    codigo: 'RP3',
    criterio: 'Objetivos general y específicos',
    descripcion: 'Los objetivos son claros, coherentes con el problema y alcanzables en el tiempo previsto.',
    peso: 15,
    niveles: NIVELES_RUBRICA_PROCESO,
  },
  {
    codigo: 'RP4',
    criterio: 'Metodología, marco teórico y referentes',
    descripcion: 'La metodología y los referentes teóricos son adecuados y guardan coherencia con los objetivos propuestos.',
    peso: 15,
    niveles: NIVELES_RUBRICA_PROCESO,
  },
  {
    codigo: 'RP5',
    criterio: 'Cronograma de actividades y viabilidad de ejecución',
    descripcion: 'El cronograma es realista y permite el desarrollo de la opción de grado dentro de los tiempos y recursos disponibles.',
    peso: 15,
    niveles: NIVELES_RUBRICA_PROCESO,
  },
  {
    codigo: 'RP6',
    criterio: 'Norma APA y redacción',
    descripcion:
      'La citación y las referencias bibliográficas siguen la norma APA en su última actualización (art. 6, parágrafo 1) y la redacción es clara y sin errores.',
    peso: 10,
    niveles: NIVELES_RUBRICA_PROCESO,
  },
  {
    codigo: 'RP7',
    criterio: 'Coherencia y estructura general de la propuesta',
    descripcion:
      'La propuesta cumple con la estructura exigida para la opción de grado y presenta coherencia en su conjunto, incluido el apartado de responsabilidad de los autores (art. 6, parágrafo 2).',
    peso: 10,
    niveles: NIVELES_RUBRICA_PROCESO,
  },
];

// Escala de calificación de las opciones de grado (art. 14, parágrafo 9, en concordancia
// con el parágrafo 2 del artículo 55 del Estatuto Estudiantil).
export function escalaCalificacionRubrica(modalidadId): string {
  if (esCalificacionEscalonada(modalidadId)) {
    return 'Reprobado: menor a 3.0 · Aprobado: entre 3.0 y 4.4 · Meritorio: entre 4.5 y 4.9 · Laureado: 5.0';
  }
  return 'Reprobado: menor a 3.0 · Aprobado: 3.0 o superior';
}

// Promedio ponderado de los puntajes de la rúbrica (0 a 5).
export function evaluarRubricaPropuesta(puntajesPorCriterio: { [codigo: string]: number }): IRubricaEvaluacion | null {
  const criterios = CRITERIOS_RUBRICA_PROPUESTA;
  const totalPeso = criterios.reduce((suma, c) => suma + c.peso, 0);
  let acumulado = 0;
  let sumado = 0;
  for (const criterio of criterios) {
    const puntaje = Number(puntajesPorCriterio[criterio.codigo]);
    if (isNaN(puntaje)) {
      continue;
    }
    acumulado += puntaje * criterio.peso;
    sumado += criterio.peso;
  }
  if (sumado === 0) {
    return null;
  }
  const notaSugerida = Math.round((acumulado / sumado) * 1) / 1;
  const prom = Math.round((acumulado / totalPeso) * 1) / 1;
  return {
    puntajePromedio: prom,
    notaSugerida,
    categoria: categoriaCalificacion(9004, notaSugerida),
  };
}

export type { ICalificacion };
