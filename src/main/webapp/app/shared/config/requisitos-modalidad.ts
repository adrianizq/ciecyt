// Tipos referenciados por la matriz; no se requieren imports especiales.

/**
 * Acuerdo 25, art. 5 par. 2 y Tabla 3: cada opcion de grado exige, ademas de
 * los documentos comunes de la Tabla 2, unos documentos adicionales que
 * dependen de la modalidad elegida. Este modulo centraliza esa matriz para
 * que el frontend pueda:
 *   - Mostrar al estudiante que documentos debe entregar ademas del paquete
 *     comun.
 *   - Validar que el estudiante vio el check antes de inscribir.
 *   - Avisar al CIECYT/decano de un documento faltante.
 *
 * Los ids de modalidad siguen la convencion ya usada en
 * shared/config/opcion_grado.ts (MODALIDADES_SUSTENTACION / SOCIALIZACION):
 *   9001  Pasantia Tecnologica
 *   9002  Pasantia Investigativa Profesional
 *   9003  Pasantia Internacional
 *   9004  Tesis
 *   9005  Diplomado de Profundizacion
 *   9006  Publicacion de Articulo
 *   9007  Especializacion como opcion de grado
 */

export interface RequisitoAdicional {
  /** Codigo corto estable para identificar el documento en logs y reportes. */
  codigo: string;
  /** Nombre legible tal como aparece en la Tabla 3 del Acuerdo 25. */
  nombre: string;
  /** Descripcion de una linea de su contenido o proposito. */
  descripcion: string;
  /** Referencia al articulo/paragrafo del Acuerdo donde se exige. */
  referencia: string;
  /**
   * Si el documento debe ser firmado solo por el estudiante y el CIECYT,
   * o si requiere tambien firma del decano/asesor/representante legal.
   * Sirve al usuario para saber que debe coordinar con esas instancias.
   */
  firmantes: string[];
  /**
   * Si true, el documento aplica solo a Programas de Pregrado (la asistencia
   * minima a 2 sustentaciones previas no aplica a Posgrado, art. 5 par. 1).
   * En este caso es false salvo que se diga explicitamente.
   */
}

export const REQUISITOS_ADICIONALES_POR_MODALIDAD: Record<number, RequisitoAdicional[]> = {
  9001: [
    // Pasantia Tecnologica (los mismos que la 9002)
    {
      codigo: 'PT-OFICIO-DECANO',
      nombre: 'Oficio de presentacion del pasante',
      descripcion: 'Expedido por el decano de la facultad a la que pertenece el estudiante.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad'],
    },
    {
      codigo: 'PT-FORM-AUTORIZACION',
      nombre: 'Formato de autorizacion para el desarrollo de la pasantia',
      descripcion: 'Firmado por el decano de facultad.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad'],
    },
    {
      codigo: 'PT-FORM-PROPUESTA',
      nombre: 'Formato de presentacion de la propuesta de pasantia',
      descripcion:
        'Viabilizado por el decano de facultad (o quien haga sus veces), el asesor y el representante legal de la empresa/organizacion/institucion.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad', 'Asesor', 'Representante legal'],
    },
    {
      codigo: 'PT-ACTA-INICIO',
      nombre: 'Acta de inicio de la pasantia',
      descripcion: 'Firmada por el estudiante, el asesor y el representante legal de la empresa/organizacion/institucion.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Estudiante', 'Asesor', 'Representante legal'],
    },
  ],
  9002: [
    // Pasantia Investigativa Profesional: mismos requisitos que la tecnologica
    {
      codigo: 'PIP-OFICIO-DECANO',
      nombre: 'Oficio de presentacion del pasante',
      descripcion: 'Expedido por el decano de la facultad a la que pertenece el estudiante.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad'],
    },
    {
      codigo: 'PIP-FORM-AUTORIZACION',
      nombre: 'Formato de autorizacion para el desarrollo de la pasantia',
      descripcion: 'Firmado por el decano de facultad.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad'],
    },
    {
      codigo: 'PIP-FORM-PROPUESTA',
      nombre: 'Formato de presentacion de la propuesta de pasantia',
      descripcion: 'Viabilizado por el decano de facultad (o quien haga sus veces), el asesor y el representante legal.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad', 'Asesor', 'Representante legal'],
    },
    {
      codigo: 'PIP-ACTA-INICIO',
      nombre: 'Acta de inicio de la pasantia',
      descripcion: 'Firmada por el estudiante, el asesor y el representante legal.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Estudiante', 'Asesor', 'Representante legal'],
    },
  ],
  9003: [
    // Pasantia Internacional: requisitos de la 9001/9002 + internacionales
    {
      codigo: 'PI-OFICIO-DECANO',
      nombre: 'Oficio de presentacion del pasante',
      descripcion: 'Expedido por el decano de la facultad.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad'],
    },
    {
      codigo: 'PI-FORM-AUTORIZACION',
      nombre: 'Formato de autorizacion para el desarrollo de la pasantia',
      descripcion: 'Firmado por el decano de facultad.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad'],
    },
    {
      codigo: 'PI-FORM-PROPUESTA',
      nombre: 'Formato de presentacion de la propuesta de pasantia',
      descripcion: 'Viabilizado por el decano, el asesor y el representante legal.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Decano de facultad', 'Asesor', 'Representante legal'],
    },
    {
      codigo: 'PI-ACTA-INICIO',
      nombre: 'Acta de inicio de la pasantia',
      descripcion: 'Firmada por el estudiante, el asesor y el representante legal.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Estudiante', 'Asesor', 'Representante legal'],
    },
    {
      codigo: 'PI-CARTA-ACEPTACION',
      nombre: 'Carta de aceptacion de la institucion anfitriona',
      descripcion: 'Documento original expedido por la institucion donde se realizara la pasantia internacional.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: ['Institucion anfitriona'],
    },
    {
      codigo: 'PI-PASAPORTE-VISA',
      nombre: 'Copia del pasaporte y visa (si aplica)',
      descripcion: 'Copia vigente del pasaporte del estudiante y de la visa cuando el pais lo requiera.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: [],
    },
  ],
  9004: [
    // Tesis: pregrado y posgrado. La propuesta diferenciada la distingue el art. 28/29.
    {
      codigo: 'T-FORM-INSCRIPCION',
      nombre: 'Formato de inscripcion de la propuesta de tesis',
      descripcion: 'Diligenciado por el estudiante y avalado por el asesor.',
      referencia: 'Acuerdo 25 art. 28 par. 1 (pregrado) / art. 29 par. 1 (posgrado)',
      firmantes: ['Estudiante', 'Asesor'],
    },
    {
      codigo: 'T-CARTA-AVAL',
      nombre: 'Carta de aval del asesor de tesis',
      descripcion: 'Dirigida al CIECYT, firmada por el asesor.',
      referencia: 'Acuerdo 25 art. 28 par. 1 (pregrado) / art. 29 par. 1 (posgrado)',
      firmantes: ['Asesor'],
    },
  ],
  9005: [
    // Diplomado de Profundizacion
    {
      codigo: 'D-SOPORTE-PAGO',
      nombre: 'Soporte de pago del diplomado',
      descripcion: 'Comprobante de pago ante tesoreria por el valor del diplomado contratado como opcion de grado.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3',
      firmantes: [],
    },
  ],
  9006: [
    // Publicacion de Articulo
    {
      codigo: 'PA-CARTA-ACEPTACION',
      nombre: 'Carta de aceptacion del articulo en la revista',
      descripcion:
        'Documento expedido por la revista indexada (Publindex u homologo internacional) que confirma la aceptacion del articulo.',
      referencia: 'Acuerdo 25 art. 5 par. 2, Tabla 3, art. 34',
      firmantes: ['Revista indexada'],
    },
  ],
  9007: [
    // Especializacion como opcion de grado
    {
      codigo: 'E-PROPUESTA-INVESTIGACION',
      nombre: 'Propuesta de investigacion para la especializacion',
      descripcion:
        'Presentada ante el programa de posgrado al momento de la matricula (art. 39). Sera remitida al CIECYT para seguimiento del tramite de grado.',
      referencia: 'Acuerdo 25 art. 39, art. 38, art. 5 par. 2, Tabla 3',
      firmantes: [],
    },
  ],
};

/**
 * Devuelve los requisitos adicionales propios de la modalidad. Si la modalidad
 * no tiene requisitos especificos (caso de modalidades aun no modeladas),
 * devuelve una lista vacia.
 */
export function requisitosAdicionalesPorModalidad(modalidadId: number | null | undefined): RequisitoAdicional[] {
  if (modalidadId == null) {
    return [];
  }
  return REQUISITOS_ADICIONALES_POR_MODALIDAD[Number(modalidadId)] || [];
}

/**
 * Devuelve un resumen corto de los requisitos adicionales (uno por linea)
 * adecuado para mostrar como pista/bullets en una UI.
 */
export function resumenRequisitosAdicionales(modalidadId: number | null | undefined): string[] {
  return requisitosAdicionalesPorModalidad(modalidadId).map(r => {
    const firmantes = r.firmantes.length > 0 ? ` (firmado por ${r.firmantes.join(', ')})` : '';
    return `${r.nombre}${firmantes}`;
  });
}

/**
 * Etiqueta corta que muestra la cantidad de requisitos adicionales de la
 * modalidad: util para tarjetas de resumen y para placeholders de subida.
 */
export function cantidadRequisitosAdicionales(modalidadId: number | null | undefined): number {
  return requisitosAdicionalesPorModalidad(modalidadId).length;
}

/**
 * Une los requisitos comunes (Tabla 2) con los adicionales (Tabla 3) para una
 * vista completa. Los comunes se conservan como referencia cruzada; el Acuerdo
 * los llama "documentos comunes" y son exigibles a todas las modalidades de
 * pregrado (ver art. 5 par. 1).
 */
export interface RequisitoCompleto extends RequisitoAdicional {
  /** 'comun' si viene de la Tabla 2, 'adicional' si viene de la Tabla 3. */
  tipo: 'comun' | 'adicional';
}

/**
 * Documentos comunes exigidos a todas las modalidades de pregrado, art. 5
 * par. 1 y Tabla 2. No son especificos de una modalidad; se listan aqui
 * para referencia cruzada.
 */
export const REQUISITOS_COMUNES_PREGRADO: RequisitoCompleto[] = [
  {
    codigo: 'COMUN-CERTIFICADO-ACTIVO',
    tipo: 'comun',
    nombre: 'Certificado de estudiante activo',
    descripcion: 'Expedido por registro y control.',
    referencia: 'Acuerdo 25 art. 5 par. 1, Tabla 2',
    firmantes: ['Registro y control'],
  },
  {
    codigo: 'COMUN-RECIBO-PAGO',
    tipo: 'comun',
    nombre: 'Recibo de pago de la opcion de grado',
    descripcion: 'Validado por tesoreria.',
    referencia: 'Acuerdo 25 art. 5 par. 1, Tabla 2',
    firmantes: ['Tesoreria'],
  },
  {
    codigo: 'COMUN-RECORD-ACADEMICO',
    tipo: 'comun',
    nombre: 'Record academico',
    descripcion: 'Expedido por registro y control.',
    referencia: 'Acuerdo 25 art. 5 par. 1, Tabla 2',
    firmantes: ['Registro y control'],
  },
  {
    codigo: 'COMUN-FORMATO-INSCRIPCION',
    tipo: 'comun',
    nombre: 'Formato de inscripcion',
    descripcion: 'Diligenciado por el estudiante.',
    referencia: 'Acuerdo 25 art. 5 par. 1, Tabla 2',
    firmantes: ['Estudiante'],
  },
  {
    codigo: 'COMUN-PROPUESTA-OPCION',
    tipo: 'comun',
    nombre: 'Propuesta de opcion de grado',
    descripcion: 'Cuando sea requerida segun la opcion elegida. Elaborada por el estudiante y avalada por el asesor.',
    referencia: 'Acuerdo 25 art. 5 par. 1, Tabla 2',
    firmantes: ['Estudiante', 'Asesor'],
  },
  {
    codigo: 'COMUN-ASISTENCIA-SUSTENTACIONES',
    tipo: 'comun',
    nombre: 'Asistencia a 2 sustentaciones o socializaciones previas',
    descripcion:
      'Acreditar asistencia a minimo dos (2) actos publicos de opciones de grado del programa o afines. Solo aplica a programas de pregrado.',
    referencia: 'Acuerdo 25 art. 5 par. 1, Tabla 2',
    firmantes: ['CIECYT'],
  },
];

/**
 * Compone los requisitos comunes y los adicionales de la modalidad en una
 * sola lista. La lista resultante es apta para mostrar al usuario el paquete
 * completo de documentos que debe entregar al CIECYT.
 */
export function paqueteCompletoDeRequisitos(modalidadId: number | null | undefined): RequisitoCompleto[] {
  const adicionales = requisitosAdicionalesPorModalidad(modalidadId).map<RequisitoCompleto>(r => ({ ...r, tipo: 'adicional' }));
  return [...REQUISITOS_COMUNES_PREGRADO, ...adicionales];
}
