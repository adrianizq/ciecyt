/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { TipoPreguntaUpdateComponent } from 'app/entities/tipo-pregunta/tipo-pregunta-update.component';
import { TipoPreguntaService } from 'app/entities/tipo-pregunta/tipo-pregunta.service';
import { TipoPregunta } from 'app/shared/model/tipo-pregunta.model';

describe('Component Tests', () => {
  describe('TipoPregunta Management Update Component', () => {
    let comp: TipoPreguntaUpdateComponent;
    let fixture: ComponentFixture<TipoPreguntaUpdateComponent>;
    let service: TipoPreguntaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [TipoPreguntaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoPreguntaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoPreguntaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPreguntaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoPregunta(123);
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
        const entity = new TipoPregunta();
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
