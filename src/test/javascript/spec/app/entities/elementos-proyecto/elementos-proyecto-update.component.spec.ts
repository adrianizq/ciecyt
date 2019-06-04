/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ElementosProyectoUpdateComponent } from 'app/entities/elementos-proyecto/elementos-proyecto-update.component';
import { ElementosProyectoService } from 'app/entities/elementos-proyecto/elementos-proyecto.service';
import { ElementosProyecto } from 'app/shared/model/elementos-proyecto.model';

describe('Component Tests', () => {
  describe('ElementosProyecto Management Update Component', () => {
    let comp: ElementosProyectoUpdateComponent;
    let fixture: ComponentFixture<ElementosProyectoUpdateComponent>;
    let service: ElementosProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ElementosProyectoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ElementosProyectoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElementosProyectoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElementosProyectoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ElementosProyecto(123);
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
        const entity = new ElementosProyecto();
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
