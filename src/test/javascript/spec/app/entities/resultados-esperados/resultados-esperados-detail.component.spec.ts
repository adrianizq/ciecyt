/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ResultadosEsperadosDetailComponent } from 'app/entities/resultados-esperados/resultados-esperados-detail.component';
import { ResultadosEsperados } from 'app/shared/model/resultados-esperados.model';

describe('Component Tests', () => {
  describe('ResultadosEsperados Management Detail Component', () => {
    let comp: ResultadosEsperadosDetailComponent;
    let fixture: ComponentFixture<ResultadosEsperadosDetailComponent>;
    const route = ({ data: of({ resultadosEsperados: new ResultadosEsperados(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ResultadosEsperadosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResultadosEsperadosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResultadosEsperadosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resultadosEsperados).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
