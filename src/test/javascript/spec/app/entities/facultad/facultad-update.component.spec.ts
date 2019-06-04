/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { FacultadUpdateComponent } from 'app/entities/facultad/facultad-update.component';
import { FacultadService } from 'app/entities/facultad/facultad.service';
import { Facultad } from 'app/shared/model/facultad.model';

describe('Component Tests', () => {
  describe('Facultad Management Update Component', () => {
    let comp: FacultadUpdateComponent;
    let fixture: ComponentFixture<FacultadUpdateComponent>;
    let service: FacultadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FacultadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FacultadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacultadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacultadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Facultad(123);
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
        const entity = new Facultad();
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
