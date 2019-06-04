/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ImpactosEsperadosDetailComponent } from 'app/entities/impactos-esperados/impactos-esperados-detail.component';
import { ImpactosEsperados } from 'app/shared/model/impactos-esperados.model';

describe('Component Tests', () => {
  describe('ImpactosEsperados Management Detail Component', () => {
    let comp: ImpactosEsperadosDetailComponent;
    let fixture: ComponentFixture<ImpactosEsperadosDetailComponent>;
    const route = ({ data: of({ impactosEsperados: new ImpactosEsperados(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ImpactosEsperadosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImpactosEsperadosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImpactosEsperadosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.impactosEsperados).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
