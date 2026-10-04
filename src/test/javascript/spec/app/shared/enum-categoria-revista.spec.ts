import { EnumCategoriaRevista, integrantesPermitidosPorCategoriaRevista } from '@/shared/model/enumerations/enum-categoria-revista.model';

describe('EnumCategoriaRevista (Acuerdo 25 art. 7 par. 2 + Tabla 9)', () => {
  it('expone los cuatro valores definidos en la Tabla 9', () => {
    expect(EnumCategoriaRevista.A1).toBe('A1');
    expect(EnumCategoriaRevista.A2).toBe('A2');
    expect(EnumCategoriaRevista.B).toBe('B');
    expect(EnumCategoriaRevista.C).toBe('C');
  });

  describe('integrantesPermitidosPorCategoriaRevista', () => {
    it('permite hasta 3 estudiantes para A1', () => {
      expect(integrantesPermitidosPorCategoriaRevista(EnumCategoriaRevista.A1)).toBe(3);
    });

    it('permite hasta 3 estudiantes para A2', () => {
      expect(integrantesPermitidosPorCategoriaRevista(EnumCategoriaRevista.A2)).toBe(3);
    });

    it('permite hasta 2 estudiantes para B', () => {
      expect(integrantesPermitidosPorCategoriaRevista(EnumCategoriaRevista.B)).toBe(2);
    });

    it('permite hasta 2 estudiantes para C', () => {
      expect(integrantesPermitidosPorCategoriaRevista(EnumCategoriaRevista.C)).toBe(2);
    });

    it('devuelve 0 si la categoria es null/undefined/desconocida', () => {
      expect(integrantesPermitidosPorCategoriaRevista(null)).toBe(0);
      expect(integrantesPermitidosPorCategoriaRevista(undefined)).toBe(0);
      expect(integrantesPermitidosPorCategoriaRevista('XYZ' as any)).toBe(0);
    });
  });
});
