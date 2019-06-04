/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { EntidadFinanciadoraUpdateComponent } from 'app/entities/entidad-financiadora/entidad-financiadora-update.component';
import { EntidadFinanciadoraService } from 'app/entities/entidad-financiadora/entidad-financiadora.service';
import { EntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';

describe('Component Tests', () => {
  describe('EntidadFinanciadora Management Update Component', () => {
    let comp: EntidadFinanciadoraUpdateComponent;
    let fixture: ComponentFixture<EntidadFinanciadoraUpdateComponent>;
    let service: EntidadFinanciadoraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [EntidadFinanciadoraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntidadFinanciadoraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntidadFinanciadoraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntidadFinanciadoraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntidadFinanciadora(123);
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
        const entity = new EntidadFinanciadora();
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
