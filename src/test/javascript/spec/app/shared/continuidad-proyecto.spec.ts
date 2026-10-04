import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import {
  continuidadEtiqueta,
  marcadorContinuidad,
  periodosContados,
  plazoFinalContinuidad,
  resumenLimite,
  totalPeriodosContinuidad,
} from '@/shared/utils/continuidad-proyecto';

describe('continuidad-proyecto (Acuerdo 25 art. 5 par. 3-6)', () => {
  function proyectoEnEstado(estado: string, opts: Partial<IProyecto> = {}): IProyecto {
    const p = new Proyecto();
    p.estadoContinuidad = estado as any;
    return Object.assign(p, opts) as IProyecto;
  }

  describe('totalPeriodosContinuidad (art. 5 par. 3 y par. 6)', () => {
    it('devuelve 3 sin aplazamiento', () => {
      expect(totalPeriodosContinuidad(proyectoEnEstado('REGULAR'))).toBe(3);
    });

    it('devuelve 4 con aplazamiento', () => {
      expect(totalPeriodosContinuidad(proyectoEnEstado('CONTINUIDAD', { continuidadPeriodoAdicional: true }))).toBe(4);
    });
  });

  describe('periodosContados', () => {
    it('devuelve 0 cuando el modelo no reporta nada', () => {
      expect(periodosContados(proyectoEnEstado('CONTINUIDAD'))).toBe(0);
    });

    it('devuelve el numero reportado por el backend', () => {
      expect(periodosContados(proyectoEnEstado('CONTINUIDAD', { periodosContinuidadUsados: 2 }))).toBe(2);
    });
  });

  describe('marcadorContinuidad', () => {
    it('devuelve "-" antes de entrar en continuidad', () => {
      expect(marcadorContinuidad(proyectoEnEstado('REGULAR'))).toBe('-');
    });

    it('devuelve "3" mientras esta en continuidad sin aplazamiento', () => {
      expect(marcadorContinuidad(proyectoEnEstado('CONTINUIDAD'))).toBe('3');
    });

    it('devuelve "3+1" cuando hubo aplazamiento', () => {
      expect(marcadorContinuidad(proyectoEnEstado('CONTINUIDAD', { continuidadPeriodoAdicional: true }))).toBe('3+1');
    });

    it('devuelve "sin derecho" cuando el estudiante perdio el derecho', () => {
      expect(marcadorContinuidad(proyectoEnEstado('CONTINUIDAD_PERDIDA'))).toBe('sin derecho');
    });
  });

  describe('resumenLimite', () => {
    it('marca alLimite cuando uso 3 de 3 sin aplazamiento', () => {
      const r = resumenLimite(proyectoEnEstado('CONTINUIDAD', { periodosContinuidadUsados: 3 }));
      expect(r.alLimite).toBe(true);
      expect(r.excedido).toBe(false);
    });

    it('marca alLimite cuando uso 4 de 4 con aplazamiento', () => {
      const r = resumenLimite(proyectoEnEstado('APLAZADO', { periodosContinuidadUsados: 4, continuidadPeriodoAdicional: true }));
      expect(r.alLimite).toBe(true);
    });

    it('marca excedido cuando uso supera el total', () => {
      const r = resumenLimite(proyectoEnEstado('CONTINUIDAD', { periodosContinuidadUsados: 5 }));
      expect(r.excedido).toBe(true);
      expect(r.alLimite).toBe(true);
    });

    it('marca excedido cuando el estado es CONTINUIDAD_PERDIDA', () => {
      const r = resumenLimite(proyectoEnEstado('CONTINUIDAD_PERDIDA', { periodosContinuidadUsados: 1 }));
      expect(r.excedido).toBe(true);
      expect(r.alLimite).toBe(false);
    });

    it('no marca alLimite ni excedido para estado regular', () => {
      const r = resumenLimite(proyectoEnEstado('REGULAR'));
      expect(r.alLimite).toBe(false);
      expect(r.excedido).toBe(false);
    });
  });

  describe('continuidadEtiqueta', () => {
    it('muestra "Regular" cuando el estudiante esta en estado REGULAR', () => {
      expect(continuidadEtiqueta(proyectoEnEstado('REGULAR'))).toBe('Regular');
    });

    it('muestra "Derecho perdido" cuando el estudiante esta en CONTINUIDAD_PERDIDA', () => {
      expect(continuidadEtiqueta(proyectoEnEstado('CONTINUIDAD_PERDIDA'))).toBe('Derecho perdido');
    });

    it('muestra "En continuidad X/3" para uso normal sin aplazamiento', () => {
      expect(continuidadEtiqueta(proyectoEnEstado('CONTINUIDAD', { periodosContinuidadUsados: 2 }))).toBe('En continuidad 2/3');
    });

    it('muestra "al limite" cuando se llego al maximo permitido', () => {
      expect(continuidadEtiqueta(proyectoEnEstado('CONTINUIDAD', { periodosContinuidadUsados: 3 }))).toBe(
        'En continuidad 3/3 (al limite Acdo. 25 art. 5)'
      );
    });

    it('muestra "(perdido tras N)" cuando el contador supera el maximo sin cambio de estado', () => {
      expect(continuidadEtiqueta(proyectoEnEstado('CONTINUIDAD', { periodosContinuidadUsados: 5 }))).toBe(
        'En continuidad 5/3 (perdido tras 5 periodos)'
      );
    });
  });

  describe('plazoFinalContinuidad', () => {
    it('devuelve null si no hay fechaInicioContinuidad', () => {
      expect(plazoFinalContinuidad(proyectoEnEstado('CONTINUIDAD'))).toBeNull();
    });

    it('cuenta 1 periodo academico ~= 4 meses calendario (12 meses para 3 periodos)', () => {
      const fechaInicio = new Date('2024-01-15T00:00:00');
      // 3 periodos ~= 12 meses -> 2025-01-15
      const ahora = new Date('2024-02-15T00:00:00');
      const r = plazoFinalContinuidad(proyectoEnEstado('CONTINUIDAD', { fechaInicioContinuidad: fechaInicio }), ahora);
      // Feb tiene 29 dias en 2024; 2024-02-15 -> 2025-01-15 ~= 335 dias
      expect(r).toBeGreaterThanOrEqual(333);
      expect(r).toBeLessThanOrEqual(336);
    });

    it('considera el +1 cuando hay aplazamiento (16 meses en lugar de 12)', () => {
      const fechaInicio = new Date('2024-01-15T00:00:00');
      const ahora = new Date('2025-02-15T00:00:00');
      // Sin aplazamiento: vencida desde 2025-01-15 (~31 dias)
      const sin = plazoFinalContinuidad(proyectoEnEstado('CONTINUIDAD', { fechaInicioContinuidad: fechaInicio }), ahora);
      expect(sin).toBeLessThanOrEqual(-31);
      // Con aplazamiento: 16 meses despues (a 2025-05-15) -> faltan ~3 meses
      const con = plazoFinalContinuidad(
        proyectoEnEstado('CONTINUIDAD', {
          fechaInicioContinuidad: fechaInicio,
          continuidadPeriodoAdicional: true,
        }),
        ahora
      );
      expect(con).toBeGreaterThan(0);
      expect(con).toBeGreaterThan(sin!);
    });
  });
});
