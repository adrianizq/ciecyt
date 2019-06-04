/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CronogramaService } from 'app/entities/cronograma/cronograma.service';
import { ICronograma, Cronograma } from 'app/shared/model/cronograma.model';

describe('Service Tests', () => {
  describe('Cronograma Service', () => {
    let injector: TestBed;
    let service: CronogramaService;
    let httpMock: HttpTestingController;
    let elemDefault: ICronograma;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CronogramaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Cronograma(0, 'AAAAAAA', 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            fechaIni: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Cronograma', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaIni: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaIni: currentDate,
            fechaFin: currentDate
          },
          returnedFromService
        );
        service
          .create(new Cronograma(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Cronograma', async () => {
        const returnedFromService = Object.assign(
          {
            actividad: 'BBBBBB',
            duracion: 1,
            fechaIni: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaIni: currentDate,
            fechaFin: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Cronograma', async () => {
        const returnedFromService = Object.assign(
          {
            actividad: 'BBBBBB',
            duracion: 1,
            fechaIni: currentDate.format(DATE_FORMAT),
            fechaFin: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fechaIni: currentDate,
            fechaFin: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Cronograma', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
