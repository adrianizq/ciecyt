/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { RubrosUpdateComponent } from 'app/entities/rubros/rubros-update.component';
import { RubrosService } from 'app/entities/rubros/rubros.service';
import { Rubros } from 'app/shared/model/rubros.model';

describe('Component Tests', () => {
  describe('Rubros Management Update Component', () => {
    let comp: RubrosUpdateComponent;
    let fixture: ComponentFixture<RubrosUpdateComponent>;
    let service: RubrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [RubrosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RubrosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RubrosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RubrosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Rubros(123);
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
        const entity = new Rubros();
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
