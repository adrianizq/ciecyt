import {
  cantidadRequisitosAdicionales,
  paqueteCompletoDeRequisitos,
  REQUISITOS_ADICIONALES_POR_MODALIDAD,
  requisitosAdicionalesPorModalidad,
  resumenRequisitosAdicionales,
} from '@/shared/config/requisitos-modalidad';

describe('requisitos-modalidad (Acuerdo 25 art. 5 par. 2 y Tabla 3)', () => {
  describe('modalidades con requisitos especificos (Tabla 3)', () => {
    it('9001 Pasantia Tecnologica exige 4 documentos', () => {
      const rs = requisitosAdicionalesPorModalidad(9001);
      expect(rs.length).toBe(4);
      expect(rs.map(r => r.codigo)).toContain('PT-OFICIO-DECANO');
      expect(rs.map(r => r.codigo)).toContain('PT-ACTA-INICIO');
    });

    it('9002 Pasantia Investigativa Profesional exige 4 documentos', () => {
      const rs = requisitosAdicionalesPorModalidad(9002);
      expect(rs.length).toBe(4);
      expect(rs.map(r => r.codigo)).toContain('PIP-OFICIO-DECANO');
      expect(rs.map(r => r.codigo)).toContain('PIP-ACTA-INICIO');
    });

    it('9003 Pasantia Internacional exige los 4 de la pasantia regular + 2 internacionales', () => {
      const rs = requisitosAdicionalesPorModalidad(9003);
      expect(rs.length).toBe(6);
      expect(rs.map(r => r.codigo)).toContain('PI-CARTA-ACEPTACION');
      expect(rs.map(r => r.codigo)).toContain('PI-PASAPORTE-VISA');
      expect(rs.map(r => r.codigo)).toContain('PI-ACTA-INICIO');
    });

    it('9004 Tesis exige formato de inscripcion + carta de aval del asesor', () => {
      const rs = requisitosAdicionalesPorModalidad(9004);
      expect(rs.length).toBe(2);
      expect(rs.map(r => r.codigo)).toEqual(['T-FORM-INSCRIPCION', 'T-CARTA-AVAL']);
    });

    it('9005 Diplomado exige soporte de pago', () => {
      const rs = requisitosAdicionalesPorModalidad(9005);
      expect(rs.length).toBe(1);
      expect(rs[0].codigo).toBe('D-SOPORTE-PAGO');
    });

    it('9006 Publicacion de Articulo exige carta de aceptacion de la revista', () => {
      const rs = requisitosAdicionalesPorModalidad(9006);
      expect(rs.length).toBe(1);
      expect(rs[0].codigo).toBe('PA-CARTA-ACEPTACION');
    });

    it('9007 Especializacion exige propuesta de investigacion (art. 39)', () => {
      const rs = requisitosAdicionalesPorModalidad(9007);
      expect(rs.length).toBe(1);
      expect(rs[0].codigo).toBe('E-PROPUESTA-INVESTIGACION');
    });
  });

  describe('modalidad sin requisitos especificos', () => {
    it('devuelve lista vacia para modalidad desconocida', () => {
      expect(requisitosAdicionalesPorModalidad(9999)).toEqual([]);
    });

    it('devuelve lista vacia para modalidad null/undefined', () => {
      expect(requisitosAdicionalesPorModalidad(null)).toEqual([]);
      expect(requisitosAdicionalesPorModalidad(undefined)).toEqual([]);
    });

    it('cada RequisitoAdicional cita el Acuerdo correctamente', () => {
      for (const [, lista] of Object.entries(REQUISITOS_ADICIONALES_POR_MODALIDAD)) {
        for (const req of lista) {
          expect(req.referencia).toMatch(/Acuerdo 25 art/);
          expect(req.codigo).toBeTruthy();
          expect(req.nombre).toBeTruthy();
          expect(req.descripcion).toBeTruthy();
          expect(Array.isArray(req.firmantes)).toBe(true);
        }
      }
    });
  });

  describe('resumen y conteo', () => {
    it('cantidadRequisitosAdicionales refleja la cantidad de entradas', () => {
      expect(cantidadRequisitosAdicionales(9001)).toBe(4);
      expect(cantidadRequisitosAdicionales(9005)).toBe(1);
      expect(cantidadRequisitosAdicionales(9007)).toBe(1);
      expect(cantidadRequisitosAdicionales(9999)).toBe(0);
    });

    it('resumenRequisitosAdicionales agrega firmantes cuando existen', () => {
      const lineas = resumenRequisitosAdicionales(9001);
      expect(lineas.length).toBe(4);
      expect(lineas[2]).toMatch(/firmado por Decano de facultad, Asesor/);
      expect(lineas[3]).toMatch(/Acta de inicio/);
    });

    it('resumenRequisitosAdicionales omite firmantes cuando la lista esta vacia', () => {
      const lineas = resumenRequisitosAdicionales(9005);
      expect(lineas.length).toBe(1);
      expect(lineas[0]).not.toMatch(/firmado por/);
    });
  });

  describe('paqueteCompletoDeRequisitos (Tabla 2 + Tabla 3)', () => {
    it('combina los 6 requisitos comunes con los adicionales de la modalidad', () => {
      const paquete = paqueteCompletoDeRequisitos(9001);
      expect(paquete.length).toBe(6 + 4);
      // Los comunes siempre van primero
      expect(paquete[0].tipo).toBe('comun');
      const adicionales = paquete.filter(r => r.tipo === 'adicional');
      expect(adicionales.length).toBe(4);
    });

    it('para una modalidad sin adicionales, devuelve solo los comunes', () => {
      const paquete = paqueteCompletoDeRequisitos(9999);
      expect(paquete.length).toBe(6);
      expect(paquete.every(r => r.tipo === 'comun')).toBe(true);
    });

    it('los requisitos comunes de pregrado incluyen la asistencia minima a 2 sustentaciones (R7)', () => {
      const paquete = paqueteCompletoDeRequisitos(9004);
      const asistencia = paquete.find(r => r.codigo === 'COMUN-ASISTENCIA-SUSTENTACIONES');
      expect(asistencia).toBeTruthy();
      expect(asistencia?.nombre).toMatch(/Asistencia.*sustentaciones/);
      expect(asistencia?.descripcion).toMatch(/dos.*actos publicos/);
      expect(asistencia?.referencia).toMatch(/Tabla 2/);
    });
  });
});
