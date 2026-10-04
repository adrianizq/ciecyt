import {
  diasHabilesEntre,
  diasHabilesDesde,
  evaluarPlazo,
  etiquetaPlazo,
  variantePlazo,
  PLAZOS_ACUERDO_25,
} from '@/shared/composables/dias-habiles';

describe('dias-habiles (Acuerdo 25 - plazos art. 9, 10, 13)', () => {
  describe('PLAZOS_ACUERDO_25', () => {
    it('define 7 dias habiles para designacion de jurado (art. 9 par. 3)', () => {
      expect(PLAZOS_ACUERDO_25.DESIGNACION_JURADO).toBe(7);
    });

    it('define 2 dias habiles para impedimento de jurado (art. 9 par. 5)', () => {
      expect(PLAZOS_ACUERDO_25.IMPEDIMENTO_JURADO).toBe(2);
    });

    it('define 10 dias habiles para evaluacion y correcciones (art. 10, 13)', () => {
      expect(PLAZOS_ACUERDO_25.EVALUACION_PROPUESTA).toBe(10);
      expect(PLAZOS_ACUERDO_25.CORRECCIONES_PROPUESTA).toBe(10);
      expect(PLAZOS_ACUERDO_25.EVALUACION_DOCUMENTO_FINAL).toBe(10);
    });
  });

  describe('diasHabilesEntre', () => {
    it('cuenta solo dias de lunes a viernes', () => {
      // 2024-01-01 (lunes) a 2024-01-07 (domingo) = 5 habiles
      expect(diasHabilesEntre('2024-01-01', '2024-01-07')).toBe(5);
    });

    it('no incluye sabado ni domingo', () => {
      // 2024-01-05 (viernes) a 2024-01-08 (lunes) = 2 habiles (viernes + lunes)
      expect(diasHabilesEntre('2024-01-05', '2024-01-08')).toBe(2);
    });

    it('devuelve negativo cuando hasta es anterior a desde', () => {
      // 2024-01-05 (viernes) a 2024-01-08 (lunes) = +2; el inverso es -2.
      expect(diasHabilesEntre('2024-01-08', '2024-01-05')).toBe(-2);
    });

    it('cuenta ambos extremos cuando caen en dia habil', () => {
      expect(diasHabilesEntre('2024-01-01', '2024-01-01')).toBe(1);
    });

    it('devuelve null si desde es null', () => {
      expect(diasHabilesEntre(null, new Date())).toBeNull();
    });

    it('devuelve null si hasta es null', () => {
      expect(diasHabilesEntre(new Date(), null)).toBeNull();
    });
  });

  describe('evaluarPlazo', () => {
    it('marca "sin-programar" cuando no hay fecha de inicio', () => {
      const estado = evaluarPlazo(null, 7);
      expect(estado.vigente).toBe('sin-programar');
      expect(estado.diasRestantes).toBeNull();
    });

    it('marca "completo" cuando se cumplio exactamente el plazo', () => {
      // 2024-01-01 a 2024-01-08 son 6 dias habiles inclusive (lun-mar-mie-jue-vie-lun).
      // Plaza = 6 -> "completo".
      const estado = evaluarPlazo('2024-01-01', 6, new Date('2024-01-08T12:00:00'));
      expect(estado.vigente).toBe('completo');
      expect(estado.diasRestantes).toBe(0);
    });

    it('marca "vencido" cuando se supero el plazo', () => {
      // plazo de 5 habiles; en dia 6 habiles (2024-01-08) -> vencido.
      const estado = evaluarPlazo('2024-01-01', 5, new Date('2024-01-08T12:00:00'));
      expect(estado.vigente).toBe('vencido');
    });

    it('marca "vence-pronto" cuando restan 2 o menos dias habiles', () => {
      // plazo 10 habiles; en dia 8 habiles (2024-01-10 = mie) -> restan 2.
      const estado = evaluarPlazo('2024-01-01', 10, new Date('2024-01-10T12:00:00'));
      expect(estado.vigente).toBe('vence-pronto');
      expect(estado.diasRestantes).toBe(2);
    });

    it('marca "en-curso" cuando restan mas de 2 dias habiles', () => {
      // plazo 10 habiles; en dia 6 habiles (2024-01-08) -> restan 4.
      const estado = evaluarPlazo('2024-01-01', 10, new Date('2024-01-08T12:00:00'));
      expect(estado.vigente).toBe('en-curso');
      expect(estado.diasRestantes).toBe(4);
    });
  });

  describe('etiquetaPlazo / variantePlazo', () => {
    it('etiqueta "Sin programar"', () => {
      expect(etiquetaPlazo(evaluarPlazo(null, 10))).toBe('Sin programar');
    });

    it('etiqueta "Vencido" cuando vencio', () => {
      expect(etiquetaPlazo(evaluarPlazo('2024-01-01', 2, new Date('2024-02-15')))).toMatch(/Vencido/);
    });

    it('variante danger cuando vencio', () => {
      expect(variantePlazo(evaluarPlazo('2024-01-01', 2, new Date('2024-02-15')))).toBe('danger');
    });

    it('variante success cuando completo', () => {
      // plazo de 6 habiles; en dia 6 (2024-01-08) -> success.
      expect(variantePlazo(evaluarPlazo('2024-01-01', 6, new Date('2024-01-08')))).toBe('success');
    });
  });

  describe('diasHabilesDesde', () => {
    it('devuelve los dias habiles transcurridos desde una fecha', () => {
      // desde lunes, ahora lunes siguiente = 6 habiles inclusive
      const r = diasHabilesDesde('2024-01-01', new Date('2024-01-08T12:00:00'));
      expect(r).toBe(6);
    });
  });
});
