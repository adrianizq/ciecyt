/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { LineaDeInvestigacionUpdateComponent } from 'app/entities/linea-de-investigacion/linea-de-investigacion-update.component';
import { LineaDeInvestigacionService } from 'app/entities/linea-de-investigacion/linea-de-investigacion.service';
import { LineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';

describe('Component Tests', () => {
  describe('LineaDeInvestigacion Management Update Component', () => {
    let comp: LineaDeInvestigacionUpdateComponent;
    let fixture: ComponentFixture<LineaDeInvestigacionUpdateComponent>;
    let service: LineaDeInvestigacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [LineaDeInvestigacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LineaDeInvestigacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineaDeInvestigacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineaDeInvestigacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LineaDeInvestigacion(123);
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
        const entity = new LineaDeInvestigacion();
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
