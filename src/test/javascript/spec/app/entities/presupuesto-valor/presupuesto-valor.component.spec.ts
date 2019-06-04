/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { PresupuestoValorComponent } from 'app/entities/presupuesto-valor/presupuesto-valor.component';
import { PresupuestoValorService } from 'app/entities/presupuesto-valor/presupuesto-valor.service';
import { PresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

describe('Component Tests', () => {
  describe('PresupuestoValor Management Component', () => {
    let comp: PresupuestoValorComponent;
    let fixture: ComponentFixture<PresupuestoValorComponent>;
    let service: PresupuestoValorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PresupuestoValorComponent],
        providers: []
      })
        .overrideTemplate(PresupuestoValorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PresupuestoValorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PresupuestoValorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PresupuestoValor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.presupuestoValors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
