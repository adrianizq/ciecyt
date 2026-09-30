export const VIABILIDAD_VALOR_VIABLE: string = 'VIABLE';
export const VIABILIDAD_VALOR_VIABLE_MODIFICACIONES: string = 'PENDIENTE';
export const VIABILIDAD_VALOR_NO_VIABLE: string = 'NO_VIABLE';

export interface OpcionViabilidad {
  valor: string;
  titulo: string;
  descripcion: string;
  variant: string;
  icon: string;
}

export const VIABILIDAD_OPCIONES: OpcionViabilidad[] = [
  {
    valor: VIABILIDAD_VALOR_VIABLE,
    titulo: 'Viable',
    descripcion:
      'La propuesta cumple con la totalidad de los requisitos del reglamento y no requiere modificaciones. Puede dar inicio al desarrollo del cronograma de actividades.',
    variant: 'success',
    icon: 'check-circle',
  },
  {
    valor: VIABILIDAD_VALOR_VIABLE_MODIFICACIONES,
    titulo: 'Viable con modificaciones',
    descripcion:
      'La propuesta cumple con los requisitos esenciales, pero presenta observaciones que deben ser corregidas. Debe incorporar las modificaciones señaladas y remitir la propuesta ajustada para una nueva revisión.',
    variant: 'warning',
    icon: 'clipboard-list',
  },
  {
    valor: VIABILIDAD_VALOR_NO_VIABLE,
    titulo: 'No viable',
    descripcion:
      'La propuesta no cumple con los requisitos mínimos del reglamento; deberá reformularla y volver a presentarla para una nueva evaluación.',
    variant: 'danger',
    icon: 'times-circle',
  },
];

export function opcionViabilidad(valor: string | undefined | null): OpcionViabilidad | undefined {
  if (valor == null) {
    return undefined;
  }
  return VIABILIDAD_OPCIONES.find(o => o.valor === valor);
}

export function textoViabilidad(valor: string | undefined | null): string {
  const opcion = opcionViabilidad(valor);
  return opcion ? opcion.titulo : 'Sin concepto de viabilidad';
}

export function descripcionViabilidad(valor: string | undefined | null): string {
  const opcion = opcionViabilidad(valor);
  return opcion ? opcion.descripcion : '';
}
