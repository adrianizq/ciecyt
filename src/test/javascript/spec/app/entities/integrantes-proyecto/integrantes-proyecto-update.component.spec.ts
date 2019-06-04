/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { IntegrantesProyectoUpdateComponent } from 'app/entities/integrantes-proyecto/integrantes-proyecto-update.component';
import { IntegrantesProyectoService } from 'app/entities/integrantes-proyecto/integrantes-proyecto.service';
import { IntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

describe('Component Tests', () => {
  describe('IntegrantesProyecto Management Update Component', () => {
    let comp: IntegrantesProyectoUpdateComponent;
    let fixture: ComponentFixture<IntegrantesProyectoUpdateComponent>;
    let service: IntegrantesProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [IntegrantesProyectoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IntegrantesProyectoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntegrantesProyectoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntegrantesProyectoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IntegrantesProyecto(123);
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
        const entity = new IntegrantesProyecto();
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
