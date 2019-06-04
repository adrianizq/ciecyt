/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { ResultadosEsperadosComponent } from 'app/entities/resultados-esperados/resultados-esperados.component';
import { ResultadosEsperadosService } from 'app/entities/resultados-esperados/resultados-esperados.service';
import { ResultadosEsperados } from 'app/shared/model/resultados-esperados.model';

describe('Component Tests', () => {
  describe('ResultadosEsperados Management Component', () => {
    let comp: ResultadosEsperadosComponent;
    let fixture: ComponentFixture<ResultadosEsperadosComponent>;
    let service: ResultadosEsperadosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ResultadosEsperadosComponent],
        providers: []
      })
        .overrideTemplate(ResultadosEsperadosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadosEsperadosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadosEsperadosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ResultadosEsperados(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.resultadosEsperados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
