export const TIPO_ACTO_SUSTENTACION: string = 'SUSTENTACION';
export const TIPO_ACTO_SOCIALIZACION: string = 'SOCIALIZACION';
export const TIPO_ACTO_NINGUNO: string = 'NINGUNO';

const MODALIDADES_SUSTENTACION: number[] = [9002, 9004];
const MODALIDADES_SOCIALIZACION: number[] = [9001, 9003, 9006];

export const ESTADO_APROBADA_POR_ASESOR: string = 'APROBADA_POR_ASESOR';
export const ESTADO_NO_VIABLE: string = 'NO_VIABLE';
export const ESTADO_NOTA_DEFINITIVA: string = 'NOTA_DEFINITIVA';
export const ESTADO_FINALIZADO: string = 'FINALIZADO';

export const ESTADO_REVISION_ASESOR: string = 'EN_REVISION_ASESOR';
export const ESTADO_CORRECCIONES_ASESOR: string = 'CORRECCIONES_ASESOR';
export const ESTADO_REVISION_ASESOR_PROYECTO: string = 'EN_REVISION_ASESOR_PROYECTO';
export const ESTADO_CORRECCIONES_ASESOR_PROYECTO: string = 'CORRECCIONES_ASESOR_PROYECTO';

export const ESTADO_REVISION_JURADO_PROPUESTA: string = 'EN_REVISION_JURADO_PROPUESTA';
export const ESTADO_CORRECCIONES_JURADO_PROPUESTA: string = 'CORRECCIONES_JURADO_PROPUESTA';
export const ESTADO_REVISION_JURADO_PROYECTO: string = 'EN_REVISION_JURADO_PROYECTO';
export const ESTADO_CORRECCIONES_JURADO_PROYECTO: string = 'CORRECCIONES_JURADO_PROYECTO';

export const ESTADO_LISTO_PARA_SUSTENTAR: string = 'LISTO_PARA_SUSTENTAR';
export const ESTADO_SUSTENTACION_PROGRAMADA: string = 'SUSTENTACION_PROGRAMADA';
export const ESTADO_SUSTENTACION_REALIZADA: string = 'SUSTENTACION_REALIZADA';
export const ESTADO_EN_EVALUACION_SUSTENTACION: string = 'EN_EVALUACION_SUSTENTACION';

export const ESTADO_LISTO_PARA_SOCIALIZAR: string = 'LISTO_PARA_SOCIALIZAR';
export const ESTADO_SOCIALIZACION_PROGRAMADA: string = 'SOCIALIZACION_PROGRAMADA';
export const ESTADO_SOCIALIZACION_REALIZADA: string = 'SOCIALIZACION_REALIZADA';
export const ESTADO_EN_EVALUACION_SOCIALIZACION: string = 'EN_EVALUACION_SOCIALIZACION';

export function tieneJurado(modalidadId: number | undefined | null): boolean {
  return MODALIDADES_SUSTENTACION.includes(Number(modalidadId));
}

export function estadoRevisionPropuesta(modalidadId: number | undefined | null): string {
  return tieneJurado(modalidadId) ? ESTADO_REVISION_JURADO_PROPUESTA : ESTADO_REVISION_ASESOR;
}

export function estadoCorreccionesPropuesta(modalidadId: number | undefined | null): string {
  return tieneJurado(modalidadId) ? ESTADO_CORRECCIONES_JURADO_PROPUESTA : ESTADO_CORRECCIONES_ASESOR;
}

export function estadoRevisionProyecto(modalidadId: number | undefined | null): string {
  return tieneJurado(modalidadId) ? ESTADO_REVISION_JURADO_PROYECTO : ESTADO_REVISION_ASESOR_PROYECTO;
}

export function estadoCorreccionesProyecto(modalidadId: number | undefined | null): string {
  return tieneJurado(modalidadId) ? ESTADO_CORRECCIONES_JURADO_PROYECTO : ESTADO_CORRECCIONES_ASESOR_PROYECTO;
}

export function estadoListoParaActo(modalidadId: number | undefined | null): string | null {
  if (requiereSustentacion(modalidadId)) {
    return ESTADO_LISTO_PARA_SUSTENTAR;
  }
  if (requiereSocializacion(modalidadId)) {
    return ESTADO_LISTO_PARA_SOCIALIZAR;
  }
  return null;
}

export function estadoActoProgramado(modalidadId: number | undefined | null): string | null {
  if (requiereSustentacion(modalidadId)) {
    return ESTADO_SUSTENTACION_PROGRAMADA;
  }
  if (requiereSocializacion(modalidadId)) {
    return ESTADO_SOCIALIZACION_PROGRAMADA;
  }
  return null;
}

export function estadoActoRealizado(modalidadId: number | undefined | null): string | null {
  if (requiereSustentacion(modalidadId)) {
    return ESTADO_SUSTENTACION_REALIZADA;
  }
  if (requiereSocializacion(modalidadId)) {
    return ESTADO_SOCIALIZACION_REALIZADA;
  }
  return null;
}

export function estadoEvaluacionActo(modalidadId: number | undefined | null): string | null {
  if (requiereSustentacion(modalidadId)) {
    return ESTADO_EN_EVALUACION_SUSTENTACION;
  }
  if (requiereSocializacion(modalidadId)) {
    return ESTADO_EN_EVALUACION_SOCIALIZACION;
  }
  return null;
}

export function estadosActoPublico(modalidadId: number | undefined | null): string[] {
  if (requiereSustentacion(modalidadId)) {
    return [
      ESTADO_LISTO_PARA_SUSTENTAR,
      ESTADO_SUSTENTACION_PROGRAMADA,
      ESTADO_SUSTENTACION_REALIZADA,
      ESTADO_EN_EVALUACION_SUSTENTACION,
      ESTADO_NOTA_DEFINITIVA,
      ESTADO_FINALIZADO,
    ];
  }
  if (requiereSocializacion(modalidadId)) {
    return [
      ESTADO_LISTO_PARA_SOCIALIZAR,
      ESTADO_SOCIALIZACION_PROGRAMADA,
      ESTADO_SOCIALIZACION_REALIZADA,
      ESTADO_EN_EVALUACION_SOCIALIZACION,
      ESTADO_NOTA_DEFINITIVA,
      ESTADO_FINALIZADO,
    ];
  }
  return [ESTADO_NOTA_DEFINITIVA, ESTADO_FINALIZADO];
}

export function textoEnvioRevision(modalidadId: number | undefined | null): string {
  return tieneJurado(modalidadId) ? 'Enviar al jurado' : 'Emitir concepto favorable';
}

export function tipoActoPublico(modalidadId: number | undefined | null): string {
  if (modalidadId == null) {
    return TIPO_ACTO_NINGUNO;
  }
  if (MODALIDADES_SUSTENTACION.includes(modalidadId)) {
    return TIPO_ACTO_SUSTENTACION;
  }
  if (MODALIDADES_SOCIALIZACION.includes(modalidadId)) {
    return TIPO_ACTO_SOCIALIZACION;
  }
  return TIPO_ACTO_NINGUNO;
}

export function requiereSustentacion(modalidadId: number | undefined | null): boolean {
  return tipoActoPublico(modalidadId) === TIPO_ACTO_SUSTENTACION;
}

export function requiereSocializacion(modalidadId: number | undefined | null): boolean {
  return tipoActoPublico(modalidadId) === TIPO_ACTO_SOCIALIZACION;
}

export function requiereActoPublico(modalidadId: number | undefined | null): boolean {
  return tipoActoPublico(modalidadId) !== TIPO_ACTO_NINGUNO;
}

export function nombreActoPublico(modalidadId: number | undefined | null): string {
  const tipo = tipoActoPublico(modalidadId);
  return tipo === TIPO_ACTO_SUSTENTACION ? 'Sustentación' : tipo === TIPO_ACTO_SOCIALIZACION ? 'Socialización' : '';
}

export function referenciaAcuerdoActoPublico(modalidadId: number | undefined | null): string {
  const tipo = tipoActoPublico(modalidadId);
  if (tipo === TIPO_ACTO_SUSTENTACION) {
    return 'Sustentación ante jurado (Acuerdo 025, art. 14, parágrafo 1).';
  }
  if (tipo === TIPO_ACTO_SOCIALIZACION) {
    return 'Socialización ante la comunidad académica, sin jurado calificador (Acuerdo 025, art. 14, parágrafo 2).';
  }
  return 'No requiere sustentación ni socialización como requisito para la obtención del título (Acuerdo 025, art. 14, parágrafos 3 y 4).';
}
