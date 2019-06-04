/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { EntidadFinanciadoraDetailComponent } from 'app/entities/entidad-financiadora/entidad-financiadora-detail.component';
import { EntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';

describe('Component Tests', () => {
  describe('EntidadFinanciadora Management Detail Component', () => {
    let comp: EntidadFinanciadoraDetailComponent;
    let fixture: ComponentFixture<EntidadFinanciadoraDetailComponent>;
    const route = ({ data: of({ entidadFinanciadora: new EntidadFinanciadora(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [EntidadFinanciadoraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntidadFinanciadoraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntidadFinanciadoraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entidadFinanciadora).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
