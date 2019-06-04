/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { PresupuestoValorUpdateComponent } from 'app/entities/presupuesto-valor/presupuesto-valor-update.component';
import { PresupuestoValorService } from 'app/entities/presupuesto-valor/presupuesto-valor.service';
import { PresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

describe('Component Tests', () => {
  describe('PresupuestoValor Management Update Component', () => {
    let comp: PresupuestoValorUpdateComponent;
    let fixture: ComponentFixture<PresupuestoValorUpdateComponent>;
    let service: PresupuestoValorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PresupuestoValorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PresupuestoValorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PresupuestoValorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PresupuestoValorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PresupuestoValor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PresupuestoValor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
