/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { PreguntasUpdateComponent } from 'app/entities/preguntas/preguntas-update.component';
import { PreguntasService } from 'app/entities/preguntas/preguntas.service';
import { Preguntas } from 'app/shared/model/preguntas.model';

describe('Component Tests', () => {
  describe('Preguntas Management Update Component', () => {
    let comp: PreguntasUpdateComponent;
    let fixture: ComponentFixture<PreguntasUpdateComponent>;
    let service: PreguntasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PreguntasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PreguntasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PreguntasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PreguntasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Preguntas(123);
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
        const entity = new Preguntas();
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
