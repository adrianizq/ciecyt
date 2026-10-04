export enum EnumCategoriaRevista {
  A1 = 'A1',
  A2 = 'A2',
  B = 'B',
  C = 'C',
}

/**
 * Acuerdo 25, art. 7 par. 2 y Tabla 9: el numero de estudiantes para la opcion
 * de grado Publicacion de Articulo depende de la categoria de la revista.
 *   A1, A2 -> hasta 3 estudiantes
 *   B,  C  -> hasta 2 estudiantes
 */
export function integrantesPermitidosPorCategoriaRevista(categoria: EnumCategoriaRevista | undefined | null): number {
  if (categoria === EnumCategoriaRevista.A1 || categoria === EnumCategoriaRevista.A2) {
    return 3;
  }
  if (categoria === EnumCategoriaRevista.B || categoria === EnumCategoriaRevista.C) {
    return 2;
  }
  return 0;
}
