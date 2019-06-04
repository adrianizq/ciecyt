/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { PresupuestoValorDetailComponent } from 'app/entities/presupuesto-valor/presupuesto-valor-detail.component';
import { PresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

describe('Component Tests', () => {
  describe('PresupuestoValor Management Detail Component', () => {
    let comp: PresupuestoValorDetailComponent;
    let fixture: ComponentFixture<PresupuestoValorDetailComponent>;
    const route = ({ data: of({ presupuestoValor: new PresupuestoValor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PresupuestoValorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PresupuestoValorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PresupuestoValorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.presupuestoValor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
