/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { LineaDeInvestigacionDetailComponent } from 'app/entities/linea-de-investigacion/linea-de-investigacion-detail.component';
import { LineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';

describe('Component Tests', () => {
  describe('LineaDeInvestigacion Management Detail Component', () => {
    let comp: LineaDeInvestigacionDetailComponent;
    let fixture: ComponentFixture<LineaDeInvestigacionDetailComponent>;
    const route = ({ data: of({ lineaDeInvestigacion: new LineaDeInvestigacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [LineaDeInvestigacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LineaDeInvestigacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LineaDeInvestigacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lineaDeInvestigacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
