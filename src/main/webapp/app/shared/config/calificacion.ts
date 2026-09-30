export const MODALIDADES_ESCALA_ESCALONADA = [9002, 9004];

export interface ICalificacion {
  categoria: string;
  variant: string;
  descripcion: string;
}

export function esCalificacionEscalonada(modalidadId): boolean {
  return MODALIDADES_ESCALA_ESCALONADA.includes(Number(modalidadId));
}

export function categoriaCalificacion(modalidadId, nota): ICalificacion {
  const n = Number(nota);
  if (nota === null || nota === undefined || isNaN(n)) {
    return { categoria: 'Sin calificación', variant: 'secondary', descripcion: '' };
  }
  if (esCalificacionEscalonada(modalidadId)) {
    if (n < 3.0) {
      return { categoria: 'Reprobado', variant: 'danger', descripcion: 'Menor a 3.0' };
    }
    if (n <= 4.4) {
      return { categoria: 'Aprobado', variant: 'success', descripcion: 'Entre 3.0 y 4.4' };
    }
    if (n < 5.0) {
      return { categoria: 'Meritorio', variant: 'info', descripcion: 'Entre 4.5 y 4.9' };
    }
    return { categoria: 'Laureado', variant: 'warning', descripcion: '5.0' };
  }
  return n >= 3.0
    ? { categoria: 'Aprobado', variant: 'success', descripcion: 'Aprobado' }
    : { categoria: 'Reprobado', variant: 'danger', descripcion: 'Reprobado' };
}

export function textoEscalaCalificacion(modalidadId): string {
  if (esCalificacionEscalonada(modalidadId)) {
    return 'Reprobado: menor a 3.0 · Aprobado: entre 3.0 y 4.4 · Meritorio: entre 4.5 y 4.9 · Laureado: 5.0';
  }
  return 'Aprobado (3.0 o superior) · Reprobado (menor a 3.0)';
}
