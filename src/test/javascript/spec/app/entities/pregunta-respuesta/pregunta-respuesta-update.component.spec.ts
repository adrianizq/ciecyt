/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { PreguntaRespuestaUpdateComponent } from 'app/entities/pregunta-respuesta/pregunta-respuesta-update.component';
import { PreguntaRespuestaService } from 'app/entities/pregunta-respuesta/pregunta-respuesta.service';
import { PreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';

describe('Component Tests', () => {
  describe('PreguntaRespuesta Management Update Component', () => {
    let comp: PreguntaRespuestaUpdateComponent;
    let fixture: ComponentFixture<PreguntaRespuestaUpdateComponent>;
    let service: PreguntaRespuestaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PreguntaRespuestaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PreguntaRespuestaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PreguntaRespuestaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PreguntaRespuestaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PreguntaRespuesta(123);
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
        const entity = new PreguntaRespuesta();
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
