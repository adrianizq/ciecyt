/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ResultadosEsperadosUpdateComponent } from 'app/entities/resultados-esperados/resultados-esperados-update.component';
import { ResultadosEsperadosService } from 'app/entities/resultados-esperados/resultados-esperados.service';
import { ResultadosEsperados } from 'app/shared/model/resultados-esperados.model';

describe('Component Tests', () => {
  describe('ResultadosEsperados Management Update Component', () => {
    let comp: ResultadosEsperadosUpdateComponent;
    let fixture: ComponentFixture<ResultadosEsperadosUpdateComponent>;
    let service: ResultadosEsperadosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ResultadosEsperadosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResultadosEsperadosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResultadosEsperadosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResultadosEsperadosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResultadosEsperados(123);
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
        const entity = new ResultadosEsperados();
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
