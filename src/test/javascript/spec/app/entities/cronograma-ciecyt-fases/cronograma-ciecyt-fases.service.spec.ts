/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import CronogramaCiecytFasesService from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases.service';
import { CronogramaCiecytFases } from '@/shared/model/cronograma-ciecyt-fases.model';

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
  describe('CronogramaCiecytFases Service', () => {
    let service: CronogramaCiecytFasesService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new CronogramaCiecytFasesService();
      currentDate = new Date();

      elemDefault = new CronogramaCiecytFases(0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            inicioFase: format(currentDate, DATE_FORMAT),
            finFase: format(currentDate, DATE_FORMAT),
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

      it('should create a CronogramaCiecytFases', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inicioFase: format(currentDate, DATE_FORMAT),
            finFase: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicioFase: currentDate,
            finFase: currentDate,
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a CronogramaCiecytFases', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a CronogramaCiecytFases', async () => {
        const returnedFromService = Object.assign(
          {
            inicioFase: format(currentDate, DATE_FORMAT),
            finFase: format(currentDate, DATE_FORMAT),
            textoExplicativo: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inicioFase: currentDate,
            finFase: currentDate,
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a CronogramaCiecytFases', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of CronogramaCiecytFases', async () => {
        const returnedFromService = Object.assign(
          {
            inicioFase: format(currentDate, DATE_FORMAT),
            finFase: format(currentDate, DATE_FORMAT),
            textoExplicativo: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            inicioFase: currentDate,
            finFase: currentDate,
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of CronogramaCiecytFases', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a CronogramaCiecytFases', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a CronogramaCiecytFases', async () => {
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
