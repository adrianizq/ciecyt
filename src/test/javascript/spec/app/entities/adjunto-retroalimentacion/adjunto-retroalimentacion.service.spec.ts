/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import AdjuntoRetroalimentacionService from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion.service';
import { AdjuntoRetroalimentacion } from '@/shared/model/adjunto-retroalimentacion.model';

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
  describe('AdjuntoRetroalimentacion Service', () => {
    let service: AdjuntoRetroalimentacionService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new AdjuntoRetroalimentacionService();
      currentDate = new Date();

      elemDefault = new AdjuntoRetroalimentacion(
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaCreacion: format(currentDate, DATE_FORMAT),
            fechaModificacion: format(currentDate, DATE_FORMAT),
            fechaInicio: format(currentDate, DATE_FORMAT),
            fechaFin: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
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

      it('should create a AdjuntoRetroalimentacion', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCreacion: format(currentDate, DATE_FORMAT),
            fechaModificacion: format(currentDate, DATE_FORMAT),
            fechaInicio: format(currentDate, DATE_FORMAT),
            fechaFin: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaModificacion: currentDate,
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a AdjuntoRetroalimentacion', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a AdjuntoRetroalimentacion', async () => {
        const returnedFromService = Object.assign(
          {
            nombreAdjunto: 'BBBBBB',
            fechaCreacion: format(currentDate, DATE_FORMAT),
            fechaModificacion: format(currentDate, DATE_FORMAT),
            estadoAdjunto: 1,
            adjuntoRetroalimentacion: 'BBBBBB',
            nombreArchivoOriginal: 'BBBBBB',
            fechaInicio: format(currentDate, DATE_FORMAT),
            fechaFin: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaModificacion: currentDate,
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a AdjuntoRetroalimentacion', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of AdjuntoRetroalimentacion', async () => {
        const returnedFromService = Object.assign(
          {
            nombreAdjunto: 'BBBBBB',
            fechaCreacion: format(currentDate, DATE_FORMAT),
            fechaModificacion: format(currentDate, DATE_FORMAT),
            estadoAdjunto: 1,
            adjuntoRetroalimentacion: 'BBBBBB',
            nombreArchivoOriginal: 'BBBBBB',
            fechaInicio: format(currentDate, DATE_FORMAT),
            fechaFin: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaModificacion: currentDate,
            fechaInicio: currentDate,
            fechaFin: currentDate,
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of AdjuntoRetroalimentacion', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a AdjuntoRetroalimentacion', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a AdjuntoRetroalimentacion', async () => {
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
