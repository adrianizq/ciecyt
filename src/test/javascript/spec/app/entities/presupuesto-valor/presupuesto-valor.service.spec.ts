/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PresupuestoValorService } from 'app/entities/presupuesto-valor/presupuesto-valor.service';
import { IPresupuestoValor, PresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

describe('Service Tests', () => {
  describe('PresupuestoValor Service', () => {
    let injector: TestBed;
    let service: PresupuestoValorService;
    let httpMock: HttpTestingController;
    let elemDefault: IPresupuestoValor;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PresupuestoValorService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PresupuestoValor(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PresupuestoValor', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new PresupuestoValor(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PresupuestoValor', async () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            justificacion: 'BBBBBB',
            cantidad: 1,
            valorUnitario: 1,
            especie: 1,
            dinero: 1,
            entidadFinanciadora: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PresupuestoValor', async () => {
        const returnedFromService = Object.assign(
          {
            descripcion: 'BBBBBB',
            justificacion: 'BBBBBB',
            cantidad: 1,
            valorUnitario: 1,
            especie: 1,
            dinero: 1,
            entidadFinanciadora: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a PresupuestoValor', async () => {
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
