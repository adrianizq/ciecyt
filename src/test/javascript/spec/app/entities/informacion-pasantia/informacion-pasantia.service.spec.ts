/* tslint:disable max-line-length */
import axios from 'axios';

import * as config from '@/shared/config/config';
import {} from '@/shared/date/filters';
import InformacionPasantiaService from '@/entities/informacion-pasantia/informacion-pasantia.service';
import { InformacionPasantia } from '@/shared/model/informacion-pasantia.model';

const mockedAxios: any = axios;
const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn(),
}));

describe('Service Tests', () => {
  describe('InformacionPasantia Service', () => {
    let service: InformacionPasantiaService;
    let elemDefault;
    beforeEach(() => {
      service = new InformacionPasantiaService();

      elemDefault = new InformacionPasantia();
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a InformacionPasantia', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a InformacionPasantia', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a InformacionPasantia', async () => {
        const returnedFromService = Object.assign(
          {
            duracionHoras: 1,
            direccion: 'BBBBBB',
            email: 'BBBBBB',
            lunes: true,
            martes: true,
            miercoles: true,
            jueves: true,
            viernes: true,
            sabado: true,
            domingo: true,
            horasMes: 1,
            bonoAlimenticio: true,
            apoyoEconomico: true,
            auxilioTransporte: true,
            capacitacion: true,
            otroApoyo: 'BBBBBB',
            nombreEmpresa: 'BBBBBB',
            nitEmpresa: 'BBBBBB',
            direccionEmpresa: 'BBBBBB',
            sectorEconomicoEmpresa: 'BBBBBB',
            representanteLegalEmpresa: 'BBBBBB',
            asesorEmpresa: 'BBBBBB',
            cargoAsesorEmpresa: 'BBBBBB',
            emailAsesorEmpresa: 'BBBBBB',
            municipioEmpresa: 'BBBBBB',
            telefonoContactoEmpresa: 'BBBBBB',
            emailEmpresa: 'BBBBBB',
            departamentoEmpresa: 'BBBBBB',
            identificacionRepresentanteLegal: 'BBBBBB',
            profesionAsesorEmpresa: 'BBBBBB',
            celularAsesorEmpresa: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a InformacionPasantia', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of InformacionPasantia', async () => {
        const returnedFromService = Object.assign(
          {
            duracionHoras: 1,
            direccion: 'BBBBBB',
            email: 'BBBBBB',
            lunes: true,
            martes: true,
            miercoles: true,
            jueves: true,
            viernes: true,
            sabado: true,
            domingo: true,
            horasMes: 1,
            bonoAlimenticio: true,
            apoyoEconomico: true,
            auxilioTransporte: true,
            capacitacion: true,
            otroApoyo: 'BBBBBB',
            nombreEmpresa: 'BBBBBB',
            nitEmpresa: 'BBBBBB',
            direccionEmpresa: 'BBBBBB',
            sectorEconomicoEmpresa: 'BBBBBB',
            representanteLegalEmpresa: 'BBBBBB',
            asesorEmpresa: 'BBBBBB',
            cargoAsesorEmpresa: 'BBBBBB',
            emailAsesorEmpresa: 'BBBBBB',
            municipioEmpresa: 'BBBBBB',
            telefonoContactoEmpresa: 'BBBBBB',
            emailEmpresa: 'BBBBBB',
            departamentoEmpresa: 'BBBBBB',
            identificacionRepresentanteLegal: 'BBBBBB',
            profesionAsesorEmpresa: 'BBBBBB',
            celularAsesorEmpresa: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of InformacionPasantia', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a InformacionPasantia', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a InformacionPasantia', async () => {
        mockedAxios.delete.mockReturnValue(Promise.reject(error));

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
