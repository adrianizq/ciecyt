/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ImpactosEsperadosUpdateComponent } from 'app/entities/impactos-esperados/impactos-esperados-update.component';
import { ImpactosEsperadosService } from 'app/entities/impactos-esperados/impactos-esperados.service';
import { ImpactosEsperados } from 'app/shared/model/impactos-esperados.model';

describe('Component Tests', () => {
  describe('ImpactosEsperados Management Update Component', () => {
    let comp: ImpactosEsperadosUpdateComponent;
    let fixture: ComponentFixture<ImpactosEsperadosUpdateComponent>;
    let service: ImpactosEsperadosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ImpactosEsperadosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImpactosEsperadosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImpactosEsperadosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpactosEsperadosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImpactosEsperados(123);
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
        const entity = new ImpactosEsperados();
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
